package com.servicos.estatica.resicolor.lab.analythics.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.resicolor.lab.analythics.model.Leitura;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;
import com.servicos.estatica.resicolor.lab.analythics.util.HibernateUtil;

public class LeituraDAO {

	public List<Leitura> findLeiturasByProva(Prova prova) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query queryProva = session.createQuery("SELECT p FROM Prova p WHERE id = :idProva");
		queryProva.setParameter("idProva", prova.getId());
		session.close();
		return prova.getLeituras();
	}
}
