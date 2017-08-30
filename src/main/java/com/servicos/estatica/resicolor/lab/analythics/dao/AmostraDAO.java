package com.servicos.estatica.resicolor.lab.analythics.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.resicolor.lab.analythics.model.Amostra;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;
import com.servicos.estatica.resicolor.lab.analythics.util.HibernateUtil;

public class AmostraDAO {

	@SuppressWarnings("unchecked")
	public List<Amostra> findAmostraByProva(Prova prova) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT a FROM Amostra a WHERE provaAmostras = :idProva");
		query.setParameter("idProva", prova);
		List<Amostra> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}

}
