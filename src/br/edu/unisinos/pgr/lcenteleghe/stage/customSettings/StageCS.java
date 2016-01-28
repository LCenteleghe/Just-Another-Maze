package br.edu.unisinos.pgr.lcenteleghe.stage.customSettings;

import java.util.ArrayList;
import java.util.List;


public class StageCS {
	private int id;
	private int gameDuration;
	private LandscapeCS landscapeBg;
	private LandscapeCS landscapeFg;
	private TargetDropperCS targetDropper;
	private List<WallCS> walls;
	private CannonCS cannon;
	
	public StageCS(LandscapeCS landscapeBg,
			LandscapeCS landscapeFg,
			TargetDropperCS targetDropper, CannonCS cannon, int id) {
		super();
		this.landscapeBg = landscapeBg;
		this.landscapeFg = landscapeFg;
		this.targetDropper = targetDropper;
		this.cannon = cannon;
		this.id = id;
	}

	public StageCS() {	
		this.walls = new ArrayList<>();
	}

	public LandscapeCS getLandscapeBg() {
		return landscapeBg;
	}

	public void setLandscapeBg(LandscapeCS landscapeBg) {
		this.landscapeBg = landscapeBg;
	}

	public LandscapeCS getLandscapeFg() {
		return landscapeFg;
	}

	public void setLandscapeFg(LandscapeCS landscapeFg) {
		this.landscapeFg = landscapeFg;
	}

	public TargetDropperCS getTargetDropper() {
		return targetDropper;
	}

	public void setTargetDropper(TargetDropperCS targetDropper) {
		this.targetDropper = targetDropper;
	}

	public void addWallCustomSettings(WallCS wallCustomSettings){
		this.walls.add(wallCustomSettings);
	}

	public List<WallCS> getWalls() {
		return walls;
	}

	public CannonCS getCannon() {
		return cannon;
	}

	public void setCannon(CannonCS cannon) {
		this.cannon = cannon;
	}

	public int getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(int gameDuration) {
		this.gameDuration = gameDuration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}


