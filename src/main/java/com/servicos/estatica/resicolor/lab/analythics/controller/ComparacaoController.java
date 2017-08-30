package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ComparacaoController implements Initializable {

	private Prova prova1;
	private Prova prova2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setContext(Prova prova1, Prova prova2) {
		this.prova1 = prova1;
		this.prova2 = prova2;
	}

}
