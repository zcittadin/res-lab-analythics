package com.servicos.estatica.resicolor.lab.analythics.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.tableOfContentsCustomizer;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.servicos.estatica.resicolor.lab.analythics.model.Prova;
import com.servicos.estatica.resicolor.lab.analythics.util.EnsaioUtil;

import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.chart.TimeSeriesChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.TimePeriod;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

public class AnaliseComparativaReportTemplate {

	public static final StyleBuilder rootStyle;
	public static final StyleBuilder boldStyle;
	public static final StyleBuilder italicStyle;
	public static final StyleBuilder boldCenteredStyle;
	public static final StyleBuilder bold12CenteredStyle;
	public static final StyleBuilder bold18CenteredStyle;
	public static final StyleBuilder bold22CenteredStyle;
	public static final StyleBuilder columnStyle;
	public static final StyleBuilder columnTitleStyle;
	public static final StyleBuilder groupStyle;
	public static final StyleBuilder subtotalStyle;

	public static final ReportTemplateBuilder reportTemplate;
	public static final ComponentBuilder<?, ?> separatorComponent;
	public static final ComponentBuilder<?, ?> footerComponent;
	private static DateTimeFormatter horasFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
	private static SimpleDateFormat horasSdf = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat dataSdf = new SimpleDateFormat("dd/MM/YYYY");

	static {
		rootStyle = stl.style().setPadding(2);
		boldStyle = stl.style(rootStyle).bold();
		italicStyle = stl.style(rootStyle).italic();
		boldCenteredStyle = stl.style(boldStyle).setTextAlignment(HorizontalTextAlignment.CENTER,
				VerticalTextAlignment.MIDDLE);
		bold12CenteredStyle = stl.style(boldCenteredStyle).setFontSize(12);
		bold18CenteredStyle = stl.style(boldCenteredStyle).setFontSize(18);
		bold22CenteredStyle = stl.style(boldCenteredStyle).setFontSize(22);
		columnStyle = stl.style(rootStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		columnTitleStyle = stl.style(columnStyle).setBorder(stl.pen1Point())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setBackgroundColor(Color.LIGHT_GRAY).bold();
		groupStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
		subtotalStyle = stl.style(boldStyle).setTopBorder(stl.pen1Point());

		StyleBuilder crosstabGroupStyle = stl.style(columnTitleStyle);
		StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(170, 170, 170));
		StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(140, 140, 140));
		StyleBuilder crosstabCellStyle = stl.style(columnStyle).setBorder(stl.pen1Point());
		TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer().setHeadingStyle(0,
				stl.style(rootStyle).bold());

		reportTemplate = template().setLocale(Locale.getDefault()).setColumnStyle(columnStyle)
				.setColumnTitleStyle(columnTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle)
				.setSubtotalStyle(subtotalStyle).highlightDetailEvenRows().crosstabHighlightEvenRows()
				.setCrosstabGroupStyle(crosstabGroupStyle).setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
				.setCrosstabGrandTotalStyle(crosstabGrandTotalStyle).setCrosstabCellStyle(crosstabCellStyle)
				.setTableOfContentsCustomizer(tableOfContentsCustomizer);

		separatorComponent = cmp.horizontalList(cmp.verticalList(cmp.verticalGap(10), cmp.line(), cmp.verticalGap(10)));
		footerComponent = cmp.pageNumber().setStyle(stl.style(boldCenteredStyle).setTopBorder(stl.pen1Point()));
	}

	public static ComponentBuilder<?, ?> createHeaderComponent() {
		return cmp.horizontalList(
				cmp.image(AnaliseReportTemplate.class.getResource("/com/servicos/estatica/resicolor/lab/analythics/style/wtech.png"))
				.setFixedDimension(100, 100),
				cmp.text("Relat�rio de ensaio laboratorial (comparativo)").setStyle(bold22CenteredStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
				cmp.horizontalGap(10)
				);
	}
	
	public static ComponentBuilder<?, ?> createDadosComponent(Prova prova) {
		return cmp.verticalList(
				cmp.horizontalList(
						cmp.text("Projeto: " + prova.getProjeto().getNome()).setStyle(boldStyle.setFontSize(12))
							.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)),
						cmp.horizontalList(
								cmp.text("Prova: " + prova.getNomeProva()).setStyle(boldStyle.setFontSize(12))
								.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)),
				cmp.horizontalList(
				cmp.verticalList(
						cmp.text("Data de realiza��o: " + dataSdf.format(prova.getDhInicial())),
						cmp.text("Produto: " + prova.getProduto()),
						cmp.text("Bal�o: " + prova.getBalao())),
				cmp.verticalList(
						cmp.text("Hor�rio de in�cio: " + horasSdf.format(prova.getDhInicial())),
						cmp.text("Hor�rio de encerramento: " + horasSdf.format(prova.getDhFinal())),
						cmp.text("Tempo decorrido: " + EnsaioUtil.formatPeriod(prova.getDhInicial(), prova.getDhFinal()))),
				cmp.verticalList(
						cmp.text("Executor: " + (prova.getExecutor())),
						cmp.text("Temperatura m�nima: " + EnsaioUtil.getTempMin(prova) + " �C"),
						cmp.text("Temperatura m�xima: " + EnsaioUtil.getTempMax(prova) + " �C"))));
	}
	
	public static ComponentBuilder<?, ?> createChartComponent(List<AnaliseComparativaModel> models, String prova1, String prova2) {
		return cmp.horizontalList(createLineChart(models, prova1, prova2));
	}
	
	private static TimeSeriesChartBuilder createLineChart(List<AnaliseComparativaModel> models, String prova1, String prova2) {
		FontBuilder boldFont = stl.fontArialBold().setFontSize(10);
		TextColumnBuilder<Date> horarioColumn = col.column("Hor�rio", "intervalo", type.dateDayType());
		TextColumnBuilder<Double> temp1Column = col.column("Prova: " + prova1, 
				"temp1", type.doubleType());
		TextColumnBuilder<Double> temp2Column = col.column("Prova: " + prova2, 
				"temp2", type.doubleType());
		
		TimeSeriesChartBuilder builder = cht.timeSeriesChart()
				.setTitle("Temperatura x tempo")
				.setTitleFont(boldFont)
				.setShowShapes(false)
				.setShowLegend(Boolean.TRUE)
				.setTimePeriod(horarioColumn)
				.setTimePeriodType(TimePeriod.SECOND)
				.setTimeAxisFormat(cht.axisFormat())
				.setValueAxisFormat(
						cht.axisFormat()
						.setLabel("�C")
						.setRangeMaxValueExpression(350)
						.setRangeMinValueExpression(0)
						);
		
		return builder.series(
				cht.serie(temp1Column),
				cht.serie(temp2Column)
				).seriesColors(Color.RED, Color.ORANGE);
		
	}

	public static ComponentBuilder<?, ?> createSeparatorComponent() {
		return separatorComponent;
	}
	
	public static ComponentBuilder<?, ?> createEmissaoComponent() {
		return cmp.horizontalList(cmp.verticalList(separatorComponent,
				cmp.text("Data de emiss�o: " + horasFormatter.format(LocalDateTime.now()))
						.setStyle(stl.style().setFontSize(10))
						.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)));
	}

}
