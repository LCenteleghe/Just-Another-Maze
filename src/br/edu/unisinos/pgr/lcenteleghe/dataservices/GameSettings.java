package br.edu.unisinos.pgr.lcenteleghe.dataservices;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Class used for the game settings.
 * @author Luis Gustavo
 *
 */
public class GameSettings {
	private static GameSettings instance;	
	private static String gameSettingsFile = "settings.xml";

	private int screenWidth;
	private int screenHeight;
	
	private GameSettings() {
	}

	public static GameSettings getInstance() {
		if(instance == null){
			instance = getGameSettingsFromXML();
		}
		
		return instance;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	private void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	private void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	
	private static GameSettings getGameSettingsFromXML(){
		try {
			File fXmlFile = new File(gameSettingsFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			GameSettings gs = new GameSettings();
			gs.setScreenWidth(Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent()));
			gs.setScreenHeight(Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent()));

			return gs;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
