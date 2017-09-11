package com.servicos.estatica.resicolor.lab.analythics.util;

import java.time.Duration;
import java.util.Date;

import com.servicos.estatica.resicolor.lab.analythics.model.Leitura;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

public class EnsaioUtil {

	public static double getTempMin(Prova prova) {
		double min = 1000;
		for (Leitura leitura : prova.getLeituras()) {
			if (leitura.getTemp() < min) {
				min = leitura.getTemp();
			}
		}
		return min;
	}

	public static double getTempMax(Prova prova) {
		double max = 0;
		for (Leitura leitura : prova.getLeituras()) {
			if (leitura.getTemp() >= max) {
				max = leitura.getTemp();
			}
		}
		return max;
	}

	public static String formatPeriod(Date ini, Date fim) {
		long periodoMillis = fim.getTime() - ini.getTime();
		Duration duration = Duration.ofMillis(periodoMillis);
		long hours = duration.toHours();
		int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
		int seconds = (int) (duration.getSeconds() % 60);
		return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds);
	}
}
