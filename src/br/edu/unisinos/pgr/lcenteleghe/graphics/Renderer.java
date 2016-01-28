package br.edu.unisinos.pgr.lcenteleghe.graphics;

import org.lwjgl.opengl.EXTBgra;
import org.lwjgl.opengl.GL11;

/**
 * Class used to call all OPENGL commands.
 * @author Luis Gustavo
 *
 */
public class Renderer{
	
	public static void renderImage(Image image){
		GL11.glDrawPixels(image.getWidth(), image.getHeight(), EXTBgra.GL_BGRA_EXT,GL11.GL_UNSIGNED_BYTE, image.getByteBuffer());
	}
	
}
