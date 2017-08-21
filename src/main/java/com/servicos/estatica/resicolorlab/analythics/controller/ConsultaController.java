package com.servicos.estatica.resicolorlab.analythics.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.resicolorlab.analythics.app.ControlledScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConsultaController implements Initializable, ControlledScreen {

	@FXML
	private Rectangle rectForm;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rectForm.setFill(Color.TRANSPARENT);
	}

}
