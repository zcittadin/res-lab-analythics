package com.servicos.estatica.resicolor.lab.analythics.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
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
import net.sf.dynamicreports.report.exception.DRException;

public class AnaliseReportBuilder {

	public static void buildSingle(Prova prova, String path) {

		TextColumnBuilder<Date> dataHoraColumn = col.column("Hor�rio", "dtProc", type.timeHourToSecondType());
		TextColumnBuilder<Double> temperaturaColumn = col.column("Temperatura (�C)", "temp", type.doubleType());
		TextColumnBuilder<Double> setPointColumn = col.column("Set-point (�C)", "sp", type.doubleType());

		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
		try {
			report().setTemplate(AnaliseReportTemplate.reportTemplate)
					.title(AnaliseReportTemplate.createHeaderComponent(prova),
							AnaliseReportTemplate.createSeparatorComponent(),
							AnaliseReportTemplate.createDadosComponent(prova),
							AnaliseReportTemplate.createSeparatorComponent(),
							AnaliseReportTemplate.createChartComponent(prova),
							AnaliseReportTemplate.createSeparatorComponent())
					.setDataSource(prova.getLeituras()).columns(dataHoraColumn, temperaturaColumn, setPointColumn)
					.summary(AnaliseReportTemplate.createEmissaoComponent()).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	public static void buildComparative(Prova prova1, Prova prova2, String path) {
		
		TextColumnBuilder<Date> dataHoraColumn = col.column("Hor�rio", "dtProc", type.timeHourToSecondType());
		TextColumnBuilder<Double> temperaturaColumn = col.column("Temperatura (�C)", "temp1", type.doubleType());
		TextColumnBuilder<Double> setPointColumn = col.column("Set-point (�C)", "temp2", type.doubleType());
		
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
				models.add(new AnaliseComparativaModel(intervalo, prova1.getLeituras().get(i).getTemp(),
						i < sizeProva2 ? prova2.getLeituras().get(i).getTemp() : 0));
			}
		} else {
			Date inicio = prova2.getLeituras().get(0).getDtProc();
			for (i = 0; i < sizeProva2; i++) {
				Date horario = prova2.getLeituras().get(i).getDtProc();
				long millis = horario.getTime() - inicio.getTime();
				LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
				Date intervalo = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
				models.add(new AnaliseComparativaModel(intervalo,
						i < sizeProva1 ? prova1.getLeituras().get(i).getTemp() : 0,
						prova2.getLeituras().get(i).getTemp()));
			}
		}

		try {
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
			report().setTemplate(AnaliseComparativaReportTemplate.reportTemplate)
					.title(AnaliseComparativaReportTemplate.createHeaderComponent(prova1, prova2),
							AnaliseComparativaReportTemplate.createSeparatorComponent(),
							AnaliseComparativaReportTemplate.createDadosComponent(prova1),
							AnaliseComparativaReportTemplate.createSeparatorComponent(),
							AnaliseComparativaReportTemplate.createDadosComponent(prova2),
							AnaliseComparativaReportTemplate.createSeparatorComponent(),
							AnaliseComparativaReportTemplate.createChartComponent(models, prova1.getNomeProva(), prova2.getNomeProva())
							)
					.setDataSource(models).columns(dataHoraColumn, temperaturaColumn, setPointColumn)
//					.setDataSource(prova2.getLeituras()).columns(dataHoraColumn, temperaturaColumn, setPointColumn)
					.toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
