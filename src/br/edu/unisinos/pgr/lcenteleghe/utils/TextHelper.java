package br.edu.unisinos.pgr.lcenteleghe.utils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;


public class TextHelper {
	private Font font;
	
	public TextHelper(String fontFamily,int fontStyle, int fontSize) {
		font = new Font(fontFamily, Font.BOLD, fontSize);
	}
	
	public Image getTextImage(int imageWidth, int imageHeight, String text, Color color) {
		
		BufferedImage bi = new BufferedImage( imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bi.getGraphics();
	    graphics.setColor(color);
	    graphics.setFont(font);
	    
	    int y = 0;
	    for (String line : text.split("\n"))
	    	graphics.drawString(line, 0, y += graphics.getFontMetrics().getHeight());
	        
	    AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
	    tx.translate(0, -bi.getHeight(null));
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    bi = op.filter(bi, null);   
	     
		int[] pixels = ((DataBufferInt) bi.getRaster().getDataBuffer()).getData();
		
		Image image = new Image(imageWidth,imageHeight,pixels);
	//	Image.fullPopulate(image, RGB.getRandomRGB());
		
		return image;
	}
	
}
