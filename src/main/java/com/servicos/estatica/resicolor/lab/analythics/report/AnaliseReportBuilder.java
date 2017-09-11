package com.servicos.estatica.resicolor.lab.analythics.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.util.Date;

import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AnaliseReportBuilder {

	public static void buildSingle(Prova prova, String path) {

		TextColumnBuilder<Date> dataHoraColumn = col.column("Horário", "dtProc", type.timeHourToSecondType());
		TextColumnBuilder<Double> temperaturaColumn = col.column("Temperatura (ºC)", "temp", type.doubleType());
		TextColumnBuilder<Double> setPointColumn = col.column("Set-point (ºC)", "sp", type.doubleType());

		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(path);
		try {
			report().setTemplate(AnaliseReportTemplate.reportTemplate)
					.title(AnaliseReportTemplate.createHeaderComponent(prova),
							AnaliseReportTemplate.createSeparatorComponent(),
							AnaliseReportTemplate.createDadosComponent(prova, null, null),
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

	}
}
