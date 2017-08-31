package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;

public class ComparacaoController implements Initializable {

	@FXML
	private LineChart<String, Number> chartLeituras;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private CheckBox chkMarcadores;

	private static DateTimeFormatter dataHoraFormatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yy");
	private static DateTimeFormatter horasFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private static XYChart.Series<String, Number> tempSeries1;
	private static XYChart.Series<String, Number> tempSeries2;
	final ObservableList<XYChart.Series<String, Number>> plotValuesList = FXCollections.observableArrayList();
	final List<Node> valueMarks1 = new ArrayList<>();
	final List<Node> valueMarks2 = new ArrayList<>();

	private static LeituraDAO leituraDAO = new LeituraDAO();
	private static AmostraDAO amostraDAO = new AmostraDAO();
	private static List<Leitura> leituras1 = new ArrayList<>();
	private static List<Leitura> leituras2 = new ArrayList<>();
	private static List<Amostra> amostras = new ArrayList<>();

	private Prova prova1;
	private Prova prova2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setContext(Prova pr1, Prova pr2) {
		prova1 = pr1;
		prova2 = pr2;
		configLineChart();
		if (prova1 != null && prova2 != null) {
			consultarComparative();
			return;
		}
		if (prova1 != null) {
			consultarSingle1();
			return;
		}
		if (prova2 != null) {
			consultarSingle2();
			return;
		}
	}

	private void consultarSingle1() {
		Task<Void> leiturasTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				leituras1 = leituraDAO.findLeiturasByProva(prova1);
				return null;
			}
		};
		leiturasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (leituras1 != null && !leituras1.isEmpty()) {
					plotTemp(leituras1, tempSeries1, valueMarks1);
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
		Thread t = new Thread(leiturasTask);
		t.start();
	}

	private void consultarSingle2() {
		Task<Void> leiturasTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				leituras2 = leituraDAO.findLeiturasByProva(prova2);
				return null;
			}
		};
		leiturasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (leituras2 != null && !leituras2.isEmpty()) {
					plotTemp(leituras2, tempSeries2, valueMarks2);
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
		Thread t = new Thread(leiturasTask);
		t.start();
	}

	private void consultarComparative() {
		Task<Void> leiturasTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				leituras1 = leituraDAO.findLeiturasByProva(prova1);
				leituras2 = leituraDAO.findLeiturasByProva(prova2);
				return null;
			}
		};
		leiturasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (leituras1 != null && leituras2 != null) {
					populateComparative();
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
		Thread t = new Thread(leiturasTask);
		t.start();
	}

	@FXML
	public void toggleMarks() {
		valueMarks1.forEach(vm -> {
			vm.setVisible(chkMarcadores.isSelected());

		});
		valueMarks2.forEach(vm -> {
			vm.setVisible(chkMarcadores.isSelected());
		});
	}

	private void configLineChart() {
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(300);
		yAxis.setTickUnit(15);
		tempSeries1 = new XYChart.Series<String, Number>();
		tempSeries2 = new XYChart.Series<String, Number>();
		if (prova1 != null) {
			plotValuesList.add(tempSeries1);

		}
		if (prova2 != null) {
			plotValuesList.add(tempSeries2);

		}
		chartLeituras.setData(plotValuesList);
	}

	private void plotTemp(List<Leitura> leituras, XYChart.Series<String, Number> series, List<Node> marks) {
		leituras.forEach(l -> {
			final XYChart.Data<String, Number> data = new XYChart.Data<>(dataHoraFormatter
					.format(l.getDtProc().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()), l.getTemp());
			createLineMark(marks, data, l.getTemp(), "red");
			series.getData().add(data);
		});
		series.setName(leituras.get(0).getProvaLeituras().getNomeProva());
		chartLeituras.setLegendVisible(true);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				((Node) chartLeituras.lookupAll(".chart-legend-item-symbol").toArray()[0])
						.setStyle("-fx-background-color: red");

			}
		});
	}

	private void populateComparative() {
		if (leituras1.size() >= leituras2.size()) {
			LocalDateTime inicio = leituras1.get(0).getDtProc().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			int i = 0;
			for (Leitura leitura : leituras1) {
				LocalDateTime horario = leitura.getDtProc().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime();
				long millis = inicio.until(horario, ChronoUnit.MILLIS);
				LocalDateTime intervalo = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
				XYChart.Data<String, Number> dataLeituras1 = new XYChart.Data<>(horasFormatter.format(intervalo),
						leitura.getTemp());

				createLineMark(valueMarks1, dataLeituras1, leitura.getTemp(), "red");
				tempSeries1.setName(prova1.getNomeProva());
				tempSeries1.getData().add(dataLeituras1);

				if (leituras2.size() > i) {
					XYChart.Data<String, Number> dataLeituras2 = new XYChart.Data<>(horasFormatter.format(intervalo),
							leituras2.get(i).getTemp());

					createLineMark(valueMarks2, dataLeituras2, leituras2.get(i).getTemp(), "orange");
					tempSeries2.setName(prova2.getNomeProva());

					tempSeries2.getData().add(dataLeituras2);
				}
				i++;
			}

		} else {
			LocalDateTime inicio = leituras2.get(0).getDtProc().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			int i = 0;
			for (Leitura leitura : leituras2) {
				LocalDateTime horario = leitura.getDtProc().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime();
				long millis = inicio.until(horario, ChronoUnit.MILLIS);
				LocalDateTime intervalo = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
				XYChart.Data<String, Number> dataLeituras2 = new XYChart.Data<>(horasFormatter.format(intervalo),
						leitura.getTemp());

				createLineMark(valueMarks2, dataLeituras2, leitura.getTemp(), "orange");
				tempSeries2.setName(prova2.getNomeProva());

				tempSeries2.getData().add(dataLeituras2);
				if (leituras1.size() > i) {
					XYChart.Data<String, Number> dataLeituras1 = new XYChart.Data<>(horasFormatter.format(intervalo),
							leituras1.get(i).getTemp());

					createLineMark(valueMarks1, dataLeituras1, leituras1.get(i).getTemp(), "red");
					tempSeries1.setName(prova1.getNomeProva());

					tempSeries1.getData().add(dataLeituras1);
				}
				i++;
			}
		}
		chartLeituras.setLegendVisible(true);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				((Node) chartLeituras.lookupAll(".chart-legend-item-symbol").toArray()[0])
						.setStyle("-fx-background-color: red");
				((Node) chartLeituras.lookupAll(".chart-legend-item-symbol").toArray()[1])
						.setStyle("-fx-background-color: orange");

			}
		});
	}

	private void createLineMark(List<Node> nodes, Data<String, Number> data, Double value, String color) {
		Node mark = new HoverDataChart(1, value);
		mark.setStyle("-fx-background-color: " + color + ";");
		nodes.add(mark);
		data.setNode(mark);
		if (chkMarcadores.isSelected()) {
			mark.setVisible(Boolean.TRUE);
		} else {
			mark.setVisible(Boolean.FALSE);
		}
	}

}
