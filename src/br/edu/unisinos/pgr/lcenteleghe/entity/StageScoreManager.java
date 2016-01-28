package br.edu.unisinos.pgr.lcenteleghe.entity;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.edu.unisinos.pgr.lcenteleghe.data.DestrucionListener;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.scores.Score;
import br.edu.unisinos.pgr.lcenteleghe.scores.ScoreRecords;
import br.edu.unisinos.pgr.lcenteleghe.utils.TextHelper;

public class StageScoreManager extends Entity implements DestrucionListener{
	private TextHelper textHelper;
	private Score score;

	public StageScoreManager(float x, float y) {
		super(x, y);
		textHelper = new TextHelper("Arial Black", Font.BOLD, 20);
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String strDate = sdf.format(cal.getTime());
		score = new Score(0, System.getProperty("user.name"), strDate);
	}

	@Override
	public Image getImage() {
		return textHelper.getTextImage(400, 100, "Score: " + score.getPoints() , Color.WHITE);
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onEntityDestroyed(Entity destoyedEntity) {
		score.addPoint();
	}
	
	public void saveCurrentScore(){
		ScoreRecords.getInstance().addScoreRecord(score);
	}
}
