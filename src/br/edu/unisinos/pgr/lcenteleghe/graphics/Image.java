package br.edu.unisinos.pgr.lcenteleghe.graphics;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.AffineTransformOp;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.exception.IllegalFileFormatException;

/**
 * @author Luis Gustavo This class represents an simple image.
 */
public class Image {
	private int pixels[];
	private int width, height;

	public Image(int w, int h) {
		width = w;
		height = h;
		pixels = new int[w * h];
	}
	
	public Image(int w, int h, int[] pixels) {
		width = w;
		height = h;
		this.pixels = pixels;
	}

	public Image(String PTMFilePath) {
		load(PTMFilePath);
	}

	public Image(File PTMFile) {
		load(PTMFile);
	}

	public void setPixel(int rgb, int x, int y) {
		pixels[x + y * width] = rgb;
	}

	public void setPixel(RGB rgb, int x, int y) {
		pixels[x + y * width] = rgb.wrapInInt();
	}

	/**
	 * Retorna qual o pixel que está em uma posição x,y da imagem
	 * @param x
	 * @param y
	 * @return
	 */
	public int getPixel(int x, int y) {
		return pixels[x + y * width];
	}

	public RGB getPixelRGB(int x, int y) {
		return RGB.getRGBFromWrappedInt(pixels[x + y * width]);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public IntBuffer getByteBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(pixels.length * 4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		IntBuffer ib = bb.asIntBuffer();
		ib.put(pixels);
		ib.flip();
		return ib;
	}

	public static void randomlyPopulate(Image image) {
		int size = image.getHeight() * image.getWidth();
		for (int i = 0; i < size; i++) {
			image.setPixel(RGB.getRandomRGB(), i % image.getWidth(),
					i / image.getWidth());
		}
	}

	public static void fullPopulate(Image image, RGB rgb) {
		int size = image.getHeight() * image.getWidth();
		for (int i = 0; i < size; i++) {
			image.setPixel(rgb, i % image.getWidth(), i / image.getWidth());
		}
	}

	public int[] getPixels() {
		return pixels;
	}

	private void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public void plot(Image image, float plot_x, float plot_y) {
		int adjusted_x = (int) plot_x;
		int adjusted_y = (int) plot_y;
		int adjusted_width = (int) image.getWidth();
		int adjusted_height = (int) image.getHeight();
		int sub_imageX = 0;
		int sub_imageY = 0;
		
		
		//Caso esteja fora dos ranges nem plota
		if( 
		  plot_x < image.getWidth()*-1  ||
		  plot_x > this.getWidth() ||
		  plot_y < image.getHeight()*-1 ||
		  plot_y > this.getHeight()
		) return;

		boolean flag = false;//Flag que indica se é exigido algum tratamento especial antes de plotar
		if (plot_x < 0) {
			adjusted_x = 0;
			sub_imageX = (int)plot_x*-1;
			adjusted_width = (int) (image.width - sub_imageX);
			flag = true;
		}

		if (plot_y < 0) {
			adjusted_y = 0;
			sub_imageY = (int)plot_y*-1;
			adjusted_height = (int) (image.height - sub_imageY);
			flag = true;
		}
		
		if ((plot_x + image.getWidth()) > this.width) {
			adjusted_width = (int) (this.width - adjusted_x);
			if (adjusted_width < 0) {
				adjusted_width = 0;
			}
			flag = true;
		}

		if ((plot_y + image.getHeight()) > this.height) {
			adjusted_height = (int) (this.height - adjusted_y);
			if (adjusted_height < 0) {
				adjusted_height = 0;
			}
			flag = true;
		}

		int[] p_pixels;

		if (flag) {
			p_pixels = new int[adjusted_width * adjusted_height];
			image.subImage(sub_imageX, sub_imageY, adjusted_width, adjusted_height, p_pixels);
		} else {
			p_pixels = image.getPixels();
		}

		for (int i = 0; i < p_pixels.length; i++) {
			int localA = i / adjusted_width;
			int localB = i % adjusted_width;
			int plot_x_localB = (int) adjusted_x + localB;
			int plot_y_localA = (int) adjusted_y + localA;
			int plot_pixel = p_pixels[i];
			int plot_a = (plot_pixel >> 24) & 0xff;
			if (plot_a == 0) {
				continue;
			}

			int plot_r = (plot_pixel >> 16) & 0xff;
			int plot_g = (plot_pixel >> 8) & 0xff;
			int plot_b = plot_pixel & 0xff;

			if (plot_a == 255) {
				pixels[plot_x_localB + plot_y_localA * width] = plot_pixel;
				continue;
			}

			int this_pixel = this.getPixel(plot_x_localB, plot_y_localA);
			int this_a = (this_pixel >> 24) & 0xff;
			int this_r = (this_pixel >> 16) & 0xff;
			int this_g = (this_pixel >> 8)  & 0xff;
			int this_b = this_pixel & 0xff;

			float normalizedAlpha = plot_a / 255f;
			int mixed_pixel = RGB
					.wrapInInt(
							(int) ((plot_a * normalizedAlpha) + (this_a * (1 - normalizedAlpha))),
							(int) ((plot_r * normalizedAlpha) + (this_r * (1 - normalizedAlpha))),
							(int) ((plot_g * normalizedAlpha) + (this_g * (1 - normalizedAlpha))),
							(int) ((plot_b * normalizedAlpha) + (this_b * (1 - normalizedAlpha))));

			pixels[plot_x_localB + plot_y_localA * width] = mixed_pixel;
		}
	}

	public void plot(Image image) {
		int adjusted_x = 0;
		int adjusted_y = 0;
		int adjusted_width = (int) image.getWidth();
		int adjusted_height = (int) image.getHeight();

		boolean flag = false;
		if ((image.getWidth()) > this.width) {
			adjusted_width = (int) (this.width - adjusted_x);
			flag = true;
		}

		if ((image.getHeight()) > this.height) {
			adjusted_height = (int) (this.height - adjusted_y);
			flag = true;
		}

		int[] p_pixels;

		if (flag) {
			p_pixels = new int[adjusted_width * adjusted_height];
			image.subImage(0, 0, adjusted_width, adjusted_height, p_pixels);
		} else {
			p_pixels = image.getPixels();
		}

		for (int i = 0; i < p_pixels.length; i++) {
			int localA = i / adjusted_width;
			int localB = i % adjusted_width;
			int plot_x_localB = (int) adjusted_x + localB;
			int plot_y_localA = (int) adjusted_y + localA;
			int plot_pixel = p_pixels[i];
			int plot_a = (plot_pixel >> 24) & 0xff;
			if (plot_a == 0) {
				continue;
			}

			int plot_r = (plot_pixel >> 16) & 0xff;
			int plot_g = (plot_pixel >> 8) & 0xff;
			int plot_b = plot_pixel & 0xff;

			if (plot_a == 255) {
				pixels[plot_x_localB + plot_y_localA * width] = plot_pixel;
				continue;
			}

			int this_pixel = this.getPixel(plot_x_localB, plot_y_localA);
			int this_a = (this_pixel >> 24) & 0xff;
			int this_r = (this_pixel >> 16) & 0xff;
			int this_g = (this_pixel >> 8) & 0xff;
			int this_b = this_pixel & 0xff;

			float normalizedAlpha = plot_a / 255f;
			int mixed_pixel = RGB
					.wrapInInt(
							(int) ((plot_a * normalizedAlpha) + (this_a * (1 - normalizedAlpha))),
							(int) ((plot_r * normalizedAlpha) + (this_r * (1 - normalizedAlpha))),
							(int) ((plot_g * normalizedAlpha) + (this_g * (1 - normalizedAlpha))),
							(int) ((plot_b * normalizedAlpha) + (this_b * (1 - normalizedAlpha))));

			pixels[plot_x_localB + plot_y_localA * width] = mixed_pixel;
		}
	}

	public void erase(Image image, float plot_x, float plot_y) {
		int adjusted_x = (int) plot_x;
		int adjusted_y = (int) plot_y;
		int adjusted_width = (int) image.getWidth();
		int adjusted_height = (int) image.getHeight();
		int adjusted_subImage_x = 0;
		int adjusted_subImage_y = 0;

		if (plot_x < (image.width * -1) || plot_y < (image.height * -1)) {
			return;
		}

		boolean flag = false;
		if (plot_x < 0) {
			adjusted_x = 0;
			adjusted_subImage_x = (int) plot_x * -1;
			flag = true;
		}

		if (plot_y < 0) {
			adjusted_y = 0;
			adjusted_subImage_y = (int) plot_y * -1;
			flag = true;
		}

		if ((plot_x + image.getWidth()) > this.width) {
			adjusted_width = (int) (this.width - adjusted_x);
			if (adjusted_width < 0) {
				adjusted_width = 0;
			}
			flag = true;
		}

		if ((plot_y + image.getHeight()) > this.height) {
			adjusted_height = (int) (this.height - adjusted_y);
			if (adjusted_height < 0) {
				adjusted_height = 0;
			}
			flag = true;
		}

		adjusted_width -= adjusted_subImage_x;
		adjusted_height -= adjusted_subImage_y;

		int[] p_pixels;

		if (flag) {
			p_pixels = new int[adjusted_width * adjusted_height];
			image.subImage(adjusted_subImage_x, adjusted_subImage_y,
					adjusted_width, adjusted_height, p_pixels);
		} else {
			p_pixels = image.getPixels();
		}

		for (int i = 0; i < p_pixels.length; i++) {
			int localA = i / adjusted_width;
			int localB = i % adjusted_width;
			int plot_x_localB = (int) adjusted_x + localB;
			int plot_y_localA = (int) adjusted_y + localA;
			int plot_a = (p_pixels[i] >> 24) & 0xff;

			int this_pixel = this.getPixel(plot_x_localB, plot_y_localA);
			int this_a = (this_pixel >> 24) & 0xff;
			int this_r = (this_pixel >> 16) & 0xff;
			int this_g = (this_pixel >> 8) & 0xff;
			int this_b = this_pixel & 0xff;

			int newA = this_a - plot_a;
			if (newA < 0) {
				newA = 0;
			}

			int mixed_pixel = RGB.wrapInInt(newA, this_r, this_g, this_b);

			pixels[plot_x_localB + plot_y_localA * width] = mixed_pixel;
		}
	}
	
	public boolean hasColorAt(float x, float y){
		if(x < 0) return false;
		if(x >= this.width) return false;
		if(y < 0) return false;
		if(y >= this.height) return false;

		int this_pixel = getPixel((int)x, (int)y);
		int this_a = (this_pixel >> 24) & 0xff;

		if(this_a > 0){
			return true;
		}
		
		return false;
	}

	public boolean intersects(Image image, float x, float y) {
		int adjusted_x = (int) x;
		int adjusted_y = (int) y;
		int adjusted_width = (int) image.getWidth();
		int adjusted_height = (int) image.getHeight();
		int adjusted_subImage_x = 0;
		int adjusted_subImage_y = 0;

		if (x < (image.width * -1) || y < (image.height * -1)) {
			return false;
		}

		boolean flag = false;
		if (x < 0) {
			adjusted_x = 0;
			adjusted_subImage_x = (int) x * -1;
			flag = true;
		}

		if (y < 0) {
			adjusted_y = 0;
			adjusted_subImage_y = (int) y * -1;
			flag = true;
		}

		if ((x + image.getWidth()) > this.width) {
			adjusted_width = (int) (this.width - adjusted_x);
			if (adjusted_width < 0) {
				adjusted_width = 0;
			}
			flag = true;
		}

		if ((y + image.getHeight()) > this.height) {
			adjusted_height = (int) (this.height - adjusted_y);
			if (adjusted_height < 0) {
				adjusted_height = 0;
			}
			flag = true;
		}

		adjusted_width -= adjusted_subImage_x;
		adjusted_height -= adjusted_subImage_y;

		int[] p_pixels;

		if (flag) {
			p_pixels = new int[adjusted_width * adjusted_height];
			image.subImage(adjusted_subImage_x, adjusted_subImage_y,
					adjusted_width, adjusted_height, p_pixels);
		} else {
			p_pixels = image.getPixels();
		}

		for (int i = 0; i < p_pixels.length; i++) {
			int localA = i / adjusted_width;
			int localB = i % adjusted_width;
			int plot_x_localB = (int) adjusted_x + localB;
			int plot_y_localA = (int) adjusted_y + localA;
			int plot_a = (p_pixels[i] >> 24) & 0xff;

			int this_pixel = this.getPixel(plot_x_localB, plot_y_localA);
			int this_a = (this_pixel >> 24) & 0xff;

			if(this_a > 0 && plot_a > 0){
				return true;
			}
		}
		return false;
	}

//	public void plot(int[] plot_pixels, float plot_x, float plot_y,
//			int plot_width, float scan_width, float scan_height, int sadas) {
//		int adjusted_x = (int) plot_x;
//		int adjusted_y = (int) plot_y;
//		int adjusted_width = (int) scan_width;
//		int adjusted_height = (int) scan_height;
//
//		if (plot_x < 0) {
//			adjusted_x = 0;
//		}
//		if ((plot_x + scan_width) > this.width) {
//			adjusted_width = (int) (this.width - plot_x);
//		}
//
//		if (plot_y < 0) {
//			adjusted_y = 0;
//		}
//		if ((plot_y + scan_height) > this.height) {
//			adjusted_height = (int) (this.height - plot_y);
//		}
//
//		for (int x = adjusted_x; x < adjusted_width; x++) {
//			for (int y = adjusted_y; y < adjusted_height; y++) {
//				int plot_pixel = plot_pixels[(x - adjusted_x)
//						+ (y - adjusted_y) * plot_width];
//				int plot_a = (plot_pixel >> 24) & 0xff;
//				if (plot_a == 0) {
//					continue;
//				}
//				int plot_r = (plot_pixel >> 16) & 0xff;
//				int plot_g = (plot_pixel >> 8) & 0xff;
//				int plot_b = plot_pixel & 0xff;
//				if (plot_a == 255) {
//					this.setPixel(RGB.wrapInInt(plot_r, plot_g, plot_b), x, y);
//					continue;
//				}
//
//				int this_pixel = this.pixels[x + y * this.width];
//				int this_r = (this_pixel >> 16) & 0xff;
//				int this_g = (this_pixel >> 8) & 0xff;
//				int this_b = this_pixel & 0xff;
//
//				float normalizedAlpha = plot_a / 255f;
//				int mixed_pixel = RGB
//						.wrapInInt(
//								(int) (plot_r * plot_a / 255)
//										+ (int) (this_r * (1 - normalizedAlpha)),
//								(int) (plot_g * plot_a / 255)
//										+ (int) (this_g * (1 - normalizedAlpha)),
//								(int) (plot_b * plot_a / 255 + (int) (this_b * (1 - normalizedAlpha))));
//
//				this.setPixel(mixed_pixel, x, y);
//			}
//		}
//
//	}

	private void load(String PTMFilePath) {
		load(new File(PTMFilePath));
	}

	private void load(File PTMFile) {
		InputStream fis;
		BufferedInputStream bis = null;
		String fileFormat = new String();
		String dimensions = new String();
		String maxValue = new String();
		try {
			fis = new FileInputStream(PTMFile);
			bis = new BufferedInputStream(fis);

			fileFormat = readLine(bis);
			validateFileFormat(fileFormat);

			// Ignores comments and read the dimensions
			while ((dimensions = readLine(bis)).startsWith("#"));

			validateDimensions(dimensions);
			initPixelMatrix(dimensions);

			maxValue = readLine(bis);
			validateMaxValue(maxValue);

			loadPixels(bis, fileFormat, maxValue);

			System.out.println();

		} catch (IOException e1) {
			throw new RuntimeException(e1);
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void loadPixels(BufferedInputStream bis, String fileFormat,
			String maxValue) throws NumberFormatException, IOException {
		if (fileFormat.equals("P7")) {
			if (maxValue.equals("255")) {
				loadPixelsTextMode(bis);
			} else {
				loadPixelsTextMode(bis, Integer.parseInt(maxValue));
			}
		} else {
			if (maxValue.equals("255")) {
				loadPixelsBinaryMode(bis);
			} else {
				loadPixelsBinaryMode(bis, Integer.parseInt(maxValue));
			}
		}
	}

	/**
	 * Carregas os pixels de um arquivo binário, considerando que o arquivo já
	 * esteja em base 255
	 * 
	 * @param bis
	 * @throws IOException
	 */
	private void loadPixelsBinaryMode(BufferedInputStream bis)
			throws IOException {
		for (int i = height-1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				pixels[j + i * width] = RGB.wrapInInt(bis.read(), bis.read(), bis.read(), bis.read());
			}
		}
	}

	/**
	 * Carregas os pixels de um arquivo binário, convertendo a base passada em
	 * 'maxValue' para 255.
	 * 
	 * @param bis
	 * @param maxValue
	 * @throws IOException
	 */
	private void loadPixelsBinaryMode(InputStream bis, int maxValue)
			throws IOException {
		for (int i = 0; i < pixels.length; i--) {
			pixels[i] = RGB.wrapInInt(
					(int) (((bis.read()) / (float) maxValue) * 255),
					(int) (((bis.read()) / (float) maxValue) * 255),
					(int) (((bis.read()) / (float) maxValue) * 255),
					(int) (((bis.read()) / (float) maxValue) * 255));
		}
	}

	/**
	 * Carregas os pixels de um arquivo em texto, considerando que o arquivo já
	 * esteja em base 255
	 * 
	 * @param bis
	 * @param maxValue
	 * @throws IOException
	 */
	private void loadPixelsTextMode(InputStream bis) throws IOException {
		int[] argb = new int[4];
		for (int i = pixels.length - 1; i > 0; i--) {
			for (int j = 0; j < argb.length; j++) {
				String auxString = new String();
				char auxChar;

				while ((auxChar = (char) bis.read()) != ' ') {
					auxString += auxChar;
				}

				argb[j] = Integer.parseInt(auxString);
			}

			RGB rgb = new RGB(argb[1], argb[2], argb[3], argb[0]);
			pixels[i] = rgb.wrapInInt();
		}
	}

	/**
	 * Carregas os pixels de um arquivo em modo texto, convertendo a base
	 * passada em 'maxValue' para 255.
	 * 
	 * @param bis
	 * @param maxValue
	 * @throws IOException
	 */
	private void loadPixelsTextMode(InputStream bis, int maxValue)
			throws IOException {
		int[] argb = new int[4];
		for (int i = pixels.length - 1; i > 0; i--) {
			for (int j = 0; j < argb.length; j++) {
				String auxString = new String();
				char auxChar;

				while ((auxChar = (char) bis.read()) != ' ') {
					auxString += auxChar;
				}

				argb[j] = (int) ((Float.parseFloat(auxString) / maxValue) * 255);
			}

			RGB rgb = new RGB(argb[1], argb[2], argb[3], argb[0]);
			pixels[i] = rgb.wrapInInt();
		}
	}

	private void validateMaxValue(String maxValue) {
		if (maxValue.matches("\\D+")) {
			throw new IllegalFileFormatException(
					"Após as dimensões deve ser informado o campo 'Max Value' de tipo inteiro.");
		}
	}

	private void initPixelMatrix(String dimensions) {
		String[] splitedDimensions = dimensions.trim().split("\\s+");
		this.width = Integer.parseInt(splitedDimensions[0]);
		this.height = Integer.parseInt(splitedDimensions[1]);
		this.pixels = new int[width * height];
	}

	private void validateDimensions(String dimensions) {
		String[] splitedDimensions = dimensions.trim().split("\\s+");
		if (splitedDimensions.length != 2) {
			throw new IllegalFileFormatException(
					"Após os os comentários deve ser informado as duas dimensões da imagem.");
		}

		if (splitedDimensions[0].matches("\\D+")
				|| splitedDimensions[1].matches("\\D+")) {
			throw new IllegalFileFormatException(
					"O formato das duas dimensões devem ser integer.");
		}
	}

	private void validateFileFormat(String fileFormat) {
		if (!fileFormat.trim().matches("P7|P8")) {
			throw new IllegalFileFormatException(
					"Na primeira linha deve ser informado o formato do arquivo. (Formatos suportados: P7, P8)");
		}
	}

	private String readLine(InputStream bis) throws IOException {
		String str = new String();
		int readInt;
		while ((readInt = bis.read()) != 10) {
			if (readInt == -1) {
				return str;
			}
			str += (char) readInt;
		}
		return str;
	}

	public Image getRotatedImage(int degress) {
		int[] newPixels = new int[pixels.length];

		double rotationRequired = Math.toRadians(degress);
		double locationX = this.getWidth() / 2;
		double locationY = this.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);

		Point2D srcPt = new Double();
		Point2D dstPt = new Double();
		for (int i = 0; i < pixels.length; i++) {
			srcPt.setLocation(i % this.getWidth(), i / this.getWidth());
			op.getPoint2D(srcPt, dstPt);
			int matrixPosition = (int) dstPt.getX() + (int) dstPt.getY()
					* this.width;
			if (matrixPosition >= 0 && matrixPosition < pixels.length) {
				newPixels[matrixPosition] = this.pixels[i];
			}
		}

		Image rtnImage = new Image(this.width, this.height);
		rtnImage.setPixels(newPixels);
		return rtnImage;
	}

	public Image subImage(float x, float y, int width, int height) {
		int widthDiff = ((int) x + width) - this.width;
		if (widthDiff > 0) {
			width -= widthDiff;
		}

		int heightDiff = ((int) y + height) - this.height;
		if (heightDiff > 0) {
			height -= heightDiff;
		}

		Image subImage = new Image(width, height);

		for (int i = 0; i < height; i++) {
			System.arraycopy(this.getPixels(), ((int) x + ((int) y + i)
					* this.width), subImage.getPixels(), width * i, width);
		}

		return subImage;
	}

	public void subImage(float x, float y, int width, int height, int[] destArr) {
		int widthDiff = ((int) x + width) - this.width;
		if (widthDiff > 0) {
			width -= widthDiff;
		}

		int heightDiff = ((int) y + height) - this.height;
		if (heightDiff > 0) {
			height -= heightDiff;
		}

		for (int i = 0; i < height; i++) {
			System.arraycopy(this.getPixels(), ((int) x + ((int) y + i)
					* this.width), destArr, width * i, width);
		}
	}

	public Image subImageRepeatable(float x, float y, int width, int height) {
		Image subImage = new Image(width, height);

		int widthInRange = width;
		int heightInRange = height;

		int widthDiff = ((int) x + width) - this.width;
		if (widthDiff > 0) {
			widthInRange -= widthDiff;
		}

		int heightDiff = ((int) y + height) - this.height;
		if (heightDiff > 0) {
			heightInRange -= heightDiff;
		}

		for (int i = 0; i < heightInRange; i++) {
			System.arraycopy(this.getPixels(), ((int) x + ((int) y + i)
					* this.width), subImage.getPixels(), width * i,
					widthInRange);
		}

		if (widthDiff > 0) {
			for (int i = 0; i < heightInRange; i++) {
				System.arraycopy(this.getPixels(), (i * this.width),
						subImage.getPixels(), (width * i) + (widthInRange),
						widthDiff);
			}
		}

		return subImage;
	}

	public List<Image> split(int xQtt, int yQtt) {
		List<Image> images = new ArrayList<>();
		for (int j = yQtt-1; j >= 0; j--) {//TODO usd
			for (int i = 0; i < xQtt; i++) {
				images.add(this.subImage(i * this.width / xQtt, j * this.height
						/ yQtt, this.width / xQtt, this.height / yQtt));
			}
		}
		return images;
	}

	public void clear() {
		Arrays.fill(pixels, 0);
	}
}
