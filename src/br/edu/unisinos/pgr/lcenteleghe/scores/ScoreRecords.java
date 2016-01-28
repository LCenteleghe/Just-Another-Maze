package br.edu.unisinos.pgr.lcenteleghe.scores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import br.edu.unisinos.pgr.lcenteleghe.dataservices.ScoreRecordsDataService;

public class ScoreRecords {
	@XStreamOmitField
	private static ScoreRecords instance;
	
	private List<Score> scores;

	public ScoreRecords() {
		super();
		this.scores = new ArrayList<>();
	}
	
	public static ScoreRecords getInstance(){
		if(instance == null){
			instance = ScoreRecordsDataService.getInstance().get();
		} 
		
		return instance;
	}
	
	public void addScoreRecord(Score score){
		scores.add(score);
		ScoreRecordsDataService.getInstance().save(instance);
	}
	
	public void sortScoresDesc(){
		Collections.sort(scores);
	}
	
	public List<Score> getScores(){
		return scores;
	}
}
