package br.edu.unisinos.pgr.lcenteleghe.dataservices;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.stage.GameStage;
import br.edu.unisinos.pgr.lcenteleghe.stage.IGameStage;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.GameStagesPack;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.StageCS;

import com.thoughtworks.xstream.XStream;

/**
 * This class is used to load Game Stages from a XML file.
 */
public class GameStagesDataService extends BaseDataService<GameStagesPack>{
	private static GameStagesDataService instance;

	private GameStagesDataService() {
		super("GameStages");
	}
	
	public static GameStagesDataService getInstance(){
		if(instance == null){
			instance = new GameStagesDataService();
		}
		return instance;
	}
	
	public GameStage getLinkedGameStages(){
		List<GameStage> lstGameStage = getGameStages();
		
		GameStage currGameStage = lstGameStage.get(0);
		GameStage gameStage = currGameStage;
		
		for (int i = 1; i < lstGameStage.size(); i++) {
			currGameStage.setNextStage(lstGameStage.get(i));
			currGameStage = currGameStage.getNextState();
		}
		
		return  gameStage;
	}

	private List<GameStage> getGameStages() {
		List<GameStage> gameStagesLst = new ArrayList<>();
		
		for (StageCS gameStageCustomSetting : super.get().getCustomSettingsList()) {
			gameStagesLst.add(new GameStage(gameStageCustomSetting));
		}
		
		return gameStagesLst;
	}

	@Override
	public void setAliases(XStream xtStream) {
		xtStream.aliasPackage("", "br.edu.unisinos.pgr.lcenteleghe.stage.customSettings");
	}	
}
