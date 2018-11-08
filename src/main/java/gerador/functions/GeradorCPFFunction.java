package gerador.functions;

import gerador.pageobjects.GeradorCPFPage;

public class GeradorCPFFunction {

	private GeradorCPFPage page = new GeradorCPFPage();
	
	public GeradorCPFFunction navegarGeradorCpf() {
		page.goToGeradorCpf();
		return this;
	}
	
	public GeradorCPFFunction informarCpf() {
		page.setCpf("33460337087");
		page.clickValidar();
		return this;
	}
	
	public GeradorCPFFunction validarCorretude() {
		page.validationIcon();
		return this;
	}
	
}
