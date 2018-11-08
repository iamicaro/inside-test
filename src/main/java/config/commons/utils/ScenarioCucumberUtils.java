package config.commons.utils;

import java.nio.file.Paths;
import java.util.Collection;

import cucumber.api.Scenario;
import cucumber.runtime.CucumberException;

public class ScenarioCucumberUtils {
	
	/**
	 * Verifica o tipo de cenário
	 * 
	 * @param scenario
	 *            Scenario em execução
	 * @return retorna true caso seja Esquema de Cenário, caso contrário retorna
	 *         false
	 */
	private static boolean isScenarioOutline(Scenario scenario) {
		if (scenario.getId().split("\\;").length > 2) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo para montar o nome do report em PDF
	 * 
	 * @author Ricardo Yamada
	 * @param scenario
	 * @return nome do report em PDF
	 * @throws Exception
	 */
	public static String getReportName(Scenario scenario) throws Exception {
		String diretorio = Paths.get("").toAbsolutePath().toString() + "\\Evidences\\";
		String name = StringUtils.toCamelCase(scenario.getName().trim());
		String numberExecution = null;

		if (isScenarioOutline(scenario)) {
			numberExecution = String.valueOf(
					Integer.parseInt(scenario.getId().split(";")[scenario.getId().split(";").length - 1].trim()) - 1);
		} else {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.toLowerCase().contains("XXXXXXXXX")) {
					name = tag.trim().replace("@", "");
					break;
				}
			}
		}

		if (numberExecution != null) {
			name = name.length() > ((160 - diretorio.length()) / 2)
					? name.substring(0, ((158 - (numberExecution.length()) * 2) - diretorio.length()) / 2) : name;
			name += "_" + numberExecution;
		} else {
			name = name.length() > ((160 - diretorio.length()) / 2) ? name.substring(0, (160 - diretorio.length()) / 2)
					: name;
		}

		return name;
	}
	
	public static boolean isWeb(Scenario scenario) {
		boolean name = false;
		
		for (String tag : scenario.getSourceTagNames()) {
			if (tag.toLowerCase().contains("web")) {
				name = true;
				break;
			}
		}
		
		return name;
		
	}

	/**
	 * Metodo para retornar a descrição da feature
	 * 
	 * @author Ricardo Yamada
	 * @param scenario
	 * @return nome da feature
	 */
	public static String getDescriptionFeature(Scenario scenario) {
		String feature = scenario.getId().split("\\;")[0];
		feature = feature.replaceAll("\\-", " ");
		String firstLetter = feature.substring(0, 1).toUpperCase();
		String rest = feature.substring(1, feature.length());
		return firstLetter.concat(rest);
	}

	public static int getID(Scenario scenario) {
		Collection<String> tags = scenario.getSourceTagNames();
		for (String tag : tags) {
			if (tag.matches("^@T\\d{6}$"))
				return Integer.parseInt(tag.replace("@T", ""));
		}
		throw new CucumberException(
				String.format("O cenário %s não possui um ID para fazer relação com TestRail.", scenario.getName()));
	}
	
}
