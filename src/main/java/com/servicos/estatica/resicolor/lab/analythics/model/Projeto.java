package com.servicos.estatica.resicolor.lab.analythics.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "projetos")
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1990930300452155512L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@OneToMany(mappedBy = "projeto", targetEntity = Prova.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Prova> provas;
	@Column(name = "nome")
	private String nome;
	@Column(name = "dt_criacao")
	private Date dtCriacao;
	@Column(name = "dt_final")
	private Date dtFinal;
	@Column(name = "teor_solidos")
	private String teorSolidos;
	@Column(name = "viscosidade")
	private String viscosidade;
	@Column(name = "cor_gardner")
	private String corGardner;
	@Column(name = "indice_acidez")
	private String indiceAcidez;
	@Column(name = "teor_oh")
	private String teorOh;
	@Column(name = "ph")
	private String ph;
	@Column(name = "dados_add")
	private String dadosAdd;

	public Projeto() {

	}

	public Projeto(Long id, List<Prova> provas, String nome, Date dtCriacao, Date dtFinal, String teorSolidos,
			String viscosidade, String corGardner, String indiceAcidez, String teorOh, String ph, String dadosAdd) {
		this.id = id;
		this.provas = provas;
		this.nome = nome;
		this.dtCriacao = dtCriacao;
		this.dtFinal = dtFinal;
		this.teorSolidos = teorSolidos;
		this.viscosidade = viscosidade;
		this.corGardner = corGardner;
		this.indiceAcidez = indiceAcidez;
		this.teorOh = teorOh;
		this.ph = ph;
		this.dadosAdd = dadosAdd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Prova> getProvas() {
		return provas;
	}

	public void setProvas(List<Prova> provas) {
		this.provas = provas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtFinal() {
		return dtFinal;
	}

	public void setDtFinal(Date dtFinal) {
		this.dtFinal = dtFinal;
	}

	public String getTeorSolidos() {
		return teorSolidos;
	}

	public void setTeorSolidos(String teorSolidos) {
		this.teorSolidos = teorSolidos;
	}

	public String getViscosidade() {
		return viscosidade;
	}

	public void setViscosidade(String viscosidade) {
		this.viscosidade = viscosidade;
	}

	public String getCorGardner() {
		return corGardner;
	}

	public void setCorGardner(String corGardner) {
		this.corGardner = corGardner;
	}

	public String getIndiceAcidez() {
		return indiceAcidez;
	}

	public void setIndiceAcidez(String indiceAcidez) {
		this.indiceAcidez = indiceAcidez;
	}

	public String getTeorOh() {
		return teorOh;
	}

	public void setTeorOh(String teorOh) {
		this.teorOh = teorOh;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getDadosAdd() {
		return dadosAdd;
	}

	public void setDadosAdd(String dadosAdd) {
		this.dadosAdd = dadosAdd;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", provas=" + provas + ", nome=" + nome + ", dtCriacao=" + dtCriacao + ", dtFinal="
				+ dtFinal + ", teorSolidos=" + teorSolidos + ", viscosidade=" + viscosidade + ", corGardner="
				+ corGardner + ", indiceAcidez=" + indiceAcidez + ", teorOh=" + teorOh + ", ph=" + ph + ", dadosAdd="
				+ dadosAdd + "]";
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
		Projeto other = (Projeto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
