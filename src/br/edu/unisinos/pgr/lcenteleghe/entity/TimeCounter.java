package br.edu.unisinos.pgr.lcenteleghe.entity;

import java.awt.Color;
import java.awt.Font;

import br.edu.unisinos.pgr.lcenteleghe.assync.Scheduler;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.utils.TextHelper;

public class TimeCounter extends Entity{
	private TextHelper textHelper;
	private long duration;

	public TimeCounter(float x, float y, long duration) {
		super(x, y);
		textHelper = new TextHelper("Arial Black", Font.BOLD, 20);
		this.duration = duration;
	}

	@Override
	public Image getImage() {
		return textHelper.getTextImage(400, 100, "Time Remaining: " + timeRemaining() , Color.WHITE);
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

	public String timeRemaining(){
		return String.valueOf(duration);
	}
	
	public void start(){
		Scheduler.repeate(1000, "timeUpdate", this);
	}
	
	public void timeUpdate(){
		if(duration>0)
			duration--;
	}
	
	public boolean hasCompleted(){
		return duration==0;
	}
}
