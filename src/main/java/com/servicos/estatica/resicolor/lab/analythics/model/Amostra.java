package com.servicos.estatica.resicolor.lab.analythics.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "amostras")
public class Amostra implements Serializable {

	private static final long serialVersionUID = 6150418213712955052L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prova")
	private Prova provaAmostras;
	@Column(name = "horario")
	private Date horario;
	@Column(name = "temp")
	private int temp;
	@Column(name = "set_point")
	private int setPoint;
	@Column(name = "ia_sobre_nv")
	private double iaSobreNv;
	@Column(name = "visc_gardner")
	private String viscGardner;
	@Column(name = "cor_gardner")
	private String corGardner;
	@Column(name = "percentual_nv")
	private double percentualNv;
	@Column(name = "gel_time")
	private int gelTime;
	@Column(name = "agua")
	private double agua;
	@Column(name = "amostra")
	private double amostra;
	@Column(name = "ph")
	private double ph;
	@Column(name = "descricao")
	private String descricao;

	public Amostra() {

	}

	public Amostra(Long id, Prova provaAmostras, Date horario, int temp, int setPoint, double iaSobreNv,
			String viscGardner, String corGardner, double percentualNv, int gelTime, double agua, double amostra,
			double ph, String descricao) {
		this.id = id;
		this.provaAmostras = provaAmostras;
		this.horario = horario;
		this.temp = temp;
		this.setPoint = setPoint;
		this.iaSobreNv = iaSobreNv;
		this.viscGardner = viscGardner;
		this.corGardner = corGardner;
		this.percentualNv = percentualNv;
		this.gelTime = gelTime;
		this.agua = agua;
		this.amostra = amostra;
		this.ph = ph;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Prova getProvaAmostras() {
		return provaAmostras;
	}

	public void setProvaAmostras(Prova provaAmostras) {
		this.provaAmostras = provaAmostras;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getSetPoint() {
		return setPoint;
	}

	public void setSetPoint(int setPoint) {
		this.setPoint = setPoint;
	}

	public double getIaSobreNv() {
		return iaSobreNv;
	}

	public void setIaSobreNv(double iaSobreNv) {
		this.iaSobreNv = iaSobreNv;
	}

	public String getViscGardner() {
		return viscGardner;
	}

	public void setViscGardner(String viscGardner) {
		this.viscGardner = viscGardner;
	}

	public String getCorGardner() {
		return corGardner;
	}

	public void setCorGardner(String corGardner) {
		this.corGardner = corGardner;
	}

	public double getPercentualNv() {
		return percentualNv;
	}

	public void setPercentualNv(double percentualNv) {
		this.percentualNv = percentualNv;
	}

	public int getGelTime() {
		return gelTime;
	}

	public void setGelTime(int gelTime) {
		this.gelTime = gelTime;
	}

	public double getAgua() {
		return agua;
	}

	public void setAgua(double agua) {
		this.agua = agua;
	}

	public double getAmostra() {
		return amostra;
	}

	public void setAmostra(double amostra) {
		this.amostra = amostra;
	}

	public double getPh() {
		return ph;
	}

	public void setPh(double ph) {
		this.ph = ph;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Amostra [id=" + id + ", provaAmostras=" + provaAmostras + ", horario=" + horario + ", temp=" + temp
				+ ", setPoint=" + setPoint + ", iaSobreNv=" + iaSobreNv + ", viscGardner=" + viscGardner
				+ ", corGardner=" + corGardner + ", percentualNv=" + percentualNv + ", gelTime=" + gelTime + ", agua="
				+ agua + ", amostra=" + amostra + ", ph=" + ph + ", descricao=" + descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Amostra other = (Amostra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
