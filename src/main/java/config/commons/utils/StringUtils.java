package config.commons.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtils {

	/**
	 * Construtor privado
	 */
	private StringUtils() {
	}
	
	
	static Logger logger = Logger.getLogger(StringUtils.class);
	
	public static boolean isNotNull(String string) {
		return string != null ? false : true;
	}

	/**
	 * Verifica se o campo é null ou vazio
	 * @param param String String que deseja verificar se é vazia
	 * @return se a String for nula ou vazia retorna true, caso contrario false.
	 * 
	 */
	public static boolean isEmptyOrNull(String param) {
		boolean ret = param != null && !param.isEmpty();
		return !ret;
	}
		
	/**
	 * Trata campos com caracteres especiais
	 * 
	 * @param
	 * @return String com o campo sem formatação
	 * @author Jhonny Santos
	 * 
	 */
	public static String formatValueToString(String value) {
		String result = "";
		if (!"".equals(value)) {
			result = value.replaceAll("\\s*R\\$\\s*", "").replaceAll("\\s*U\\S\\$\\s*", "").replaceAll("\\.", "").replaceAll("%", "").replaceAll(",","\\.");
		} else {
			result = "";
		}
		return result;
	}
	
	/**
	 * Recupera arquivo TXT de arquivo
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 * @author Inmetrics - Filipe Santos
	 */
	public static String retriveQuery(String file) {

		logger.info("Recuperando o texto ('" + file + "')");
		String strTxt = null;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				strTxt = line + "\n";
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Erro, arquivo não encontrado");
		} catch (IOException e) {
			throw new IllegalArgumentException("Erro, arquivo não processado");
		}
		return strTxt;
	}
	
	/**
	 * Remove os números da palavra via Regex
	 * 
	 * @param p
	 * @return
	 */
	public static String removeNumeros(String p) {
		return p.replace("-", "").replaceAll("\\d", "");
	}

	/**
	 * Converter string array para string
	 * @param strArr
	 * @return
	 */
	public static String convertStringArrayToString(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        for(String str : strArr) 
        	sb.append(str);
        return sb.toString();
    }
	
	/**
	 * Remove todos os caracteres especiais de uma String recebida por parâmetro
	 * @param s
	 * @return
	 */
	public static String removeAll(String s) {
		return removeEspecialChars(s).toLowerCase().trim();
	}

	/**
	 * Remove caracteres especiais
	 * @param s
	 * @return
	 */
	public static String removeEspecialChars(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * Metodo retorna o numero da posição de um valor em um array
	 * 
	 * @param String
	 * @throws Exception
	 */
	public static int getPositionNumberOfAColumn(String[] array, String value) {
		int cont = 0;
		for (String values : array) {
			if (values.equals(value)) {
				return cont;
			}
			cont++;
		}
		return cont;
	}
	
	/**
	 * Retorna o valor encontrado via regex
	 * @param texto
	 * @param regex
	 * @return
	 */
	public static String getTextRegex(String texto, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(texto);
		StringBuilder ret = new StringBuilder();
		while(m.find()){
			if (!isEmptyOrNull(ret.toString()))
				ret.append("\n");
			
			ret.append(m.group());
		}
		return ret.toString();
	}
	
	/**
	 * Compara o conteúdo das strings
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean compara(String s1, String s2) {
		boolean result = false;
		if (s1.length() == s2.length()) {
			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) == s2.charAt(i)) {
					result = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Adiciona valor '0' à esquerda
	 * @param param
	 * @param size
	 * @return
	 */
	public static String lpad(String param, int size) {
		String msg = "'%0' + %d + d";
		return String.format(msg, Integer.parseInt(param), size);
	}
	
	/**
	 * Remover mascara monetaria
	 * @param value
	 * @return
	 */
	public static String formatMoneyValueFromPage(String value) {
		String result = value.replace("R$", "").replace(" , ", "").trim();
		if (result.length() < 15) {
			String aux = "000000000000000";
			int posicaoFinal = aux.length() - result.length();
			result = aux.substring(0, posicaoFinal).concat(result);
		}
		return result;
	}
	
	/**
	 * Tratar zeros no inicio da string
	 * @param value
	 * @return
	 */
	public static String tratarZerosNoInicioDaString(String value) {
		
		String valor = value;
		
		if (valor.matches("^000.*")) 
			valor = value.replace("000", "");
		boolean isDoiszero = valor.matches("^00.*");
		if (isDoiszero) 
			valor = valor.replace("00", "");
		boolean isUmzero = valor.matches("^0.*");
		if (isUmzero) 
			valor = valor.replaceFirst("0", "");
		
		return valor;
	}
	
	/**
	 * Formata valor, removendo a mascara monetaria
	 * @param value
	 * @return
	 */
	public static double formatValue(String value) {
		String result = null;
		String valor = value;
		if (!"".equals(valor)) {
			result = valor.replaceAll("\\s*R\\$\\s*", "").replaceAll("\\.", "").replaceAll("%", "").replaceAll(",","\\.");
		} else {
			result = "0";
		}
		return (Double.valueOf(result));
	}
	
	/**
	 * Converte string para camelCase
	 * @param init
	 * @return
	 */
	public static String toCamelCase(final String init) {
		
	    if (init==null)
	        return null;

	    final StringBuilder ret = new StringBuilder(init.length());

	    for (final String word : init.split(" ")) {
	        if (!word.isEmpty()) {
	            ret.append(word.substring(0, 1).toUpperCase());
	            ret.append(word.substring(1).toLowerCase());
	        }
	        if (ret.length() != init.length())
	            ret.append(" ");
	    }
	    return ret.toString();
	}
	
	/**
	 * Obter linha do arquivo
	 * @param palavra
	 * @return
	 */
	public static List<String> getLine(String palavra) {
		List<String> palavras = new ArrayList<>();
		String[] matchLineDividida = palavra.split(";");
		for (int i = 0; i < matchLineDividida.length; i++) {
			String registro = matchLineDividida[i];
			if (isNotEmptyOrNull(registro)) palavras.add(registro);
		}
		return palavras;
	}
	
	public static boolean isNotEmptyOrNull(String arg) {
		if(isNotNull(arg) && arg != "") {
			return true;
		}
		return false;
	}
	
	/**
	 * Valida se o Nome do Scenario possui mais de 255 caracteres, 
	 * 	se for maior ele limita ao maximo de 255 caracteres descartando o final do nome
	 * 
	 * @param scenarioName : Nome do cenario que está sendo utilizado
	 * @return <I> String </I>: nome do cenario limitado a 255 caracteres
	 * 
	 * @author Aliomar Junior.
	 * 
	 */
	public static String validateName(String scenarioName) {
		if (scenarioName.length() < 255) {
			return scenarioName;
		}else{
			return scenarioName.substring(0, 254);
		}
	}	

	/**
	 * Inmetrics<BR>
	 * 
	 * Tratar caracteres especiais no nome do cenário, pois automação estoura
	 * exception ao tentar criar nome do arquivo .pdf <BR>
	 * 
	 * @since 5 de jun de 2017 16:38:19
	 * @author Renato Vieira<BR>
	 */
	public static String normalizaCaracteresEspeciaisAlfa(String text) {
		String texto = text;
		texto = removeEspecialChars(texto);
		texto = texto.replaceAll("[^a-zA-Z0-9 ]", "");
		return texto;
	}
	
	/**
	 * Tratar string de validacao
	 * @param str
	 * @return
	 */
	public static String tratarStringValidacao(String str){
		String palavra = str;
		if (isEmptyOrNull(palavra) || isBlank(palavra)) 
			palavra = "[EM BRANCO]";
		palavra = palavra.trim();
		return palavra;
	}
	
	/**
	 * Verifica se string é vazia
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value.length() == 0;
	}
	
	/**
	 * Verifica se é null
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object value) {
		return value == null;
	}
	
	/**
	 * Verifica se não é vazio
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
	/**
	 * Valida se a object nao é null
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(Object value) {
		return !isNull(value);
	}
	
	/**
	 * Remove espaços em branco na string
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(CharSequence cs) {
		
        int strLen;
        boolean retorno = true;
        if (cs == null || (strLen = cs.length()) == 0)
            return retorno;
        
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(cs.charAt(i)))) {
            	retorno = true;
            } else {
            	retorno = false;
            }
        }
        return retorno;
    }
	
	/**
	 * Retorna os valores de uma coluna especifica
	 * @param posicao
	 * @param linhasFiltradas
	 * @return 
	 */
	public static List<String> retornarValoresPorColuna(int posicao, List<String> linhasFiltradas){
		List<String> valores = new ArrayList<>();
		for (String linha : linhasFiltradas) {	
			String[] matchLineDividida = linha.split(";");
			if(matchLineDividida.length >= posicao){
				valores.add(matchLineDividida[posicao-1]);
			}
		}
		return valores;
	}
	
	/**
	 * Formata celulas vindas do excel
	 * @param celula
	 * @return
	 */
	public static String formatarCelulaOriundaDoExcel(String celula) {
		return celula.replaceAll("=", "").replaceAll("\"", "");
	}
	
	/**
	 * Somar os valores monetarios
	 * @param valores
	 * @return
	 */
	public static BigDecimal somaValoresPorColuna(List<String> valores) {
		BigDecimal somaValores = new BigDecimal("0.00");
		for (String valor : valores) {
			BigDecimal valorBigDecimal = new BigDecimal(valor.replace(",", "."));
			somaValores = somaValores.add(valorBigDecimal);
		}
		return somaValores;
	}
	
	/**
	 * somar a quantidade
	 * @param listaDeValores
	 * @return
	 */
	public static int somaQuantidadePorColuna(List<String> listaDeValores) {
		int somaQuantidade = 0;
		for(String linha : listaDeValores){
			int valores = Integer.parseInt(StringUtils.formatValueToString(linha));
			somaQuantidade += valores;
		}
		return somaQuantidade;
	}
	/**
	 * Remove de uma string que contenha um valor, todos os caracteres diferentes de 0 a 9, e substitui a virgula por ponto, permitindo o uso de conversão
	 * de string para BigDecimal{@link BigDecimalUtils}
	 * @param value
	 * @return String
	 */
	public static String normalizarValorMonetario(String value){
		return value.replaceAll("[^0-9\\,]", "").replace(",", ".");
	}
	
}
