package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.resicolor.lab.analythics.dao.AmostraDAO;
import com.servicos.estatica.resicolor.lab.analythics.dao.LeituraDAO;
import com.servicos.estatica.resicolor.lab.analythics.model.Amostra;
import com.servicos.estatica.resicolor.lab.analythics.model.Leitura;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;
import com.servicos.estatica.resicolor.lab.analythics.util.HoverDataChart;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;

@SuppressWarnings("rawtypes")
public class ProvaDetalhesController implements Initializable {

	@FXML
	private LineChart chartLeituras;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private CheckBox chkMarcadores;
	@FXML
	private TableView tblAmostras;
	@FXML
	private TableColumn colHora;
	@FXML
	private TableColumn colTemp;
	@FXML
	private TableColumn colSetPoint;
	@FXML
	private TableColumn colIASobreNV;
	@FXML
	private TableColumn colViscGardner;
	@FXML
	private TableColumn colCorGardner;
	@FXML
	private TableColumn colNv;
	@FXML
	private TableColumn colGelTime;
	@FXML
	private TableColumn colAgua;
	@FXML
	private TableColumn colAmostra;
	@FXML
	private TableColumn colPh;
	@FXML
	private TableColumn colDescricao;

	private static XYChart.Series<String, Number> tempSeries;
	private static DateTimeFormatter dataHoraFormatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yy");

	final ObservableList<XYChart.Series<String, Number>> plotValuesList = FXCollections.observableArrayList();
	private static ObservableList<Amostra> amostras = FXCollections.observableArrayList();
	private static LeituraDAO leituraDAO = new LeituraDAO();
	private static AmostraDAO amostraDAO = new AmostraDAO();
	private static List<Leitura> leituras;
	final List<Node> valueMarks = new ArrayList<>();
	private Prova prova;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configLineChart();
	}

	public void setContext(Prova prova) {
		this.prova = prova;
		consultar();
	}

	private void consultar() {

		Task<Void> leiturasTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				leituras = leituraDAO.findLeiturasByProva(prova);
				return null;
			}
		};

		leiturasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (!leituras.isEmpty()) {
					for (Leitura leitura : leituras) {
						plotTemp(leitura.getTemp(), leitura.getDtProc());
					}
				}
			}

		});
		leiturasTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Falha");
				alert.setHeaderText("Ocorreu um erro ao consultar os dados.");
				alert.showAndWait();
			}
		});
		Thread t1 = new Thread(leiturasTask);
		t1.start();

		Task<Void> amostrasTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				amostras = FXCollections.observableList((List<Amostra>) amostraDAO.findAmostraByProva(prova));
				return null;
			}
		};

		amostrasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (!amostras.isEmpty()) {
					populateTableAmostras();
				}
			}

		});
		Thread t2 = new Thread(amostrasTask);
		t2.start();

	}

	@FXML
	public void toggleMarks() {
		for (Node mark : valueMarks) {
			mark.setVisible(chkMarcadores.isSelected());
		}
	}

	private void plotTemp(Double temperatura, Date dtProc) {
		final XYChart.Data<String, Number> data = new XYChart.Data<>(
				dataHoraFormatter.format(dtProc.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
				temperatura);
		Node mark = new HoverDataChart(1, temperatura);
		mark.setVisible(chkMarcadores.isSelected());
		mark.setStyle("-fx-background-color: red;");
		valueMarks.add(mark);
		data.setNode(mark);
		tempSeries.getData().add(data);
	}
	
	@SuppressWarnings("unchecked")
	private void configLineChart() {
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(300);
		yAxis.setTickUnit(15);
		tempSeries = new XYChart.Series<String, Number>();
		plotValuesList.add(tempSeries);
		chartLeituras.setData(plotValuesList);
	}

	@SuppressWarnings("unchecked")
	private void populateTableAmostras() {
		tblAmostras.setItems(amostras);

		colHora.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject;
						simpleObject = new SimpleObjectProperty<String>(
								new SimpleDateFormat("HH:mm:ss").format(a.getHorario()));
						return simpleObject;
					}
				});
		colTemp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getTemp());
						return simpleObject;
					}
				});
		colSetPoint.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getSetPoint());
						return simpleObject;
					}
				});
		colIASobreNV.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getIaSobreNv());
						return simpleObject;
					}
				});
		colViscGardner.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getViscGardner());
						return simpleObject;
					}
				});
		colCorGardner.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								// "");
								a.getCorGardner());
						return simpleObject;
					}
				});
		colNv.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getPercentualNv());
						return simpleObject;
					}
				});
		colGelTime.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Integer>, ObservableValue<Integer>>() {
					public ObservableValue<Integer> call(CellDataFeatures<Amostra, Integer> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Integer> simpleObject = new SimpleObjectProperty<Integer>(
								a.getGelTime());
						return simpleObject;
					}
				});
		colAgua.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getAgua());
						return simpleObject;
					}
				});
		colAmostra.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getAmostra());
						return simpleObject;
					}
				});
		colPh.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getPh());
						return simpleObject;
					}
				});
		colDescricao.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getDescricao());
						return simpleObject;
					}
				});

		colHora.setStyle("-fx-alignment: CENTER;");
		colTemp.setStyle("-fx-alignment: CENTER;");
		colSetPoint.setStyle("-fx-alignment: CENTER;");
		colIASobreNV.setStyle("-fx-alignment: CENTER;");
		colViscGardner.setStyle("-fx-alignment: CENTER;");
		colCorGardner.setStyle("-fx-alignment: CENTER;");
		colNv.setStyle("-fx-alignment: CENTER;");
		colGelTime.setStyle("-fx-alignment: CENTER;");
		colAgua.setStyle("-fx-alignment: CENTER;");
		colAmostra.setStyle("-fx-alignment: CENTER;");
		colPh.setStyle("-fx-alignment: CENTER;");
		colDescricao.setStyle("-fx-alignment: CENTER;");
		tblAmostras.getColumns().setAll(colHora, colTemp, colSetPoint, colIASobreNV, colViscGardner, colCorGardner,
				colNv, colGelTime, colAgua, colAmostra, colPh, colDescricao);

	}

}
