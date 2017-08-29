package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.resicolor.lab.analythics.app.ControlledScreen;
import com.servicos.estatica.resicolor.lab.analythics.dao.LeituraDAO;
import com.servicos.estatica.resicolor.lab.analythics.model.Leitura;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConsultaController implements Initializable, ControlledScreen {

	@FXML
	private Rectangle rectForm;
	@FXML
	private RadioButton rdProva;
	@FXML
	private RadioButton rdPeriodo;
	@FXML
	private RadioButton rdUltimos;
	@FXML
	private TextField txtProva;
	@FXML
	private DatePicker dtpInicio;
	@FXML
	private DatePicker dtpFinal;
	@FXML
	private Spinner<Integer> spnUltimos;
	@FXML
	private LineChart<String, Number> chartConsulta;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;

	private static XYChart.Series<String, Number> tempSeries;

	final ObservableList<XYChart.Series<String, Number>> plotValuesList = FXCollections.observableArrayList();

	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 10);

	ToggleGroup group = new ToggleGroup();

	private static String TOOLTIP_CSS = "-fx-font-size: 8pt; -fx-font-weight: bold; -fx-font-style: normal; ";
	private Tooltip tooltipChart = new Tooltip("Visualizar o gráfico do processo");
	private Tooltip tooltipReport = new Tooltip("Emitir um relatório em PDF");
	private Tooltip tooltipDelete = new Tooltip("Excluir o processo");

	private static DateTimeFormatter horasFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private static LeituraDAO leituraDAO = new LeituraDAO();

	private List<Leitura> leituras;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rectForm.setFill(Color.TRANSPARENT);
		spnUltimos.setValueFactory(valueFactory);
		rdProva.setToggleGroup(group);
		rdPeriodo.setToggleGroup(group);
		rdUltimos.setToggleGroup(group);
		rdUltimos.setSelected(true);
		configLineChart();
	}

	@FXML
	private void findByProvaName() {
		leituras = leituraDAO.findByProvaName(txtProva.getText());
		populateLineChart();
	}

	@FXML
	private void selectUltimos() {
		spnUltimos.setDisable(false);
		dtpInicio.getEditor().setText(null);
		dtpFinal.getEditor().setText(null);
		txtProva.setText(null);
		dtpInicio.setDisable(true);
		dtpFinal.setDisable(true);
		txtProva.setDisable(true);
	}

	@FXML
	private void selectPorPeriodo() {
		spnUltimos.setDisable(true);
		spnUltimos.getValueFactory().setValue(10);
		txtProva.setText(null);
		dtpInicio.setDisable(false);
		dtpFinal.setDisable(false);
		txtProva.setDisable(true);
	}

	@FXML
	private void selectPorProva() {
		dtpInicio.getEditor().setText(null);
		dtpFinal.getEditor().setText(null);
		spnUltimos.setDisable(true);
		spnUltimos.getValueFactory().setValue(10);
		dtpInicio.setDisable(true);
		dtpFinal.setDisable(true);
		txtProva.setDisable(false);

	}

	private void populateLineChart() {
		clearLineChart();
		chartConsulta.setLegendVisible(true);
		for (Leitura leitura : leituras) {
			LocalDateTime horario = leitura.getDtProc().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			XYChart.Data<String, Number> dataLeitura = new XYChart.Data<>(horasFormatter.format(horario),
					leitura.getTemp());
			// createLineMark(reatorMarks, dataReator, registro.getTempReator(),
			// "red");
			// tempSeries.setName(txtProva.getText() + ": Reator");
			// styleSeries();
			tempSeries.getData().add(dataLeitura);
		}
	}

	@SuppressWarnings("unchecked")
	private void configLineChart() {
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(350);
		yAxis.setTickUnit(25);
		tempSeries = new XYChart.Series<String, Number>();
		plotValuesList.addAll(tempSeries);
		chartConsulta.setData(plotValuesList);
	}

	@SuppressWarnings("unchecked")
	private void clearLineChart() {
		tempSeries.getData().clear();
		chartConsulta.getData().clear();
		tempSeries = new XYChart.Series<String, Number>();
		chartConsulta.getData().addAll(tempSeries);
	}

}
