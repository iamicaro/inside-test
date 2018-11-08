package gerador.steps;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import gerador.functions.GeradorCPFFunction;

public class GeradorCPFStep {

	private GeradorCPFFunction function = new GeradorCPFFunction();
	
	@Dado("^que esteja na pagina do gerador de cpf$")
	public void navegarGeradorCpf() {
		function.navegarGeradorCpf();
	}
	
	@Quando("^informar o cpf para validacao$")
	public void informarCpf() {
		function.informarCpf();
	}
	
	@Entao("^devera informar que esta correto$")
	public void validarCorretude() {
		function.validarCorretude();
	}
	
}
