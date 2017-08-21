package com.servicos.estatica.resicolorlab.analythics.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "provas")
public class Prova implements Serializable {

	private static final long serialVersionUID = 2171320528758867638L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_projeto")
	private Projeto projeto;
	@OneToMany(mappedBy = "provaLeituras", targetEntity = Leitura.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Leitura> leituras;
	@OneToMany(mappedBy = "provaAmostras", targetEntity = Amostra.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Amostra> amostras;
	@Column(name = "produto")
	private String produto;
	@Column(name = "objetivo")
	private String objetivo;
	@Column(name = "executor")
	private String executor;
	@Column(name = "nome_prova")
	private String nomeProva;
	@Column(name = "balao")
	private String balao;
	@Column(name = "t_max")
	private double tempMax;
	@Column(name = "t_min")
	private double tempMin;
	@Column(name = "dh_inicial")
	private Date dhInicial;
	@Column(name = "dh_final")
	private Date dhFinal;

	public Prova() {

	}

	public Prova(Long id, Projeto projeto, List<Leitura> leituras, List<Amostra> amostras, String produto,
			String objetivo, String executor, String nomeProva, String balao, double tempMax, double tempMin,
			Date dhInicial, Date dhFinal) {
		this.id = id;
		this.projeto = projeto;
		this.leituras = leituras;
		this.amostras = amostras;
		this.produto = produto;
		this.objetivo = objetivo;
		this.executor = executor;
		this.nomeProva = nomeProva;
		this.balao = balao;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.dhInicial = dhInicial;
		this.dhFinal = dhFinal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Leitura> getLeituras() {
		return leituras;
	}

	public void setLeituras(List<Leitura> leituras) {
		this.leituras = leituras;
	}

	public List<Amostra> getAmostras() {
		return amostras;
	}

	public void setAmostras(List<Amostra> amostras) {
		this.amostras = amostras;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getNomeProva() {
		return nomeProva;
	}

	public void setNomeProva(String nomeProva) {
		this.nomeProva = nomeProva;
	}

	public String getBalao() {
		return balao;
	}

	public void setBalao(String balao) {
		this.balao = balao;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public Date getDhInicial() {
		return dhInicial;
	}

	public void setDhInicial(Date dhInicial) {
		this.dhInicial = dhInicial;
	}

	public Date getDhFinal() {
		return dhFinal;
	}

	public void setDhFinal(Date dhFinal) {
		this.dhFinal = dhFinal;
	}

	@Override
	public String toString() {
		return "Prova [id=" + id + ", projeto=" + projeto + ", leituras=" + leituras + ", amostras=" + amostras
				+ ", produto=" + produto + ", objetivo=" + objetivo + ", executor=" + executor + ", nomeProva="
				+ nomeProva + ", balao=" + balao + ", tempMax=" + tempMax + ", tempMin=" + tempMin + ", dhInicial="
				+ dhInicial + ", dhFinal=" + dhFinal + "]";
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
		Prova other = (Prova) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
