package com.servicos.estatica.resicolor.lab.analythics.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class AmostrasReportBuilder {

	private static TextColumnBuilder<Date> dataHoraColumn = col.column("Horário", "horario", type.timeHourToSecondType());
	private static TextColumnBuilder<Double> temperaturaColumn = col.column("Temp. (ºC)", "temp", type.doubleType());
	private static TextColumnBuilder<Double> setPointColumn = col.column("Set-point (ºC)", "setPoint", type.doubleType());
	private static TextColumnBuilder<Double> iaSobreNvColumn = col.column("Índice de acidez", "iaSobreNv", type.doubleType());
	private static TextColumnBuilder<String> viscGardnerColumn = col.column("Viscosidade", "viscGardner", type.stringType());
	private static TextColumnBuilder<String> corGardnerColumn = col.column("Cor Gardner", "corGardner", type.stringType());
	private static TextColumnBuilder<Double> percNvColumn = col.column("% NV", "percentualNv", type.doubleType());
	private static TextColumnBuilder<Integer> gelTimeColumn = col.column("Gel-time", "gelTime", type.integerType());
	private static TextColumnBuilder<Double> aguaColumn = col.column("Água(ml)", "agua", type.doubleType());
	private static TextColumnBuilder<Double> amostraColumn = col.column("Amostra(g)", "amostra", type.doubleType());
	private static TextColumnBuilder<Double> phColumn = col.column("PH", "ph", type.doubleType());
	private static TextColumnBuilder<String> descricaoColumn = col.column("Desc.", "descricao", type.stringType());
	
	public static void buildSingle(Prova prova, String path) {

		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
		try {
			report().setTemplate(AmostrasReportTemplate.reportTemplate)
					.title(AmostrasReportTemplate.createHeaderComponent(),
							AmostrasReportTemplate.createSeparatorComponent(),
							AmostrasReportTemplate.createDadosComponent(prova),
							AmostrasReportTemplate.createSeparatorComponent(),
							AmostrasReportTemplate.createChartComponent(prova),
							AmostrasReportTemplate.createSeparatorComponent())
					.setDataSource(prova.getAmostras())
					.columns(dataHoraColumn, temperaturaColumn, setPointColumn, iaSobreNvColumn, viscGardnerColumn,
							corGardnerColumn, percNvColumn, gelTimeColumn, aguaColumn, amostraColumn, phColumn,
							descricaoColumn)
					.summary(AmostrasReportTemplate.createEmissaoComponent()).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	public static void buildComparative(Prova prova1, Prova prova2, String path) {

		TextColumnBuilder<String> provaColumn = col.column(null, "prova", type.stringType());

		ColumnGroupBuilder provaGroup = grp.group(provaColumn).setHeaderLayout(GroupHeaderLayout.VALUE)
				.showColumnHeaderAndFooter();

		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
		
		List<AnaliseComparativaModel> models = new ArrayList<>();
		int sizeProva1 = prova1.getLeituras().size();
		int sizeProva2 = prova2.getLeituras().size();
		int i = 0;

		if (sizeProva1 >= sizeProva2) {
			Date inicio = prova1.getLeituras().get(0).getDtProc();
			for (i = 0; i < sizeProva1; i++) {
				Date horario = prova1.getLeituras().get(i).getDtProc();
				long millis = horario.getTime() - inicio.getTime();
				LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
				Date intervalo = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
				models.add(new AnaliseComparativaModel(intervalo,
						prova1.getLeituras().get(i).getDtProc(),
						prova1.getNomeProva(),
						i < sizeProva2 ? prova2.getLeituras().get(i).getDtProc() : null,
						i < sizeProva2 ? prova2.getNomeProva() : "",
						prova1.getLeituras().get(i).getTemp(),
						i < sizeProva2 ? prova2.getLeituras().get(i).getTemp() : 0,
						prova1.getLeituras().get(i).getSp(),
						i < sizeProva2 ? prova2.getLeituras().get(i).getSp() : 0));
			}
		} else {
			Date inicio = prova2.getLeituras().get(0).getDtProc();
			for (i = 0; i < sizeProva2; i++) {
				Date horario = prova2.getLeituras().get(i).getDtProc();
				long millis = horario.getTime() - inicio.getTime();
				LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
				Date intervalo = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
				models.add(new AnaliseComparativaModel(intervalo,
						i < sizeProva1 ? prova1.getLeituras().get(i).getDtProc() : null,
						i < sizeProva1 ? prova1.getNomeProva() : "",
						prova2.getLeituras().get(i).getDtProc(),
						prova2.getNomeProva(),
						i < sizeProva1 ? prova1.getLeituras().get(i).getTemp() : 0,
						prova2.getLeituras().get(i).getTemp(),
						i < sizeProva1 ? prova1.getLeituras().get(i).getSp() : 0,
						prova2.getLeituras().get(i).getSp()));
			}
		}
		
		try {
			report().setTemplate(AmostrasReportTemplate.reportTemplate).setShowColumnTitle(false)
					.title(AmostrasReportTemplate.createHeaderComponent(),
							AmostrasReportTemplate.createSeparatorComponent(),
							AmostrasReportTemplate.createDadosComponent(prova1),
							AmostrasReportTemplate.createSeparatorComponent(),
							AmostrasReportTemplate.createDadosComponent(prova2),
							AmostrasReportTemplate.createSeparatorComponent(),
							AmostrasReportTemplate.createComparativeChartComponent(models, prova1, prova2),
							AmostrasReportTemplate.createSeparatorComponent())
					.setDataSource(createDataSource(prova1, prova2))
					.columns(provaColumn, dataHoraColumn, temperaturaColumn, setPointColumn, iaSobreNvColumn, viscGardnerColumn,
							corGardnerColumn, percNvColumn, gelTimeColumn, aguaColumn, amostraColumn, phColumn,
							descricaoColumn)
					.groupBy(provaGroup).summary(AmostrasReportTemplate.createEmissaoComponent()).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private static JRDataSource createDataSource(Prova prova1, Prova prova2) {
		DRDataSource dataSource = new DRDataSource("prova", "horario", "temp", "setPoint", "iaSobreNv", "viscGardner",
				"corGardner", "percentualNv", "gelTime", "agua", "amostra", "ph", "descricao");
		if (prova1 != null) {
			prova1.getAmostras().forEach(amostra -> {
				dataSource.add("Projeto: " + prova1.getProjeto().getNome() + "\nProva: " + prova1.getNomeProva(),
						amostra.getHorario(), amostra.getTemp(), amostra.getSetPoint(), amostra.getIaSobreNv(),
						amostra.getViscGardner(), amostra.getCorGardner(), amostra.getPercentualNv(),
						amostra.getGelTime(), amostra.getAgua(), amostra.getAmostra(), amostra.getPh(),
						amostra.getDescricao());
			});
		}
		if (prova2 != null) {
			prova2.getAmostras().forEach(amostra -> {
				dataSource.add("Projeto: " + prova2.getProjeto().getNome() + "\nProva: " + prova2.getNomeProva(),
						amostra.getHorario(), amostra.getTemp(), amostra.getSetPoint(), amostra.getIaSobreNv(),
						amostra.getViscGardner(), amostra.getCorGardner(), amostra.getPercentualNv(),
						amostra.getGelTime(), amostra.getAgua(), amostra.getAmostra(), amostra.getPh(),
						amostra.getDescricao());
			});
		}
		return dataSource;
	}
}
