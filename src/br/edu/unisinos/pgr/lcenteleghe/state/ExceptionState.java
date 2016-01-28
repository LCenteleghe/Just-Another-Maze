package br.edu.unisinos.pgr.lcenteleghe.state;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.utils.TextHelper;

public class ExceptionState extends AbstractGameState{
	private Image image;
	private TextHelper textHelperTimes18;
	private TextHelper textHelperTimes;
	
	
	public ExceptionState(Exception ex) {
		textHelperTimes = new TextHelper("Times", Font.BOLD, 34);
		textHelperTimes18 = new TextHelper("Times", Font.BOLD, 18);
		image  = new Image("exception-bg.ptm");
		
		image.plot(textHelperTimes.getTextImage(500, 60,"Ops! An error has occured.", Color.ORANGE),320,650);
		image.plot(textHelperTimes18.getTextImage(500, 40,"Press any key to exit.", Color.ORANGE),425, 625);
		image.plot(textHelperTimes18.getTextImage(500, 40,"Technical Information: ", Color.ORANGE),50, 500);
		
		String stackTrace = "Message: " + ex.getMessage() + "\n";
		
		for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
			stackTrace += stackTraceElement.toString() + "\n";
		}
		
		this.log(stackTrace);
		
		stackTrace += "... see more on Errors.log";
		
		image.plot(textHelperTimes18.getTextImage(1024, 500,stackTrace, Color.ORANGE),50, 0);
		
		ex.printStackTrace();
	}

	private void log(String stackTrace) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File("Errors.log"),true);
			fw.write(stackTrace);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public AbstractGameState getNextState() {
		return this;
	}

	@Override
	public void handleKeyboardInput(int eventKey, char eventCharacter, boolean eventKeyState) {
		System.exit(1);
	}

	@Override
	public void handleMouseInput(int x, int y, boolean eventButtonState) {
	}

	@Override
	public void stateUpdate() {
	}
}
