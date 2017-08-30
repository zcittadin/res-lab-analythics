package com.servicos.estatica.resicolor.lab.analythics.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.resicolor.lab.analythics.model.Projeto;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;
import com.servicos.estatica.resicolor.lab.analythics.util.HibernateUtil;

public class ProvaDAO {

	@SuppressWarnings("unchecked")
	public List<Prova> findEmAndamento() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		String hql = "SELECT p FROM Prova p WHERE p.dhFinal IS NULL AND p.dhInicial IS NOT NULL";
		Query query = session.createQuery(hql);
		List<Prova> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Prova> findRecentes() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		String hql = "SELECT p FROM Prova p WHERE p.dhFinal IS NOT NULL ORDER BY p.id DESC";
		Query query = session.createQuery(hql);
		query.setMaxResults(8);
		List<Prova> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}

	@SuppressWarnings({ "unchecked" })
	public List<Prova> findByNome(String provaName) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		String hql = "SELECT p FROM Prova p WHERE p.nomeProva LIKE '%" + provaName + "%' ORDER BY p.id DESC";
		Query query = session.createQuery(hql);
		List<Prova> provas = query.getResultList();
		session.close();
		if (provas != null && !provas.isEmpty()) {
			return provas;
		}
		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings({ "unchecked" })
	public List<Prova> findByProjeto(String projetoName) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		String hqlProjeto = "SELECT p FROM Projeto p WHERE p.nome LIKE '%" + projetoName + "%' ORDER BY p.id DESC";
		Query queryProjeto = session.createQuery(hqlProjeto);
		List<Projeto> projetos = queryProjeto.getResultList();
		session.close();
		if (projetos != null && !projetos.isEmpty()) {
			return groupProvas(projetos);
		}
		return Collections.EMPTY_LIST;
	}

	private List<Prova> groupProvas(List<Projeto> projetos) {
		List<Prova> provas = new ArrayList<>();
		projetos.forEach(projeto -> {
			List<Prova> provasParciais = projeto.getProvas();
			provasParciais.forEach(provaParcial -> {
				provas.add(provaParcial);
			});
		});
		return provas;

	}

	@SuppressWarnings("unchecked")
	public List<Prova> findByPeriodo(String inicio, String fim) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT p FROM Prova p WHERE DATE(dhInicial) BETWEEN '" + inicio + "' AND '"
				+ fim + "' ORDER BY id DESC");
		List<Prova> list = query.getResultList();
		session.close();
		return list;
	}
}
