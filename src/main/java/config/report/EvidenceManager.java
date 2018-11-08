package config.report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import config.report.exception.ReportException;

public class EvidenceManager {

	private static HashMap <String, List<Path>> pathMap;
	private static HashMap <String, List<String>> reportMap;
	private static Path pathToSave;
		
	static {
		pathMap = new HashMap <String, List<Path>>();
		reportMap = new HashMap <String, List<String>>(); 
		pathToSave = Paths.get("./Evidences/Execucao_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + "/");
		try {
			Files.createDirectories(pathToSave);
		} catch (IOException e) {
			throw new ReportException(e);
		}
	}
	
	/**
	 * Construtor privado. Não é necessário iniciar a classe para utiliza-la
	 */
	private EvidenceManager () {
	}
	
	/**
	 * Recebe um objeto Path referente ao arquivo, 
	 * move ele para o diretório do teste e mantêm o novo Path relaciado ao teste executado.
	 * Caso já exista um arquivo com o mesmo nome no diretório de destino, então ele será sbrescrito.
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void addEvidence (Path path) throws IOException {
		addEvidence(path, false);
	}
	
	/**
	 * Recebe um objeto Path referente ao arquivo,
	 * copy / move ele para o diretório do teste e mantêm o novo Path relaciado ao teste executado.
	 * Caso já exista um arquivo com o mesmo nome no diretório de destino, então ele será sbrescrito.
	 * @param path - caminho do arquivo para adicionar a pasta de evidencia.
	 * @param copyFile <b>True</b> : copia o arquivo para a pasta de evidencia.
	 * <b>False</b> :  move o arquivo para a pasta de evidencia. 
	 * @throws IOException
	 */
	public static void addEvidence (Path path, boolean copyFile) throws IOException {
		String testName = getTestName ();
		Path fullPath = Paths.get(pathToSave + "/" + testName + "/" + path.getFileName() );
		
		if (!pathMap.containsKey(testName)) {
			pathMap.put(testName, new ArrayList <Path> ());
		}
		try {	
			if (!Files.exists(fullPath)) {
				Files.createDirectories(fullPath.getParent());
			}
			if(copyFile){
				Files.copy(path,fullPath, StandardCopyOption.REPLACE_EXISTING);
			}
			else{
				Files.move(path,fullPath, StandardCopyOption.REPLACE_EXISTING);
			}
			pathMap.get(testName).add(fullPath);
		}
		catch (IOException e ) {
			throw new ReportException("Falha ao mover o arquivo de " + path.toString() + " para " + fullPath + " -----\n" + e.getMessage());
		}		
	}
		
	/**
	 * Retorna uma lista de Path que contêm os endereços 
	 * para as evidências relacionadas ao caso de teste que está sendo executado.
	 * 
	 * @return List<Path>  Lista de Path contendo endereço das evidências
	 */
	public static List <Path> getEvidences () {
		String testName = getTestName();
		return pathMap.get(testName);
	}
		
	/**
	 * Retorna uma lista de Path que contêm os endereços 
	 * para as evidências relacionadas ao caso de teste recebido por parâmetro
	 * 
	 * @return List<Path>  Lista de Path contendo endereço das evidências
	 */
	public static List <Path> getEvidences (String testName) {
		return pathMap.get(testName);
	}
			
	/**
	 * Obtem o nome teste que está sendo executado
	 * 
	 * @return Retorno o nome do metodo de teste que está sendo executado.
	 */
	private static String getTestName () {
		return Thread.currentThread().getName();
	}
	
	/**
	 * Adiciona a lista de texto padrão do Report para ser utilizado no metodo getTextReport.
	 * @param text
	 */
	public static void addTextReport(String text){
		String testName = getTestName ();	
		if (!reportMap.containsKey(testName)) {
			reportMap.put(testName, new ArrayList <String> ());
		}
		reportMap.get(testName).add(text);
	}
	
	/**
	 * Retorna a lista de textos padrões adicionando pelo metodo addTextReport e limpa a lista referente ao testName Informado. 
	 * @param testName
	 * @return
	 */
	public static List<String> getTextReport(String testName){
		List<String> listTextReport = new ArrayList <String> ();
		try{
			if (reportMap.containsKey(testName)) {
				int i = 0;
				for(String text : reportMap.get(testName)){
					listTextReport.add((i==0)?text:"\n"+text);
					i++;
				}
			}
			reportMap.get(testName).clear();
			return listTextReport;
		}
		catch(Exception e){
			return listTextReport;
		}
	}

	/**
	 * Metodo para limpar a Lista que contem os textos padrões adicionados pelo addTextReport.
	 * @param testName
	 */
	public static void clearTextReport(String testName){
		if(reportMap.containsKey(testName)){
			reportMap.get(testName).clear();
		}
	}
	
}
