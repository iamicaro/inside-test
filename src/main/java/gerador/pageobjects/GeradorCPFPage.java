package gerador.pageobjects;

import config.Config;

public class GeradorCPFPage {

	Config config = new Config();
	
	private static final String URL_GERADOR_CPF_ORG = "https://www.geradordecpf.org/";
	private static final String XPATH_NUMERO_CPF = "//input[@id='numero']";
	private static final String XPATH_BUTTON_VALIDAR = "//input[contains(@value,'Validar CPF')]";
	private static final String XPATH_ICON_VALIDATION = "//span[contains(@class, 'glyphicon-ok')][contains(@style,'display: block')]";
	
	private String cpf = "";
	
	public void goToGeradorCpf() {
		config.getExtendedWebDriver().goTo(URL_GERADOR_CPF_ORG);
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
		config.getExtendedWebDriver().xpath(XPATH_NUMERO_CPF, cpf);
	}

	public void clickValidar() {
		config.getExtendedWebDriver().click(XPATH_BUTTON_VALIDAR);
	}
	
	public void validationIcon() {
		if(!config.getExtendedWebDriver().exists(XPATH_ICON_VALIDATION)) {
			throw new RuntimeException("Não foi possível validar o CPF: " + cpf);
		}
	}
	
}
