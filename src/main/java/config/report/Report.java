package config.report;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;

/**
 * 
 * Name: {@link Report}
 * 
 * Propósito: Interface com o objetivo de encapsular as funcionalidades de criação das evidencias
 * 
 * @author Inmetrics
 * 
 * @see
 * 
 * @version 1.0
 *
 */
public interface Report {

	public void setWebDriver(WebDriver webDriver);
	
	/**
	 * Captura a tela atual e empilha a imagem ao Report sem precisar incluir o texto.
	 */
	public void addStep();
	
	/**
	 * Captura a tela atual e empilha a imagem ao Report junto à descrição
	 * recebida por parâmetro.
	 * 
	 * @param description
	 *            Texto que será adicionado ao report junto à imagem
	 * @author Ícaro Silva
	 */
	public void addStep(String description);

	/**
	 * Captura a tela atual e empilha a imagem ao Report junto à descrição
	 * recebida por parâmetro. Permite também desativar temporariamente a utilização do texto padrão.
	 * 
	 * @param description
	 * 			Texto que será adicionado ao report junto à imagem	
	 * @param discartStandardText
	 * 			Se o valor for True, não adiciona o texto padrão que foi incluido no metodo EvidenceManager.addTextReport.
	 */
	public void addStep(String description, boolean discartStandardText);

	/**
	 * Recebe os elementos da tela que devem ser destacados, captura a tela e
	 * empilha a imagem ao Report. Após
	 * a captura da tela os highlights devem ser removidos.
	 * 
	 * @param WebElement[]
	 *            Lista de WebElements que devem ser destacados.
	 */
	public void addStep(WebElement... webElement);
	
	/**
	 * Recebe os elementos da tela que devem ser destacados, captura a tela e
	 * empilha a imagem ao Report junto à descrição recebida por parâmetro. Após
	 * a captura da tela os highlights devem ser removidos.
	 * 
	 * @param description
	 *            Texto que será adicionado ao report junto à imagem
	 * @param WebElement[]
	 *            Lista de WebElements que devem ser destacados.
	 * @author Ícaro Silva
	 */
	public void addStep(String description, WebElement... webElement);

	/**
	 * Recebe os elementos da tela que devem ser destacados, captura a tela e
	 * empilha a imagem ao Report junto à descrição recebida por parâmetro. Após
	 * a captura da tela os highlights devem ser removidos. 
	 * Permite também desativar temporariamente a utilização do texto padrão.
	 * 
	 * @param description
	 * 			Texto que será adicionado ao report junto à imagem
	 * @param discartStandardText
	 * 			Se o valor for True, não adiciona o texto padrão que foi incluido no metodo EvidenceManager.addTextReport.
	 * @param webElement
	 *  		Lista de WebElements que devem ser destacados.
	 */
	public void addStep(String description, boolean discartStandardText, WebElement... webElement);
	
	/**
	 * Recebe os elementos da tela que devem ser destacados, captura a tela e
	 * empilha a imagem ao Report junto à descrição recebida por parâmetro. Após
	 * a captura da tela os highlights devem ser removidos. 
	 * Permite também desativar temporariamente a utilização do texto padrão.
	 * 
	 * @param description
	 * 			Texto que será adicionado ao report junto à imagem
	 * @param discartStandardText
	 * 			Se o valor for True, não adiciona o texto padrão que foi incluido no metodo EvidenceManager.addTextReport.
	 * @param listWebElement
	 *  		Lista de WebElements que devem ser destacados.
	 */
	public void addStep(String description, boolean discartStandardText, List<WebElement> listWebElement);

	/**
	 * Define qual o local o Report deve ser salvo.
	 * 
	 * Caso não seja definido, usará como default o diretório de execução.
	 * 
	 * @param path
	 *            Path indicando qual diretório o Report deve ser salvo.
	 * @author Ícaro Silva
	 */
	public void setPath(String path);

	/**
	 * Define o nome do teste e o objetivo dele na capa do Report. Caso não seja
	 * definido nenhum valor e capa será adicionada em branco.
	 * 
	 * @param testName
	 * @param objective
	 */
	public void setCover(String testName, String objective);

	/**
	 * Salva a evidencia em caminho previamente especificado. Caso não tenha
	 * sido especificado um caminho, então salvará no diretório de execução
	 */
	public void save();

	/**
	 * Salva a evidencia em caminho previamente especificado. Caso não tenha
	 * sido especificado um caminho, então salvará no diretório de execução
	 */
	public void save(String path);

	/**
	 * Adicionar um texto à Pagina
	 * 
	 * @param description
	 *            Texto que será adicionado ao report junto à imagem
	 * @author Elenilva Andrade
	 */
	public void addText(String description);

	public void disableStandardText();
	
	/**
	 * 
	 */
	public void save (boolean isPassed);
	
	/**
	 * Adicionar um texto à mesma página
	 * 
	 * @param description
	 *            Texto que será adicionado ao report junto à imagem
	 * @param fontName
	 *            Define o nome da fonte
	 * @param size
	 *            Define tamanho da fonte
	 * @param style
	 *            Define o estilo da fonte ex:(Italic, bold)
	 * @BaseColor 
	 *            Define a cor da fonte
	 * Exemplo de uso: report.addFreeText("EXAMPLE", FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.GREEN);
	 * 
	 * @author Ícaro Silva
	 */
	public void addFreeText(String description, String fontName, float size, int style, BaseColor color);
	
	/**
	 * Método responsável por anexar as evidencias do REDES
	 * @param params
	 */
	public void addMainframeStep(String friendly) throws MalformedURLException, IOException, DocumentException;

	void takeFullScreenScreenshot(String description);
}
