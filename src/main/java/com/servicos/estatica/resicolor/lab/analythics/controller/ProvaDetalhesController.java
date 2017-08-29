package com.servicos.estatica.resicolor.lab.analythics.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import javafx.fxml.Initializable;

public class ProvaDetalhesController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	public void setContext(Prova prova) {
		System.out.println(prova.getExecutor());
		System.out.println(prova.getObjetivo());
//		leituras = LeituraDAO.findLeiturasByProcesso(processo);
//		if (!leituras.isEmpty()) {
//			for (Leitura leitura : leituras) {
//				plotTemp(leitura.getTemp(), leitura.getDtProc());
//			}
//		}
	}

}
