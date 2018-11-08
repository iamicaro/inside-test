package config.report;

/**
 * 
 * Name: {@link ReportType}
 * 
 * Propósito: Enum de configuração com os tipos de extensao de reports
 * 
 * @author Inmetrics
 * 
 * @see
 * 
 * @version 1.0
 *
 */
public enum ReportType {
	PDF {
		@Override
		public Report getReport() {
			return new ReportPDF();
		}
	},
	DOC {
		@Override
		public Report getReport() {
			throw new RuntimeException("Ainda não implementado");
		}
	};
	
	/**
	 * Obter report
	 * @param driver
	 * @return
	 */
	public abstract Report getReport ();
}