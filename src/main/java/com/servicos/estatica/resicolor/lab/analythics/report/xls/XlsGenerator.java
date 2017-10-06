package com.servicos.estatica.resicolor.lab.analythics.report.xls;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.servicos.estatica.resicolor.lab.analythics.model.Amostra;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class XlsGenerator {

	private static SimpleDateFormat dataHoraSdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

	public Task<Void> generate(File file, Prova prova1, Prova prova2, ObservableList<Amostra> amostras1,
			ObservableList<Amostra> amostras2) {
		System.out.println("Generate...");
		Task<Void> xlsTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				int maximum = 20;
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet firstSheet = workbook.createSheet("Aba1");
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file);
					int line = 0;
					if (prova1 != null) {
						HSSFRow headerRowProjetoA = firstSheet.createRow(line);
						headerRowProjetoA.createCell(0).setCellValue("Projeto " + prova1.getProjeto().getNome());
						line++;
						HSSFRow headerRowProvaA = firstSheet.createRow(line);
						headerRowProvaA.createCell(0).setCellValue("Prova " + prova1.getNomeProva());
						line++;
						HSSFRow blankRow = firstSheet.createRow(line);
						blankRow.createCell(0).setCellValue("");
						line++;
						HSSFRow dadosLabel = firstSheet.createRow(line);
						dadosLabel.createCell(0).setCellValue("Dados de entrada");
						line++;
						HSSFRow dadosHeader = firstSheet.createRow(line);
						dadosHeader.createCell(0).setCellValue("Teor de sólidos");
						dadosHeader.createCell(1).setCellValue("Índice de acidez");
						dadosHeader.createCell(2).setCellValue("Viscosidade");
						dadosHeader.createCell(4).setCellValue("Cor Gardner");
						dadosHeader.createCell(3).setCellValue("Teor OH");
						dadosHeader.createCell(5).setCellValue("PH");
						dadosHeader.createCell(6).setCellValue("Dados adicionais");
						line++;
						HSSFRow dadosContent = firstSheet.createRow(line);
						dadosContent.createCell(0).setCellValue(prova1.getProjeto().getTeorSolidos());
						dadosContent.createCell(1).setCellValue(prova1.getProjeto().getIndiceAcidez());
						dadosContent.createCell(2).setCellValue(prova1.getProjeto().getViscosidade());
						dadosContent.createCell(4).setCellValue(prova1.getProjeto().getCorGardner());
						dadosContent.createCell(3).setCellValue(prova1.getProjeto().getTeorOh());
						dadosContent.createCell(5).setCellValue(prova1.getProjeto().getPh());
						dadosContent.createCell(6).setCellValue(prova1.getProjeto().getDadosAdd());
						headerRowProvaA.createCell(0).setCellValue("Prova " + prova1.getNomeProva());
						line++;
						dadosLabel = firstSheet.createRow(line);
						dadosLabel.createCell(0).setCellValue("Resultados");
						line++;
						HSSFRow titleRowA = firstSheet.createRow(line);
						titleRowA.createCell(0).setCellValue("Horário");
						titleRowA.createCell(1).setCellValue("Temperatura");
						titleRowA.createCell(2).setCellValue("Set-point");
						titleRowA.createCell(3).setCellValue("Índice de acidez");
						titleRowA.createCell(4).setCellValue("Viscosidade");
						titleRowA.createCell(5).setCellValue("Cor Gardner");
						titleRowA.createCell(6).setCellValue("Percentual NV");
						titleRowA.createCell(7).setCellValue("Gel time(seg)");
						titleRowA.createCell(8).setCellValue("Água(ml)");
						titleRowA.createCell(9).setCellValue("Amostra(g)");
						titleRowA.createCell(10).setCellValue("PH");
						titleRowA.createCell(11).setCellValue("Descrição do processo");
						line++;
						for (Amostra amostra : amostras1) {
							HSSFRow rowA = firstSheet.createRow(line);
							rowA.createCell(0).setCellValue(dataHoraSdf.format(amostra.getHorario()));
							rowA.createCell(1).setCellValue(amostra.getTemp());
							rowA.createCell(2).setCellValue(amostra.getSetPoint());
							rowA.createCell(3).setCellValue(amostra.getIaSobreNv());
							rowA.createCell(4).setCellValue(amostra.getViscGardner());
							rowA.createCell(5).setCellValue(amostra.getCorGardner());
							rowA.createCell(6).setCellValue(amostra.getPercentualNv());
							rowA.createCell(7).setCellValue(amostra.getGelTime());
							rowA.createCell(8).setCellValue(amostra.getAgua());
							rowA.createCell(9).setCellValue(amostra.getAmostra());
							rowA.createCell(10).setCellValue(amostra.getPh());
							rowA.createCell(11).setCellValue(amostra.getDescricao());
							line++;
						}
					}
					line++;
					if (prova2 != null) {
						HSSFRow blankRow = firstSheet.createRow(line);
						blankRow.createCell(0).setCellValue("");
						line++;
						blankRow = firstSheet.createRow(line);
						blankRow.createCell(0).setCellValue("");
						line++;
						HSSFRow headerRowProjetoB = firstSheet.createRow(line);
						headerRowProjetoB.createCell(0).setCellValue("Projeto " + prova2.getProjeto().getNome());
						line++;
						HSSFRow headerRowProvaB = firstSheet.createRow(line);
						headerRowProvaB.createCell(0).setCellValue("Prova " + prova2.getNomeProva());
						line++;
						blankRow = firstSheet.createRow(line);
						blankRow.createCell(0).setCellValue("");
						line++;
						HSSFRow dadosLabel = firstSheet.createRow(line);
						dadosLabel.createCell(0).setCellValue("Dados de entrada");
						line++;
						HSSFRow dadosHeader = firstSheet.createRow(line);
						dadosHeader.createCell(0).setCellValue("Teor de sólidos");
						dadosHeader.createCell(1).setCellValue("Índice de acidez");
						dadosHeader.createCell(2).setCellValue("Viscosidade");
						dadosHeader.createCell(4).setCellValue("Cor Gardner");
						dadosHeader.createCell(3).setCellValue("Teor OH");
						dadosHeader.createCell(5).setCellValue("PH");
						dadosHeader.createCell(6).setCellValue("Dados adicionais");
						line++;
						HSSFRow dadosContent = firstSheet.createRow(line);
						dadosContent.createCell(0).setCellValue(prova2.getProjeto().getTeorSolidos());
						dadosContent.createCell(1).setCellValue(prova2.getProjeto().getIndiceAcidez());
						dadosContent.createCell(2).setCellValue(prova2.getProjeto().getViscosidade());
						dadosContent.createCell(4).setCellValue(prova2.getProjeto().getCorGardner());
						dadosContent.createCell(3).setCellValue(prova2.getProjeto().getTeorOh());
						dadosContent.createCell(5).setCellValue(prova2.getProjeto().getPh());
						dadosContent.createCell(6).setCellValue(prova2.getProjeto().getDadosAdd());
						headerRowProvaB.createCell(0).setCellValue("Prova " + prova2.getNomeProva());
						line++;
						dadosLabel = firstSheet.createRow(line);
						dadosLabel.createCell(0).setCellValue("Resultados");
						line++;
						HSSFRow titleRowB = firstSheet.createRow(line);
						line++;
						titleRowB.createCell(0).setCellValue("Horário");
						titleRowB.createCell(1).setCellValue("Temperatura");
						titleRowB.createCell(2).setCellValue("Set-point");
						titleRowB.createCell(3).setCellValue("Índice de acidez");
						titleRowB.createCell(4).setCellValue("Viscosidade");
						titleRowB.createCell(5).setCellValue("Cor Gardner");
						titleRowB.createCell(6).setCellValue("Percentual NV");
						titleRowB.createCell(7).setCellValue("Gel time(seg)");
						titleRowB.createCell(8).setCellValue("Água(ml)");
						titleRowB.createCell(9).setCellValue("Amostra(g)");
						titleRowB.createCell(10).setCellValue("PH");
						titleRowB.createCell(11).setCellValue("Descrição do processo");
						for (Amostra amostra : amostras2) {
							HSSFRow rowB = firstSheet.createRow(line);
							rowB.createCell(0).setCellValue(dataHoraSdf.format(amostra.getHorario()));
							rowB.createCell(1).setCellValue(amostra.getTemp());
							rowB.createCell(2).setCellValue(amostra.getSetPoint());
							rowB.createCell(3).setCellValue(amostra.getIaSobreNv());
							rowB.createCell(4).setCellValue(amostra.getViscGardner());
							rowB.createCell(5).setCellValue(amostra.getCorGardner());
							rowB.createCell(6).setCellValue(amostra.getPercentualNv());
							rowB.createCell(7).setCellValue(amostra.getGelTime());
							rowB.createCell(8).setCellValue(amostra.getAgua());
							rowB.createCell(9).setCellValue(amostra.getAmostra());
							rowB.createCell(10).setCellValue(amostra.getPh());
							rowB.createCell(11).setCellValue(amostra.getDescricao());
							line++;
						}
					}
					workbook.write(fos);
					for (int i = 0; i < maximum; i++) {
						updateProgress(i, maximum);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Erro ao exportar arquivo");
				} finally {
					try {
						fos.flush();
						fos.close();
						workbook.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		};
		return xlsTask;
	}
}