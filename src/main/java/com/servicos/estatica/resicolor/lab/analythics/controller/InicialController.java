package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	private TableColumn colFinalRecentes;
	@FXML
	private TableColumn colDetalharRecentes;
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
				provasRecentes = FXCollections.observableList((List<Prova>) provaDAO.findRecentes(8));
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
		colFinalRecentes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prova, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Prova, String> cell) {
						final Prova p = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(p.getDhFinal()));
						return simpleObject;
					}
				});

		colDetalharRecentes.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

		Callback<TableColumn<Prova, String>, TableCell<Prova, String>> cellDetalharFactory = //
				new Callback<TableColumn<Prova, String>, TableCell<Prova, String>>() {
					@Override
					public TableCell call(final TableColumn<Prova, String> param) {
						final TableCell<Prova, String> cell = new TableCell<Prova, String>() {

							final Button btn = new Button();

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(event -> {

										try {
											Stage stage;
											Parent root;
											stage = new Stage();
											URL url = getClass()
													.getResource("/com/servicos/estatica/resicolor/lab/analythics/app/ProvaDetalhes.fxml");
											FXMLLoader fxmlloader = new FXMLLoader();
											fxmlloader.setLocation(url);
											fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
											root = (Parent) fxmlloader.load(url.openStream());
											stage.setScene(new Scene(root));
											stage.setTitle("Visualização gráfica do processo");
											stage.initModality(Modality.APPLICATION_MODAL);
											stage.initOwner(tblAndamento.getScene().getWindow());
											stage.setResizable(Boolean.FALSE);
											 ((ProvaDetalhesController) fxmlloader.getController())
											 .setContext(getTableView().getItems().get(getIndex()));
											stage.showAndWait();
										} catch (IOException e) {
											System.err.println("Erro ao carregar FXML!");
											e.printStackTrace();
										}

									});
									// Tooltip.install(btn, tooltipChart);
									btn.setStyle(
											"-fx-graphic: url('com/servicos/estatica/resicolor/lab/analythics/style/View.png');");
									btn.setCursor(Cursor.HAND);
									setGraphic(btn);
									setText(null);
								}
							}
						};
						return cell;
					}
				};
		colDetalharRecentes.setCellFactory(cellDetalharFactory);

		colNomeRecentes.setStyle("-fx-alignment: CENTER;");
		colProjetoRecentes.setStyle("-fx-alignment: CENTER;");
		colObjetivoRecentes.setStyle("-fx-alignment: CENTER;");
		colExecutorRecentes.setStyle("-fx-alignment: CENTER;");
		colBalaoRecentes.setStyle("-fx-alignment: CENTER;");
		colNumeroRecentes.setStyle("-fx-alignment: CENTER;");
		colFinalRecentes.setStyle("-fx-alignment: CENTER;");
		colDetalharRecentes.setStyle("-fx-alignment: CENTER;");
		tblRecentes.getColumns().setAll(colNomeRecentes, colProjetoRecentes, colObjetivoRecentes, colExecutorRecentes,
				colBalaoRecentes, colNumeroRecentes, colFinalRecentes, colDetalharRecentes);

	}
}
