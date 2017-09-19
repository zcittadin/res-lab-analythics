package com.servicos.estatica.resicolor.lab.analythics.report;

import java.util.List;

import com.servicos.estatica.resicolor.lab.analythics.model.Amostra;

public class AmostrasReportModel {

	private String prova;
	private List<Amostra> amostras;

	public AmostrasReportModel() {

	}

	public AmostrasReportModel(String prova, List<Amostra> amostras) {
		this.prova = prova;
		this.amostras = amostras;
	}

	public String getProva() {
		return prova;
	}

	public void setProva(String prova) {
		this.prova = prova;
	}

	public List<Amostra> getAmostras() {
		return amostras;
	}

	public void setAmostras(List<Amostra> amostras) {
		this.amostras = amostras;
	}

}
