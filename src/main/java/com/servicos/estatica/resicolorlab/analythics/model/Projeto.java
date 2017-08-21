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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "projetos")
public class Projeto implements Serializable {

	private static final long serialVersionUID = 4986109093612234663L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@OneToMany(mappedBy = "projeto", targetEntity = Prova.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Prova> provas;
	@Column(name = "nome")
	private String nome;
	@Column(name = "dt_criacao")
	private Date dtCriacao;

	public Projeto() {

	}

	public Projeto(Long id, List<Prova> provas, String nome, Date dtCriacao) {
		this.id = id;
		this.provas = provas;
		this.nome = nome;
		this.dtCriacao = dtCriacao;
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

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", provas=" + provas + ", nome=" + nome + ", dtCriacao=" + dtCriacao + "]";
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
