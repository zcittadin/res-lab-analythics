package com.servicos.estatica.resicolor.lab.analythics.report;

import java.util.Date;

public class AnaliseComparativaModel {

	private Date dtProc;
	private double temp1;
	private double temp2;

	public AnaliseComparativaModel() {

	}

	public AnaliseComparativaModel(Date dtProc, double temp1, double temp2) {
		this.dtProc = dtProc;
		this.temp1 = temp1;
		this.temp2 = temp2;
	}

	public Date getDtProc() {
		return dtProc;
	}

	public void setDtProc(Date dtProc) {
		this.dtProc = dtProc;
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
