package com.servicos.estatica.resicolor.lab.analythics.report.xls;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.servicos.estatica.resicolor.lab.analythics.model.Amostra;
import com.servicos.estatica.resicolor.lab.analythics.model.Prova;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class XlsGenerator {

	private static SimpleDateFormat dataHoraSdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

	public Task<Void> generate(File file, Prova prova1, Prova prova2, ObservableList<Amostra> amostras1,
			ObservableList<Amostra> amostras2) {
		Task<Void> xlsTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				int maximum = 20;
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet firstSheet = workbook.createSheet("Aba1");
				FileOutputStream fos = null;
				HSSFRow rowIndexer = firstSheet.createRow(0);
				try {
					fos = new FileOutputStream(file);
					int line = 0;
					if (prova1 != null) {
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Projeto " + prova1.getProjeto().getNome());
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Prova " + prova1.getNomeProva());
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Dados de entrada");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Teor de sólidos");
						rowIndexer.createCell(1).setCellValue("Índice de acidez");
						rowIndexer.createCell(2).setCellValue("Viscosidade");
						rowIndexer.createCell(4).setCellValue("Cor Gardner");
						rowIndexer.createCell(3).setCellValue("Teor OH");
						rowIndexer.createCell(5).setCellValue("PH");
						rowIndexer.createCell(6).setCellValue("Dados adicionais");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue(prova1.getProjeto().getTeorSolidos());
						rowIndexer.createCell(1).setCellValue(prova1.getProjeto().getIndiceAcidez());
						rowIndexer.createCell(2).setCellValue(prova1.getProjeto().getViscosidade());
						rowIndexer.createCell(4).setCellValue(prova1.getProjeto().getCorGardner());
						rowIndexer.createCell(3).setCellValue(prova1.getProjeto().getTeorOh());
						rowIndexer.createCell(5).setCellValue(prova1.getProjeto().getPh());
						rowIndexer.createCell(6).setCellValue(prova1.getProjeto().getDadosAdd());
						rowIndexer.createCell(0).setCellValue("Prova " + prova1.getNomeProva());
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Resultados");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Horário");
						rowIndexer.createCell(1).setCellValue("Temperatura");
						rowIndexer.createCell(2).setCellValue("Set-point");
						rowIndexer.createCell(3).setCellValue("Índice de acidez");
						rowIndexer.createCell(4).setCellValue("Viscosidade");
						rowIndexer.createCell(5).setCellValue("Cor Gardner");
						rowIndexer.createCell(6).setCellValue("Percentual NV");
						rowIndexer.createCell(7).setCellValue("Gel time(seg)");
						rowIndexer.createCell(8).setCellValue("Água(ml)");
						rowIndexer.createCell(9).setCellValue("Amostra(g)");
						rowIndexer.createCell(10).setCellValue("PH");
						rowIndexer.createCell(11).setCellValue("Descrição do processo");
						line++;
						for (Amostra amostra : amostras1) {
							rowIndexer = firstSheet.createRow(line);
							rowIndexer.createCell(0).setCellValue(dataHoraSdf.format(amostra.getHorario()));
							rowIndexer.createCell(1).setCellValue(amostra.getTemp());
							rowIndexer.createCell(2).setCellValue(amostra.getSetPoint());
							rowIndexer.createCell(3).setCellValue(amostra.getIaSobreNv());
							rowIndexer.createCell(4).setCellValue(amostra.getViscGardner());
							rowIndexer.createCell(5).setCellValue(amostra.getCorGardner());
							rowIndexer.createCell(6).setCellValue(amostra.getPercentualNv());
							rowIndexer.createCell(7).setCellValue(amostra.getGelTime());
							rowIndexer.createCell(8).setCellValue(amostra.getAgua());
							rowIndexer.createCell(9).setCellValue(amostra.getAmostra());
							rowIndexer.createCell(10).setCellValue(amostra.getPh());
							rowIndexer.createCell(11).setCellValue(amostra.getDescricao());
							line++;
						}
						line++;
					}
					if (prova2 != null) {
						rowIndexer = firstSheet.createRow(line);
						CellStyle cellStyle = workbook.createCellStyle();
						cellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
						cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
						rowIndexer.setRowStyle(cellStyle);
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Projeto " + prova2.getProjeto().getNome());
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Prova " + prova2.getNomeProva());
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Dados de entrada");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Teor de sólidos");
						rowIndexer.createCell(1).setCellValue("Índice de acidez");
						rowIndexer.createCell(2).setCellValue("Viscosidade");
						rowIndexer.createCell(4).setCellValue("Cor Gardner");
						rowIndexer.createCell(3).setCellValue("Teor OH");
						rowIndexer.createCell(5).setCellValue("PH");
						rowIndexer.createCell(6).setCellValue("Dados adicionais");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue(prova2.getProjeto().getTeorSolidos());
						rowIndexer.createCell(1).setCellValue(prova2.getProjeto().getIndiceAcidez());
						rowIndexer.createCell(2).setCellValue(prova2.getProjeto().getViscosidade());
						rowIndexer.createCell(4).setCellValue(prova2.getProjeto().getCorGardner());
						rowIndexer.createCell(3).setCellValue(prova2.getProjeto().getTeorOh());
						rowIndexer.createCell(5).setCellValue(prova2.getProjeto().getPh());
						rowIndexer.createCell(6).setCellValue(prova2.getProjeto().getDadosAdd());
						rowIndexer.createCell(0).setCellValue("Prova " + prova2.getNomeProva());
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Resultados");
						line++;
						rowIndexer = firstSheet.createRow(line);
						rowIndexer.createCell(0).setCellValue("Horário");
						rowIndexer.createCell(1).setCellValue("Temperatura");
						rowIndexer.createCell(2).setCellValue("Set-point");
						rowIndexer.createCell(3).setCellValue("Índice de acidez");
						rowIndexer.createCell(4).setCellValue("Viscosidade");
						rowIndexer.createCell(5).setCellValue("Cor Gardner");
						rowIndexer.createCell(6).setCellValue("Percentual NV");
						rowIndexer.createCell(7).setCellValue("Gel time(seg)");
						rowIndexer.createCell(8).setCellValue("Água(ml)");
						rowIndexer.createCell(9).setCellValue("Amostra(g)");
						rowIndexer.createCell(10).setCellValue("PH");
						rowIndexer.createCell(11).setCellValue("Descrição do processo");
						line++;
						for (Amostra amostra : amostras2) {
							rowIndexer = firstSheet.createRow(line);
							rowIndexer.createCell(0).setCellValue(dataHoraSdf.format(amostra.getHorario()));
							rowIndexer.createCell(1).setCellValue(amostra.getTemp());
							rowIndexer.createCell(2).setCellValue(amostra.getSetPoint());
							rowIndexer.createCell(3).setCellValue(amostra.getIaSobreNv());
							rowIndexer.createCell(4).setCellValue(amostra.getViscGardner());
							rowIndexer.createCell(5).setCellValue(amostra.getCorGardner());
							rowIndexer.createCell(6).setCellValue(amostra.getPercentualNv());
							rowIndexer.createCell(7).setCellValue(amostra.getGelTime());
							rowIndexer.createCell(8).setCellValue(amostra.getAgua());
							rowIndexer.createCell(9).setCellValue(amostra.getAmostra());
							rowIndexer.createCell(10).setCellValue(amostra.getPh());
							rowIndexer.createCell(11).setCellValue(amostra.getDescricao());
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