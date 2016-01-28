package br.edu.unisinos.pgr.lcenteleghe.stage.customSettings;

import java.util.ArrayList;
import java.util.List;

public class GameStagesPack {
	private List<StageCS> StagesList;

	public GameStagesPack(
			List<StageCS> customSettingsList) {
		super();
		this.StagesList = customSettingsList;
	}

	public GameStagesPack() {
		super();
		this.StagesList = new ArrayList<>();
	}

	public List<StageCS> getCustomSettingsList() {
		return StagesList;
	}

	public void addGameStageCustomSettings(StageCS gameStageCustomSettings){
		this.StagesList.add(gameStageCustomSettings);
	}
}


