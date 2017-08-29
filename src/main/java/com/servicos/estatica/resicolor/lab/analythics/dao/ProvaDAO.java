package com.servicos.estatica.resicolor.lab.analythics.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

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
}
