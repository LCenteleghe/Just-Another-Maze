package br.edu.unisinos.pgr.lcenteleghe.scores;

public class Score implements Comparable<Score>{
	private String playerName;
	private int points;
	private String date;
	
	public Score(int points, String playerName, String date) {
		super();
		this.points = points;
		this.playerName = playerName;
		this.date = date;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void addPoint(){
		points++;
	}

	@Override
	public int compareTo(Score score) {
		if(score.getPoints()==this.getPoints()){
			return 0;
		}
		if(score.getPoints()>this.getPoints()){
			return 1;
		}
		return -1;
	}
	
}
