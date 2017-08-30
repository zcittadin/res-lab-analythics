package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.resicolor.lab.analythics.app.ControlledScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@SuppressWarnings("rawtypes")
public class AnalisesController implements Initializable, ControlledScreen {

	@FXML
	private TableView tblProvas;
	@FXML
	private TableColumn colNome;
	@FXML
	private TableColumn colProjeto;
	@FXML
	private TableColumn colObjetivo;
	@FXML
	private TableColumn colExecutor;
	@FXML
	private TableColumn colBalao;
	@FXML
	private TableColumn colNumero;
	@FXML
	private TableColumn colFinalizado;
	@FXML
	private RadioButton rdProjeto;
	@FXML
	private RadioButton rdPeriodo;
	@FXML
	private TextField txtProjeto;
	@FXML
	private DatePicker dtpInicio;
	@FXML
	private DatePicker dtpFim;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
