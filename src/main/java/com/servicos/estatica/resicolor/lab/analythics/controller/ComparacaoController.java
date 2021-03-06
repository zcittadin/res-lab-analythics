package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.servicos.estatica.resicolor.lab.analythics.dao.AmostraDAO;
import com.servicos.estatica.resicolor.lab.analythics.dao.LeituraDAO;
import com.servicos.estatica.resicolor.lab.analythics.model.Amostra;
import com.servicos.estatica.resicolor.lab.analythics.model.Leitura;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;
import com.servicos.estatica.resicolor.lab.analythics.report.AmostrasReportBuilder;
import com.servicos.estatica.resicolor.lab.analythics.report.AnaliseReportBuilder;
import com.servicos.estatica.resicolor.lab.analythics.report.xls.XlsGenerator;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("rawtypes")
public class ComparacaoController implements Initializable {

	@FXML
	private Rectangle rectReportLeituras;
	@FXML
	private Rectangle rectReportAmostras;
	@FXML
	private Rectangle rectProvas1;
	@FXML
	private Rectangle rectProvas2;
	@FXML
	private Label lblProva1;
	@FXML
	private Label lblTeorSolidos1;
	@FXML
	private Label lblViscosidade1;
	@FXML
	private Label lblCorGardner1;
	@FXML
	private Label lblIndiceAcid1;
	@FXML
	private Label lblTeorOh1;
	@FXML
	private Label lblPh1;
	@FXML
	private Label lblProva2;
	@FXML
	private Label lblTeorSolidos2;
	@FXML
	private Label lblViscosidade2;
	@FXML
	private Label lblCorGardner2;
	@FXML
	private Label lblIndiceAcid2;
	@FXML
	private Label lblTeorOh2;
	@FXML
	private Label lblPh2;
	@FXML
	private TextArea txtAdicionais1;
	@FXML
	private TextArea txtAdicionais2;
	@FXML
	private TabPane tabMain;
	@FXML
	private TableView tblAmostras1;
	@FXML
	private TableColumn colHorario1;
	@FXML
	private TableColumn colTemp1;
	@FXML
	private TableColumn colSetPoint1;
	@FXML
	private TableColumn colIAsobreNV1;
	@FXML
	private TableColumn colViscGardner1;
	@FXML
	private TableColumn colCorGardner1;
	@FXML
	private TableColumn colNv1;
	@FXML
	private TableColumn colGelTime1;
	@FXML
	private TableColumn colAgua1;
	@FXML
	private TableColumn colAmostra1;
	@FXML
	private TableColumn colPh1;
	@FXML
	private TableColumn colDescricao1;
	@FXML
	private TableView tblAmostras2;
	@FXML
	private TableColumn colHorario2;
	@FXML
	private TableColumn colTemp2;
	@FXML
	private TableColumn colSetPoint2;
	@FXML
	private TableColumn colIAsobreNV2;
	@FXML
	private TableColumn colViscGardner2;
	@FXML
	private TableColumn colCorGardner2;
	@FXML
	private TableColumn colNv2;
	@FXML
	private TableColumn colGelTime2;
	@FXML
	private TableColumn colAgua2;
	@FXML
	private TableColumn colAmostra2;
	@FXML
	private TableColumn colPh2;
	@FXML
	private TableColumn colDescricao2;
	@FXML
	private LineChart<String, Number> chartLeituras;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private CheckBox chkMarcadores;
	@FXML
	private Button btXlsLeituras;
	@FXML
	private Button btPdfLeituras;
	@FXML
	private Button btXlsAmostras;
	@FXML
	private Button btPdfAmostras;
	@FXML
	private ProgressIndicator progReport;

	private static DateTimeFormatter dataHoraFormatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yy");
	private static DateTimeFormatter horasFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private static SimpleDateFormat dataHoraSdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
	private static XYChart.Series<String, Number> tempSeries1;
	private static XYChart.Series<String, Number> tempSeries2;
	final ObservableList<XYChart.Series<String, Number>> plotValuesList = FXCollections.observableArrayList();
	final List<Node> valueMarks1 = new ArrayList<>();
	final List<Node> valueMarks2 = new ArrayList<>();

	private static LeituraDAO leituraDAO = new LeituraDAO();
	private static AmostraDAO amostraDAO = new AmostraDAO();
	private static List<Leitura> leituras1 = new ArrayList<>();
	private static List<Leitura> leituras2 = new ArrayList<>();
	private static ObservableList<Amostra> amostras1 = FXCollections.observableArrayList();
	private static ObservableList<Amostra> amostras2 = FXCollections.observableArrayList();

	private Prova prova1;
	private Prova prova2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rectReportLeituras.setFill(Color.TRANSPARENT);
		rectReportAmostras.setFill(Color.TRANSPARENT);
		rectProvas1.setFill(Color.TRANSPARENT);
		rectProvas1.setFill(Color.TRANSPARENT);
		rectProvas2.setFill(Color.TRANSPARENT);
	}

	public void setContext(Prova pr1, Prova pr2) {
		prova1 = pr1;
		prova2 = pr2;
		if (prova1 == null && prova2 == null) {
			btXlsLeituras.setDisable(true);
			btPdfLeituras.setDisable(true);
			btXlsAmostras.setDisable(true);
			btPdfAmostras.setDisable(true);
			lblProva1.setText("");
			lblCorGardner1.setText("");
			lblIndiceAcid1.setText("");
			lblPh1.setText("");
			lblTeorOh1.setText("");
			lblTeorSolidos1.setText("");
			lblViscosidade1.setText("");
			txtAdicionais1.setText("");
			lblProva2.setText("");
			lblCorGardner2.setText("");
			lblIndiceAcid2.setText("");
			lblPh2.setText("");
			lblTeorOh2.setText("");
			lblTeorSolidos2.setText("");
			lblViscosidade2.setText("");
			txtAdicionais2.setText("");
		}
		configLineChart();
		if (prova1 != null && prova2 != null) {
			lblProva1.setText(prova1.getNomeProva());
			lblCorGardner1.setText(prova1.getProjeto().getCorGardner());
			lblIndiceAcid1.setText(prova1.getProjeto().getIndiceAcidez());
			lblPh1.setText(prova1.getProjeto().getPh());
			lblTeorOh1.setText(prova1.getProjeto().getTeorOh());
			lblTeorSolidos1.setText(prova1.getProjeto().getTeorSolidos());
			lblViscosidade1.setText(prova1.getProjeto().getViscosidade());
			txtAdicionais1.setText(prova1.getProjeto().getDadosAdd());
			lblProva2.setText(prova2.getNomeProva());
			lblCorGardner2.setText(prova2.getProjeto().getCorGardner());
			lblIndiceAcid2.setText(prova2.getProjeto().getIndiceAcidez());
			lblPh2.setText(prova2.getProjeto().getPh());
			lblTeorOh2.setText(prova2.getProjeto().getTeorOh());
			lblTeorSolidos2.setText(prova2.getProjeto().getTeorSolidos());
			lblViscosidade2.setText(prova2.getProjeto().getViscosidade());
			txtAdicionais2.setText(prova2.getProjeto().getDadosAdd());
			consultarComparative();
			return;
		}
		if (prova1 != null) {
			lblProva1.setText(prova1.getNomeProva());
			lblCorGardner1.setText(prova1.getProjeto().getCorGardner());
			lblIndiceAcid1.setText(prova1.getProjeto().getIndiceAcidez());
			lblPh1.setText(prova1.getProjeto().getPh());
			lblTeorOh1.setText(prova1.getProjeto().getTeorOh());
			lblTeorSolidos1.setText(prova1.getProjeto().getTeorSolidos());
			lblViscosidade1.setText(prova1.getProjeto().getViscosidade());
			txtAdicionais1.setText(prova1.getProjeto().getDadosAdd());
			lblProva2.setText("");
			lblCorGardner2.setText("");
			lblIndiceAcid2.setText("");
			lblPh2.setText("");
			lblTeorOh2.setText("");
			lblTeorSolidos2.setText("");
			lblViscosidade2.setText("");
			txtAdicionais2.setText("");
			consultarSingle1();
			return;
		}
		if (prova2 != null) {
			lblProva2.setText(prova2.getNomeProva());
			lblCorGardner2.setText(prova2.getProjeto().getCorGardner());
			lblIndiceAcid2.setText(prova2.getProjeto().getIndiceAcidez());
			lblPh2.setText(prova2.getProjeto().getPh());
			lblTeorOh2.setText(prova2.getProjeto().getTeorOh());
			lblTeorSolidos2.setText(prova2.getProjeto().getTeorSolidos());
			lblViscosidade2.setText(prova2.getProjeto().getViscosidade());
			txtAdicionais2.setText(prova2.getProjeto().getDadosAdd());
			lblProva1.setText("");
			lblCorGardner1.setText("");
			lblIndiceAcid1.setText("");
			lblPh1.setText("");
			lblTeorOh1.setText("");
			lblTeorSolidos1.setText("");
			lblViscosidade1.setText("");
			txtAdicionais1.setText("");
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
		Thread t1 = new Thread(leiturasTask);
		t1.start();

		Task<Void> amostrasTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				amostras1 = FXCollections.observableList((List<Amostra>) amostraDAO.findAmostraByProva(prova1));
				return null;
			}
		};

		amostrasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (!amostras1.isEmpty()) {
					populateTableAmostras1();
				}
			}

		});
		Thread t2 = new Thread(amostrasTask);
		t2.start();
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
		Thread t1 = new Thread(leiturasTask);
		t1.start();

		Task<Void> amostrasTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				amostras2 = FXCollections.observableList((List<Amostra>) amostraDAO.findAmostraByProva(prova2));
				return null;
			}
		};

		amostrasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (!amostras2.isEmpty()) {
					populateTableAmostras2();
				}
			}

		});
		Thread t2 = new Thread(amostrasTask);
		t2.start();
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
				if ((leituras1 != null && !leituras1.isEmpty()) && (leituras2 != null && !leituras2.isEmpty())) {
					populateChartComparative();
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
				amostras1 = FXCollections.observableList((List<Amostra>) amostraDAO.findAmostraByProva(prova1));
				amostras2 = FXCollections.observableList((List<Amostra>) amostraDAO.findAmostraByProva(prova2));
				return null;
			}
		};

		amostrasTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (amostras1 != null && !amostras1.isEmpty()) {
					populateTableAmostras1();
				}
				if (amostras2 != null && !amostras2.isEmpty()) {
					populateTableAmostras2();
				}
			}

		});
		Thread t2 = new Thread(amostrasTask);
		t2.start();
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

	private void populateChartComparative() {
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

	@SuppressWarnings("unchecked")
	private void populateTableAmostras1() {
		tblAmostras1.setItems(amostras1);
		colHorario1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject;
						simpleObject = new SimpleObjectProperty<String>(
								new SimpleDateFormat("HH:mm:ss").format(a.getHorario()));
						return simpleObject;
					}
				});
		colTemp1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getTemp());
						return simpleObject;
					}
				});
		colSetPoint1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getSetPoint());
						return simpleObject;
					}
				});
		colIAsobreNV1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getIaSobreNv());
						return simpleObject;
					}
				});
		colViscGardner1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getViscGardner());
						return simpleObject;
					}
				});
		colCorGardner1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								// "");
								a.getCorGardner());
						return simpleObject;
					}
				});
		colNv1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getPercentualNv());
						return simpleObject;
					}
				});
		colGelTime1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getGelTime());
						return simpleObject;
					}
				});
		colAgua1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getAgua());
						return simpleObject;
					}
				});
		colAmostra1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getAmostra());
						return simpleObject;
					}
				});
		colPh1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getPh());
						return simpleObject;
					}
				});
		colDescricao1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getDescricao());
						return simpleObject;
					}
				});

		colAgua1.setStyle("-fx-alignment: CENTER;");
		colAmostra1.setStyle("-fx-alignment: CENTER;");
		colCorGardner1.setStyle("-fx-alignment: CENTER;");
		colDescricao1.setStyle("-fx-alignment: CENTER;");
		colGelTime1.setStyle("-fx-alignment: CENTER;");
		colHorario1.setStyle("-fx-alignment: CENTER;");
		colIAsobreNV1.setStyle("-fx-alignment: CENTER;");
		colNv1.setStyle("-fx-alignment: CENTER;");
		colPh1.setStyle("-fx-alignment: CENTER;");
		colSetPoint1.setStyle("-fx-alignment: CENTER;");
		colTemp1.setStyle("-fx-alignment: CENTER;");
		colViscGardner1.setStyle("-fx-alignment: CENTER;");
	}

	@SuppressWarnings("unchecked")
	private void populateTableAmostras2() {
		tblAmostras2.setItems(amostras2);
		colHorario2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject;
						simpleObject = new SimpleObjectProperty<String>(
								new SimpleDateFormat("HH:mm:ss").format(a.getHorario()));
						return simpleObject;
					}
				});
		colTemp2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getTemp());
						return simpleObject;
					}
				});
		colSetPoint2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getSetPoint());
						return simpleObject;
					}
				});
		colIAsobreNV2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getIaSobreNv());
						return simpleObject;
					}
				});
		colViscGardner2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getViscGardner());
						return simpleObject;
					}
				});
		colCorGardner2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								// "");
								a.getCorGardner());
						return simpleObject;
					}
				});
		colNv2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getPercentualNv());
						return simpleObject;
					}
				});
		colGelTime2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getGelTime());
						return simpleObject;
					}
				});
		colAgua2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getAgua());
						return simpleObject;
					}
				});
		colAmostra2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								a.getAmostra());
						return simpleObject;
					}
				});
		colPh2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Amostra, Double> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(a.getPh());
						return simpleObject;
					}
				});
		colDescricao2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amostra, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Amostra, String> cell) {
						final Amostra a = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								a.getDescricao());
						return simpleObject;
					}
				});

		colAgua2.setStyle("-fx-alignment: CENTER;");
		colAmostra2.setStyle("-fx-alignment: CENTER;");
		colCorGardner2.setStyle("-fx-alignment: CENTER;");
		colDescricao2.setStyle("-fx-alignment: CENTER;");
		colGelTime2.setStyle("-fx-alignment: CENTER;");
		colHorario2.setStyle("-fx-alignment: CENTER;");
		colIAsobreNV2.setStyle("-fx-alignment: CENTER;");
		colNv2.setStyle("-fx-alignment: CENTER;");
		colPh2.setStyle("-fx-alignment: CENTER;");
		colSetPoint2.setStyle("-fx-alignment: CENTER;");
		colTemp2.setStyle("-fx-alignment: CENTER;");
		colViscGardner2.setStyle("-fx-alignment: CENTER;");
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

	@FXML
	private void saveXlsLeituras() {
		Stage stage = new Stage();
		stage.initOwner(tblAmostras1.getScene().getWindow());
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XLS Files", "*.xls"));
		fileChooser.setTitle("Salvar planilha de leituras");
		File savedFile = fileChooser.showSaveDialog(stage);
		if (savedFile != null) {
			generateXlsLeituras(savedFile);
		}
	}

	private void generateXlsLeituras(File file) {
		fetch(true);
		Task<Void> xlsTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				int maximum = 20;
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet firstSheet = workbook.createSheet("Aba1");
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file);
					int line = 0;
					if (prova1 != null) {
						HSSFRow headerRowProjetoA = firstSheet.createRow(line);
						headerRowProjetoA.createCell(0).setCellValue("Projeto " + prova1.getProjeto().getNome());
						line++;
						HSSFRow headerRowProvaA = firstSheet.createRow(line);
						headerRowProvaA.createCell(0).setCellValue("Prova " + prova1.getNomeProva());
						line++;
						HSSFRow titleRowA = firstSheet.createRow(line);
						line++;
						titleRowA.createCell(0).setCellValue("Hor�rio");
						titleRowA.createCell(1).setCellValue("Temperatura");
						titleRowA.createCell(2).setCellValue("Set-point");
						for (Leitura leitura : leituras1) {
							HSSFRow rowA = firstSheet.createRow(line);
							rowA.createCell(0).setCellValue(dataHoraSdf.format(leitura.getDtProc()));
							rowA.createCell(1).setCellValue(leitura.getTemp());
							rowA.createCell(2).setCellValue(leitura.getSp());
							line++;
						}
					}
					if (prova2 != null) {
						HSSFRow blankRow = firstSheet.createRow(line);
						blankRow = firstSheet.createRow(line);
						CellStyle cellStyle = workbook.createCellStyle();
						cellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
						cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
						blankRow.setRowStyle(cellStyle);
						line++;
						HSSFRow headerProjetoRowB = firstSheet.createRow(line);
						headerProjetoRowB.createCell(0).setCellValue("Projeto " + prova2.getProjeto().getNome());
						line++;
						HSSFRow headerProvaRowB = firstSheet.createRow(line);
						headerProvaRowB.createCell(0).setCellValue("Prova " + prova2.getNomeProva());
						line++;
						HSSFRow titleRowB = firstSheet.createRow(line);
						line++;
						titleRowB.createCell(0).setCellValue("Hor�rio");
						titleRowB.createCell(1).setCellValue("Temperatura");
						titleRowB.createCell(2).setCellValue("Set-point");
						for (Leitura leitura : leituras2) {
							HSSFRow rowB = firstSheet.createRow(line);
							rowB.createCell(0).setCellValue(dataHoraSdf.format(leitura.getDtProc()));
							rowB.createCell(1).setCellValue(leitura.getTemp());
							rowB.createCell(2).setCellValue(leitura.getSp());
							line++;
						}
					}
					workbook.write(fos);
					for (int i = 0; i < maximum; i++) {
						updateProgress(i, maximum);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Erro ao exportar arquivo");
				} finally {
					try {
						fos.flush();
						fos.close();
						workbook.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		};
		xlsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				fetch(false);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conclu�do");
				alert.setHeaderText("Planilha de dados emitida com sucesso. Deseja visualizar?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		xlsTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				fetch(false);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Houve uma falha na gera��o do arquivo.");
				alert.showAndWait();
			}
		});
		// progReport.progressProperty().bind(xlsTask.progressProperty());
		new Thread(xlsTask).start();
	}

	@FXML
	private void saveXlsAmostras() {
		Stage stage = new Stage();
		stage.initOwner(tblAmostras1.getScene().getWindow());
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XLS Files", "*.xls"));
		fileChooser.setTitle("Salvar planilha de amostras");
		File savedFile = fileChooser.showSaveDialog(stage);
		if (savedFile != null) {
			fetch(true);
			XlsGenerator generator = new XlsGenerator();
			Task<Void> xlsTask = generator.generate(savedFile, prova1, prova2, amostras1, amostras2);
			Thread t = new Thread(xlsTask);
			t.start();
			xlsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent arg0) {
					fetch(false);
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Conclu�do");
					alert.setHeaderText("Planilha de dados emitida com sucesso. Deseja visualizar?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						try {
							Desktop.getDesktop().open(savedFile);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			});
			xlsTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					fetch(false);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setHeaderText("Houve uma falha na gera��o do arquivo.");
					alert.showAndWait();
				}
			});
		}
	}

	@FXML
	private void saveReportLeituras() {
		Stage stage = new Stage();
		stage.initOwner(tblAmostras1.getScene().getWindow());
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
		fileChooser.setTitle("Salvar relat�rio de leituras");
		// fileChooser.setInitialFileName("lote_" + produto.getLote() + ".pdf");
		File savedFile = fileChooser.showSaveDialog(stage);
		if (savedFile != null) {
			generatePdfReportLeituras(savedFile);
		}
	}

	private void generatePdfReportLeituras(File file) {
		fetch(true);
		Task<Void> reportTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (prova1 != null && prova2 == null) {
					AnaliseReportBuilder.buildSingle(prova1, file.getAbsolutePath());
					return null;
				}
				if (prova2 != null && prova1 == null) {
					AnaliseReportBuilder.buildSingle(prova2, file.getAbsolutePath());
					return null;
				}
				AnaliseReportBuilder.buildComparative(prova1, prova2, file.getAbsolutePath());
				return null;
			}
		};

		reportTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				fetch(false);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conclu�do");
				alert.setHeaderText("Relat�rio emitido com sucesso. Deseja visualizar?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		reportTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				fetch(false);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Houve uma falha na emiss�o do relat�rio.");
				alert.showAndWait();
			}
		});
		Thread t = new Thread(reportTask);
		t.start();
	}

	@FXML
	private void saveReportAmostras() {
		List<Amostra> amostras1;
		List<Amostra> amostras2;
		if (prova1 != null) {
			amostras1 = amostraDAO.findAmostraByProva(prova1);
			prova1.setAmostras(amostras1);
		}
		if (prova2 != null) {
			amostras2 = amostraDAO.findAmostraByProva(prova2);
			prova2.setAmostras(amostras2);
		}
		Stage stage = new Stage();
		stage.initOwner(tblAmostras1.getScene().getWindow());
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
		fileChooser.setTitle("Salvar relat�rio");
		File savedFile = fileChooser.showSaveDialog(stage);
		if (savedFile != null) {
			generatePdfReportAmostras(savedFile);
		}
	}

	private void generatePdfReportAmostras(File file) {
		fetch(true);
		Task<Void> reportTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (prova1 != null && prova2 == null) {
					AmostrasReportBuilder.buildSingle(prova1, file.getAbsolutePath());
					return null;
				}
				if (prova2 != null && prova1 == null) {
					AmostrasReportBuilder.buildSingle(prova2, file.getAbsolutePath());
					return null;
				}
				AmostrasReportBuilder.buildComparative(prova1, prova2, file.getAbsolutePath());
				return null;
			}
		};

		reportTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				fetch(false);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conclu�do");
				alert.setHeaderText("Relat�rio emitido com sucesso. Deseja visualizar?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		reportTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				fetch(false);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Houve uma falha na emiss�o do relat�rio.");
				alert.showAndWait();
			}
		});
		Thread t = new Thread(reportTask);
		t.start();
	}

	private void fetch(Boolean b) {
		tblAmostras1.setDisable(b);
		tblAmostras2.setDisable(b);
		chartLeituras.setDisable(b);
		btPdfLeituras.setDisable(b);
		btXlsLeituras.setDisable(b);
		btPdfAmostras.setDisable(b);
		btXlsAmostras.setDisable(b);
		progReport.setVisible(b);
	}

}
