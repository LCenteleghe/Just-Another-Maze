package br.edu.unisinos.pgr.lcenteleghe.utils;

import java.io.FileWriter;
import java.io.IOException;

import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.CannonCS;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.StageCS;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.GameStagesPack;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.LandscapeCS;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.TargetDropperCS;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.WallCS;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Baca {
	public static void main(String[] args) throws IOException {
		XStream xstream = new XStream(new StaxDriver()); 
//		xstream.alias("scores", List.class);
		xstream.aliasPackage("", "br.edu.unisinos.pgr.lcenteleghe.stage.customSettings");
//		ScoreRecords sr = new ScoreRecords();
//		sr.addScoreRecord((new Score(30, "Luis", "30/10/2013")));
		
	//	xstream.alias("Stage", GameStageCustomSettings.class);
	//	xstream.alias("wall", WallCustomSettings.class);
		
		GameStagesPack gscsp = new GameStagesPack();
		
		StageCS gs = new StageCS();
		
		gs.setCannon(new CannonCS(10, 0));
		gs.setLandscapeBg(new LandscapeCS("clouds.ptm", 0, 370));
		gs.setLandscapeFg(new LandscapeCS("landscape.ptm", 0, 0));
		gs.setTargetDropper(new TargetDropperCS(724, 800, 200, 800, 3, 50, true));
		
		gs.addWallCustomSettings(new WallCS(620, 50));
		gs.addWallCustomSettings(new WallCS(480, 400));
		gs.setGameDuration(60);
		gs.setId(1);
				
		gscsp.addGameStageCustomSettings(gs);
		
		xstream.toXML(gscsp, new FileWriter("GameStages.xml"));
		
		
		
	}
}
