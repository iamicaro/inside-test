package config.commons.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public class FileUtils {

static String pathFile;
	
	static Logger logger = Logger.getLogger(FileUtils.class);
	
	/**
	 * : método responsável por retornar o path de um arquivo que contenha parte
	 * do nome informado na pasta passada por parametro
	 * 
	 * @param parteNome
	 * @param extensaoArquivo
	 * @param path
	 *            - caminho do arquivo após o nome de usuario. Ex: '\documents'
	 *            ou '\imagens\pastaX'
	 * @return
	 * @throws IOException
	 */
	public static String getArchivePath(String parteNome, String extensaoArquivo, String caminhoArquivo) throws IOException {
		String rootPath = System.getProperty("user.home") + caminhoArquivo;
		Path fromPath = Paths.get(rootPath);
		String path = "";

		DirectoryStream<Path> stream = Files.newDirectoryStream(fromPath, "*" + parteNome + "*." + extensaoArquivo);
		Iterator<Path> it = stream.iterator();
		if (it.hasNext()) {
			Path arquive = it.next();
			path = arquive.toAbsolutePath().toString();
		} else {
			String message = "thereIsNoFileInDirectory " + rootPath;
			throw new IOException(message);
		}
		if (it.hasNext()) {
			String message = "thereIsMoreThanOneFile " + rootPath;
			throw new IOException(message);
		}
		
		return path;
	}
	
	/**
	 * Abrir arquivo
	 * 
	 * @param baseArquivo
	 * @param nomeArquivo
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter abrirArquivo(String baseArquivo, String nomeArquivo) throws IOException {
		File f = new File(baseArquivo);
		if (!f.exists()) {
			f.mkdirs();
		}
		String path = baseArquivo.concat("\\").concat(nomeArquivo);
		return new BufferedWriter(new FileWriter(path, true));
	}

	/**
	 * Escrever no arquivo
	 * 
	 * @param linha
	 * @param bw
	 */
	public static void writeArquivo(String linha, BufferedWriter bw) {
		try {
			bw.write(linha);
			bw.newLine();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Não foi possível criar o arquivo de saída", e);
		} catch (IOException e) {
			throw new IllegalArgumentException("Erro ao processar arquivo", e);
		}
	}

	/**
	 * Close arquivo
	 * 
	 * @param bw
	 * @throws IOException
	 */
	public static void fecharArquivo(BufferedWriter bw) throws IOException {
		bw.close();
	}
	
	/**
	 * Busca por arquivo .zip em fromPath e devolve o primeiro encontrado.</br>
	 * SerÃ¡ lanÃ§ada uma exceÃ§Ã£o caso nÃ£o haja nenhum arquivo .zip ou haja
	 * mais que um.
	 * 
	 * @param fromPath
	 *            DiretÃ³rio onde serÃ¡ procurado o arquivo .zip.
	 * @return {@link Path} do arquivo .csv.
	 * @throws NotDirectoryException
	 * @throws AccessDeniedException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static Path lookupZippedFile(Path fromPath, String parteNomeArquivo)
			throws NotDirectoryException, AccessDeniedException, IOException {
		Objects.requireNonNull(fromPath);
		Path zipFile = null;

		try {
			File arq = new File(fromPath.toString());
			int count = 0;
			while (!arq.exists()) {
				Thread.sleep(1000);
				arq = new File(fromPath.toString());
				if (count > 3) {
					break;
				}
				count++;
			}
			DirectoryStream<Path> stream = Files.newDirectoryStream(fromPath, "*" + parteNomeArquivo + "*.zip");
			Iterator<Path> it = stream.iterator();
			if (it.hasNext()) {
				zipFile = it.next();
				pathFile = zipFile.toAbsolutePath().toString();
			} else {
				String message = "thereIsNoZipFileInDirectory" + fromPath;
				throw new IOException(message);
			}
			if (it.hasNext()) {
				String message = "thereIsMoreThanOneZipFile" + fromPath;
				throw new IOException(message);
			}
		} catch (NotDirectoryException e) {
			String message = "notADirectory" + e.getLocalizedMessage();
			throw new NotDirectoryException(message);
		} catch (AccessDeniedException e) {
			String message = "noReadablePermission" + e.getLocalizedMessage();
			throw new AccessDeniedException(message);
		} catch (IOException e) {
			String message = "couldNotLoad" + e.getLocalizedMessage();
			throw new IOException(message, e);
		} catch (InterruptedException e) {
			String message = "couldNotLoad" + e.getLocalizedMessage();
			throw new IOException(message, e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zipFile;
	}
	
	/**
	 * Sobrecarga Metodo
	 * 
	 * @param caminho
	 * @throws NotDirectoryException
	 * @throws AccessDeniedException
	 * @throws IOException
	 */
	public static void uncompress(String caminho, String parteNomeArquivo) throws NotDirectoryException, AccessDeniedException, IOException {
		String StringFromPath = caminho;
		Path fromPath = Paths.get(StringFromPath);

		Objects.requireNonNull(fromPath);
		Path zippedFile = lookupZippedFile(fromPath, parteNomeArquivo);
		byte[] data = new byte[2048];

		try (InputStream input = new BufferedInputStream(Files.newInputStream(zippedFile));
				ZipInputStream zipInput = new ZipInputStream(input);) {

			ZipEntry entry = zipInput.getNextEntry();

			if (entry == null) {
				String message = "zipFileIsEmpty";
				logger.info(message);
				throw new ZipException(message);
			}

			String storeFileName = "";
			if (parteNomeArquivo == ""){
				storeFileName = caminho + "\\" + entry.toString().substring(0, entry.toString().length() - 4)
					.replaceAll("[0-9]", "").replaceAll("__", " ").replaceAll(" ", "_") + "Export.csv";
			}
			else{
				storeFileName = caminho + "\\" + entry.toString().substring(0, entry.toString().length() - 4) + "Export.csv";
			}

			middlewareUnZip(data, zipInput, storeFileName);

			deleteZippedFile(zippedFile);
			logger.info("Unzip File");

		} catch (FileAlreadyExistsException e) {
			String message = "O arquivo já existe no diretório de download" + e.getLocalizedMessage();
			logger.info(message);
		} catch (NotDirectoryException e) {
			String message = "O diretório não existe" + e.getLocalizedMessage();
			logger.info(message);
		} catch (AccessDeniedException e) {
			String message = "Sem permissão de acesso ao diretório" + e.getLocalizedMessage();
			logger.info(message);
		} catch (IOException e) {
			String message = "Erro ao deletar dowload .zip" + e.getLocalizedMessage();
			logger.info(message);
		}
	}

	/**
	 * getArchivePath : método responsável por retornar o path de um arquivo que
	 * contenha parte do nome informado na pasta DOWNLOADS por parametro
	 * 
	 * @param parteNome
	 *            {@link String}
	 * @param extensaoArquivo
	 *            {@link String}
	 * @return {@link String}
	 */
	public static String getArchivePath(String parteNome, String extensaoArquivo) {
		try {
			String rootPath = System.getProperty("user.home") + "\\Downloads";
			String path = "";
			Path fromPath = Paths.get(rootPath);

			DirectoryStream<Path> stream = Files.newDirectoryStream(fromPath, "*" + parteNome + "*." + extensaoArquivo);
			Iterator<Path> it = stream.iterator();
			if (it.hasNext()) {
				Path arquive = it.next();
				path = arquive.toAbsolutePath().toString();
			} else {
				String message = "thereIsNoFileInDirectory " + rootPath;
				throw new IOException(message);
			}
			if (it.hasNext()) {
				String message = "thereIsMoreThanOneFile " + rootPath;
				throw new IOException(message);
			}
			
			stream.close();
			
			return path;
		} catch (Exception e) {
			throw new RuntimeException("Não foi possivel encontrar a pasta.", e);
		}
	}

	/**
	 * 
	 * @param data
	 * @param zipInput
	 * @param storeFileName
	 * @throws IOException
	 */
	public static void middlewareUnZip(byte[] data, ZipInputStream zipInput, String storeFileName) throws IOException {
		Path outputFile = Paths.get(storeFileName);
		Path outputDirs = outputFile.resolve(Paths.get("..")).normalize();
		Files.createDirectories(outputDirs);

		if (Files.exists(outputFile)) {
			String fileName = outputFile.toString();

			if (fileName.contains(".")) {
				String extension = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
				fileName = fileName.substring(0, fileName.lastIndexOf('.'));

				int i = 1;
				for (; Files.exists(Paths.get(fileName + "_" + i + extension)); i++)
					;

				outputFile = Paths.get(fileName + i + extension);
			} else {
				int i = 1;
				for (; Files.exists(Paths.get(fileName + i)); i++)
					;
				outputFile = Paths.get(fileName + "_" + i);
			}
		}

		try (OutputStream output = new BufferedOutputStream(Files.newOutputStream(outputFile))) {
			int count = -1;
			while ((count = zipInput.read(data)) != -1)
				output.write(data, 0, count);
			logger.info("Unzip File");
		}
	}
	
	/**
	 * Delete files
	 * @param file
	 */
	public static void deleteFiles(String... file) {
		for (String f : file) {
			File fileStar = new File(f);
			File[] listFiles = fileStar.listFiles();
			if (listFiles != null) {
				for (File fl : listFiles) {
					fl.delete();
				}
			}
		}
	}
	
	/**
	 * Define qual o arquivo deve ser utilizado para carregar os dados.
	 * @author Jhonny Santos
	 * @param Path
	 * @throws Exception
	 */
 	public static void setFile(String path) throws Exception{
 		pathFile = path;
		logger.debug("setFile : Path informado = " + path);
	}
 	
 	/**
 	 * Obter path do arquivo
 	 * @return
 	 * @throws Exception
 	 */
 	public String getPath() throws Exception{
		if(StringUtils.isNotNull(pathFile)) 
			return pathFile;
		throw new Exception("Caminho para a planilha de 'Data' não localizado, verifique se o caminho foi informado.");
	}
 	
 	/**
	 * Create temp file
	 * @param clazz
	 * @param value
	 * @throws Exception
	 */
	public static void createFileTemp(String value) throws Exception {
		OutputStream out = new FileOutputStream("temp.txt");
		Writer writer = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.write(value);
		bufferedWriter.close();
	}
	
	/**
	 * Deleta arquivo .zip
	 * @param zippedFile
	 */
	public static void deleteZippedFile(Path zippedFile) {
		try {
			Files.delete(zippedFile);
		} catch (IOException e) {
			String message = ("Erro ao deletar o arquivo") + e.getLocalizedMessage();
			logger.error(message);
			throw new RuntimeException(message, e);
		}
	}
	
	/**
	 * Método para renomear um arquivo
	 * 
	 * @param oldPath
	 * @param newPath
	 * @throws Exception
	 */
	public static void renameFile(String oldPath, String newPath) throws IOException {
		File fileOld = new File(oldPath);
		File fileNew = new File(newPath);
		if (fileOld.exists())
			fileOld.renameTo(fileNew);
	}
	
	/**
	 * Obter valor
	 * @return
	 * @throws Exception
	 */
	public static String getValue() throws Exception {
		InputStream in = new FileInputStream("temp.txt");
		Reader r = new InputStreamReader(in);
		BufferedReader b = new BufferedReader(r);
		String result = b.readLine();
		b.close();
		return result;
	}
	
	/**
	 * Deleta um arquivo em um determinado diretório
	 * @param fileName - Nome do arquivo a ser deletado.
	 * @param path - Caminho do arquivo a ser deletado
	 */
	public static void deleteAllExportFiles(String... path) {
		for (String p : path) {
			File pathStar = new File(p);
			File[] listFiles = pathStar.listFiles();
			if (listFiles != null) {
				for (File fl : listFiles) {
					if (fl.getName().contains("Export")) {
						fl.delete();
					}
				}
			}
		}
	}

	/**
	 * Deleta um arquivo em um determinado diretório que contenha um texto em
	 * seu nome
	 * 
	 * @param value - texto que o nome do arquivo a deletar deverá conter.
	 * @param path - Caminho do arquivo a ser deletado
	 */
	public static void deleteFilesHasText(String value, String... path) {
		for (String caminho : path) {
			File pathStar = new File(caminho);
			File[] files = pathStar.listFiles();
			for (File file : files) {
				if (file.getName().contains(value)) {
					file.delete();
				}
			}
		}
	}
	
	/**
	 * Deleta arquivo
	 * @param diretorio
	 */
	public static void deleteFile(String diretorio) {
		try {
			Path path = Paths.get(diretorio);
			Files.deleteIfExists(path);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao remover o arquivo [" + diretorio + "].", e);
		}
	}

	/**
	 * Escrever no arquivo
	 * @param diretorio
	 * @param str
	 */
	public static void writerFile(String diretorio, String str) {
		try {
			Path path = Paths.get(diretorio);
			deleteFile(diretorio);
			Files.createFile(path);

			BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.ISO_8859_1);
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar o arquivo [" + diretorio + "].", e);
		}
	}
	
	/**
	 * Get linhas
	 * @param scn
	 * @return
	 */
	public static List<String> readArchiveLines(InputStream inputStream) {
		List<String> linhas = new ArrayList<String>();
		Scanner sc = null;
		try {
		    sc = new Scanner(inputStream);
		    while (sc.hasNextLine()) {
		    	String line = sc.nextLine();
		    	linhas.add(line);
		    }
		    
		    if (StringUtils.isNotNull(sc.ioException()))
		        throw new RuntimeException("Erro, ao processar os arquivos.");
		    
		} finally {
		    if (StringUtils.isNotNull(inputStream)) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					throw new RuntimeException("Não foi possível fechar, o arquivo");
				}
		    }
		    
		    if (StringUtils.isNotNull(sc))
		        sc.close();
		}
		return linhas;
	}
	
	public static String getPathFile() {
		return pathFile;
	}
	
}
