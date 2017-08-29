package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.resicolor.lab.analythics.app.ControlledScreen;
import com.servicos.estatica.resicolor.lab.analythics.dao.ProvaDAO;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

@SuppressWarnings("rawtypes")
public class InicialController implements Initializable, ControlledScreen {

	@FXML
	private TableView tblAndamento;
	@FXML
	private TableColumn colNomeAndamento;
	@FXML
	private TableColumn colProjetoAndamento;
	@FXML
	private TableColumn colObjetivoAndamento;
	@FXML
	private TableColumn colExecutorAndamento;
	@FXML
	private TableColumn colBalaoAndamento;
	@FXML
	private TableColumn colNumeroAndamento;
	@FXML
	private TableColumn colInicioAndamento;
	@FXML
	private TableView tblRecentes;
	@FXML
	private TableColumn colNomeRecentes;
	@FXML
	private TableColumn colProjetoRecentes;
	@FXML
	private TableColumn colObjetivoRecentes;
	@FXML
	private TableColumn colExecutorRecentes;
	@FXML
	private TableColumn colBalaoRecentes;
	@FXML
	private TableColumn colNumeroRecentes;
	@FXML
	private TableColumn colInicioRecentes;
	@FXML
	private TableColumn colFinalRecentes;
	@FXML
	private ProgressIndicator progAndamento;
	@FXML
	private ProgressIndicator progRecentes;

	private static ObservableList<Prova> provasAndamento = FXCollections.observableArrayList();
	private static ObservableList<Prova> provasRecentes = FXCollections.observableArrayList();

	private static ProvaDAO provaDAO = new ProvaDAO();

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		findAndamento();
		findRecentes();
	}

	private void findAndamento() {
		tblAndamento.getItems().clear();
		progAndamento.setVisible(true);
		tblAndamento.setDisable(true);
		Task<Void> searchTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				provasAndamento = FXCollections.observableList((List<Prova>) provaDAO.findEmAndamento());
				return null;
			}
		};

		searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (!provasAndamento.isEmpty()) {
					populateTableAndamento();
				}
				progAndamento.setVisible(false);
				tblAndamento.setDisable(false);
			}

		});
		Thread t = new Thread(searchTask);
		t.start();
	}

	private void findRecentes() {
		tblRecentes.getItems().clear();
		progRecentes.setVisible(true);
		tblRecentes.setDisable(true);
		Task<Void> searchTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				provasRecentes = FXCollections.observableList((List<Prova>) provaDAO.findRecentes());
				return null;
			}
		};

		searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (!provasRecentes.isEmpty()) {
					populateTableRecentes();
				}
				progRecentes.setVisible(false);
				tblRecentes.setDisable(false);
			}

		});
		Thread t = new Thread(searchTask);
		t.start();
	}

	@SuppressWarnings("unchecked")
	private void populateTableAndamento() {
		tblAndamento.setItems(provasAndamento);
		colNomeAndamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getNomeProva());
						return simpleObject;
					}
				});
		colProjetoAndamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getProjeto().getNome());
						return simpleObject;
					}
				});
		colObjetivoAndamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getObjetivo());
						return simpleObject;
					}
				});
		colExecutorAndamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getExecutor());
						return simpleObject;
					}
				});
		colBalaoAndamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getBalao());
						return simpleObject;
					}
				});
		colNumeroAndamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getProduto());
						return simpleObject;
					}
				});
		colInicioAndamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(p.getDhInicial()));
						return simpleObject;
					}
				});
		colNomeAndamento.setStyle("-fx-alignment: CENTER;");
		colProjetoAndamento.setStyle("-fx-alignment: CENTER;");
		colObjetivoAndamento.setStyle("-fx-alignment: CENTER;");
		colExecutorAndamento.setStyle("-fx-alignment: CENTER;");
		colBalaoAndamento.setStyle("-fx-alignment: CENTER;");
		colNumeroAndamento.setStyle("-fx-alignment: CENTER;");
		colInicioAndamento.setStyle("-fx-alignment: CENTER;");
		tblAndamento.getColumns().setAll(colNomeAndamento, colProjetoAndamento, colObjetivoAndamento,
				colExecutorAndamento, colBalaoAndamento, colNumeroAndamento, colInicioAndamento);

	}

	@SuppressWarnings("unchecked")
	private void populateTableRecentes() {
		tblRecentes.setItems(provasRecentes);
		colNomeRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getNomeProva());
						return simpleObject;
					}
				});
		colProjetoRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getProjeto().getNome());
						return simpleObject;
					}
				});
		colObjetivoRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getObjetivo());
						return simpleObject;
					}
				});
		colExecutorRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getExecutor());
						return simpleObject;
					}
				});
		colBalaoRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getBalao());
						return simpleObject;
					}
				});
		colNumeroRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								p.getProduto());
						return simpleObject;
					}
				});
		colInicioRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(p.getDhInicial()));
						return simpleObject;
					}
				});
		colFinalRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(p.getDhFinal()));
						return simpleObject;
					}
				});
		colNomeRecentes.setStyle("-fx-alignment: CENTER;");
		colProjetoRecentes.setStyle("-fx-alignment: CENTER;");
		colObjetivoRecentes.setStyle("-fx-alignment: CENTER;");
		colExecutorRecentes.setStyle("-fx-alignment: CENTER;");
		colBalaoRecentes.setStyle("-fx-alignment: CENTER;");
		colNumeroRecentes.setStyle("-fx-alignment: CENTER;");
		colInicioRecentes.setStyle("-fx-alignment: CENTER;");
		colFinalRecentes.setStyle("-fx-alignment: CENTER;");
		tblRecentes.getColumns().setAll(colNomeRecentes, colProjetoRecentes, colObjetivoRecentes, colExecutorRecentes,
				colBalaoRecentes, colNumeroRecentes, colInicioRecentes, colFinalRecentes);

	}
}
