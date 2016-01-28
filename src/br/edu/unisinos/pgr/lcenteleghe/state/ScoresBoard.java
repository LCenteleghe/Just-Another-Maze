package br.edu.unisinos.pgr.lcenteleghe.state;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.entity.Entity;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.scores.Score;
import br.edu.unisinos.pgr.lcenteleghe.scores.ScoreRecords;
import br.edu.unisinos.pgr.lcenteleghe.utils.TextHelper;

public class ScoresBoard extends Entity {
	private TextHelper textHelperTimes18;
	private TextHelper textHelperTimes;
	private Image board;
	
	public ScoresBoard(float x, float y) {
		super(x, y);
		textHelperTimes = new TextHelper("Times", Font.BOLD, 24);
		textHelperTimes18 = new TextHelper("Times", Font.BOLD, 18);
		board = new Image(720,500);
		drawScores();
	}

	private void drawScores() {
		ScoreRecords scoreRecords = ScoreRecords.getInstance();
		List<Score> scores = scoreRecords.getScores();
		
		board.plot(textHelperTimes.getTextImage(500, 40, " - Your Score - ", Color.ORANGE),275,375);
		String yourScore = String.format("%04d", scores.get(scores.size()-1).getPoints());
		board.plot(textHelperTimes.getTextImage(500, 40, "- " + yourScore + " -", Color.ORANGE),323,350);
		
		board.plot(textHelperTimes18.getTextImage(500, 40, " - High Scores - ", Color.ORANGE),292,245);
		scoreRecords.sortScoresDesc();
		String text = new String();
		for (int i = 0; i < 10; i++) {
			String formatedPlayerName;
			String formatedDate;
			String formatedPoints;

			if(i < scores.size()){
				formatedPlayerName = scores.get(i).getPlayerName();
				formatedDate = scores.get(i).getDate();
				formatedPoints = String.valueOf(scores.get(i).getPoints());
			} else {
				formatedPlayerName = "None";
				formatedDate = "None";
				formatedPoints = "None";
			}
			
			text += i+1 + " - Player: " + formatedPlayerName + " - Points: " + formatedPoints + " - Date: " + formatedDate + "\n";
		}
		
		board.plot(textHelperTimes18.getTextImage(700, 250, text, Color.ORANGE),10,5);
		
	}

	@Override
	public Image getImage() {
		return board;
	}

	@Override
	public float getWidth() {
		return board.getWidth();
	}

	@Override
	public float getHeight() {
		return board.getHeight();
	}

}
