package com.servicos.estatica.resicolor.lab.analythics.report;

import java.util.Date;

public class AnaliseComparativaModel {

	private Date intervalo;
	private Date hora1;
	private String prova1;
	private Date hora2;
	private String prova2;
	private double temp1;
	private double temp2;

	public AnaliseComparativaModel() {

	}

	public AnaliseComparativaModel(Date intervalo, Date hora1, String prova1, Date hora2, String prova2, double temp1, double temp2) {
		this.intervalo = intervalo;
		this.hora1 = hora1;
		this.prova1 = prova1;
		this.hora2 = hora2;
		this.prova2 = prova2;
		this.temp1 = temp1;
		this.temp2 = temp2;
	}

	public Date getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Date intervalo) {
		this.intervalo = intervalo;
	}

	public Date getHora1() {
		return hora1;
	}
	
	public void setHora1(Date hora1) {
		this.hora1 = hora1;
	}

	public String getProva1() {
		return prova1;
	}

	public void setProva1(String prova1) {
		this.prova1 = prova1;
	}
	
	public Date getHora2() {
		return hora2;
	}
	
	public void setHora2(Date hora2) {
		this.hora2 = hora2;
	}

	public String getProva2() {
		return prova2;
	}

	public void setProva2(String prova2) {
		this.prova2 = prova2;
	}

	public double getTemp1() {
		return temp1;
	}

	public void setTemp1(double temp1) {
		this.temp1 = temp1;
	}

	public double getTemp2() {
		return temp2;
	}

	public void setTemp2(double temp2) {
		this.temp2 = temp2;
	}

}
