package config.commons.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;



public class ImageUtils {

	final static int WIDTH = 200;
	
	final static int HEIGHT = 100;
	
	/**
	 * Converte Txt para Imagem
	 * @param nomeTela
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] convertTxtToImage(String nomeTela, String path) throws IOException {
		
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = new Font("Courier new", Font.PLAIN, 20);
		
		g2d.setFont(font);
		g2d.dispose();
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		File file = new File(path);

		BufferedReader br = null;
		int nextLinePosition = 10;
		int fontSize = 20;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				g2d.drawString(line.trim(), 0, nextLinePosition);
				nextLinePosition = nextLinePosition + fontSize;
			}
			br.close();
		} catch (FileNotFoundException ex) {
			throw new IllegalArgumentException("Erro ao encontrar arquivo");
		} catch (IOException ex) {
			throw new IllegalArgumentException("Erro ao processar arquivo");
		}

		g2d.dispose();
		File f = new File("./temp/images/" + nomeTela + ".png");
		try {
			if (!f.exists())
				f.mkdirs();
			
			ImageIO.write(img, "png", f);
		} catch (IOException ex) {
			throw new IllegalArgumentException("Erro ao processar imagem");
		}
		return convertImageToBytes(f);
	}
	
	/**
	 * Converte Image para uma array de Bytes
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] convertImageToBytes(File file) throws IOException {
		BufferedImage img = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", baos);
		byte[] imageInByte = baos.toByteArray();
		baos.flush();
		baos.close();
		return imageInByte;
	}
	
}
