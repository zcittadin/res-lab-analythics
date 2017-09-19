package com.servicos.estatica.resicolor.lab.analythics.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AmostrasReportBuilder {

	public static void buildSingle(Prova prova, String path) {

		TextColumnBuilder<Date> dataHoraColumn = col.column("Horário", "horario", type.timeHourToSecondType());
		TextColumnBuilder<Double> temperaturaColumn = col.column("Temp. (ºC)", "temp", type.doubleType());
		TextColumnBuilder<Double> setPointColumn = col.column("Set-point (ºC)", "setPoint", type.doubleType());
		TextColumnBuilder<Double> iaSobreNvColumn = col.column("IA sobre NV", "iaSobreNv", type.doubleType());
		TextColumnBuilder<String> viscGardnerColumn = col.column("V. Gardner", "viscGardner", type.stringType());
		TextColumnBuilder<String> corGardnerColumn = col.column("Cor Gardner", "corGardner", type.stringType());
		TextColumnBuilder<Double> percNvColumn = col.column("% NV", "percentualNv", type.doubleType());
		TextColumnBuilder<Integer> gelTimeColumn = col.column("Gel-time", "gelTime", type.integerType());
		TextColumnBuilder<Double> aguaColumn = col.column("Água (ml)", "agua", type.doubleType());
		TextColumnBuilder<Double> amostraColumn = col.column("Amostra", "amostra", type.doubleType());
		TextColumnBuilder<Double> phColumn = col.column("PH", "ph", type.doubleType());
		TextColumnBuilder<String> descricaoColumn = col.column("Desc.", "descricao", type.stringType());

		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
		try {
			report().setTemplate(AmostrasReportTemplate.reportTemplate)
					.title(AmostrasReportTemplate.createHeaderComponent(prova),
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

//		List<AmostrasReportModel> amostrasModel = createDataSource(prova1, prova2);
		
		TextColumnBuilder<Date> dataHoraColumn = col.column("Horário", "horario", type.timeHourToSecondType());
		TextColumnBuilder<Double> temperaturaColumn = col.column("Temp. (ºC)", "temp", type.doubleType());
		TextColumnBuilder<Double> setPointColumn = col.column("Set-point (ºC)", "setPoint", type.doubleType());
		TextColumnBuilder<Double> iaSobreNvColumn = col.column("IA sobre NV", "iaSobreNv", type.doubleType());
		TextColumnBuilder<String> viscGardnerColumn = col.column("V. Gardner", "viscGardner", type.stringType());
		TextColumnBuilder<String> corGardnerColumn = col.column("Cor Gardner", "corGardner", type.stringType());
		TextColumnBuilder<Double> percNvColumn = col.column("% NV", "percentualNv", type.doubleType());
		TextColumnBuilder<Integer> gelTimeColumn = col.column("Gel-time", "gelTime", type.integerType());
		TextColumnBuilder<Double> aguaColumn = col.column("Água (ml)", "agua", type.doubleType());
		TextColumnBuilder<Double> amostraColumn = col.column("Amostra", "amostra", type.doubleType());
		TextColumnBuilder<Double> phColumn = col.column("PH", "ph", type.doubleType());
		TextColumnBuilder<String> descricaoColumn = col.column("Desc.", "descricao", type.stringType());

		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
		try {
			report().setTemplate(AmostrasReportTemplate.reportTemplate)
					.title(AmostrasReportTemplate.createHeaderComponent(prova1),
							AmostrasReportTemplate.createSeparatorComponent())
					.setDataSource(prova1.getAmostras())
					.columns(dataHoraColumn, temperaturaColumn, setPointColumn, iaSobreNvColumn, viscGardnerColumn,
							corGardnerColumn, percNvColumn, gelTimeColumn, aguaColumn, amostraColumn, phColumn,
							descricaoColumn)
					.summary(AmostrasReportTemplate.createEmissaoComponent()).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static List<AmostrasReportModel> createDataSource(Prova prova1, Prova prova2) {
		List<AmostrasReportModel> amostrasModel = new ArrayList<>();
		amostrasModel.add(new AmostrasReportModel(prova1.getNomeProva(), prova1.getAmostras()));
		amostrasModel.add(new AmostrasReportModel(prova2.getNomeProva(), prova2.getAmostras()));
		return amostrasModel;
	}
}
