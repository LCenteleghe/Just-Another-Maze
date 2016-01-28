package br.edu.unisinos.pgr.lcenteleghe.graphics.animation;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;


public class Animation {
	private List<Image> frames;
	private int currentFrame;
	private int framesNumber;
	
	
	public Animation getSubAnimation(int idx, int len){
		Animation animation = new Animation();
		
		for (int i = idx; i < idx+len; i++) {
			animation.frames.add(this.frames.get(i));
		}
		
		animation.framesNumber = len+1;
		animation.currentFrame = 0;
		
		return animation;
	}
	
	private Animation(){
		frames = new ArrayList<>();
	};
	
	/**
	 * Create a new animation.
	 * @param horizontalFrames Number of horizontal frames that the PTMFramesSet will be divided.
	 * @param PTMFramesSet PTM image path with the animation tiles.
	 */
	public Animation(int horizontalFramesNumber, String PTMFramesSetPath){
		this(horizontalFramesNumber, new Image(PTMFramesSetPath));
	}
	
	/**
	 * Create a new animation.
	 * @param horizontalFrames Number of horizontal frames that the PTMFramesSet will be divided.
	 * @param verticalFramesNumber Number of vertical frames that the PTMFramesSet will be divided.
	 * @param PTMFramesSet PTM image path with the animation tiles.
	 */
	public Animation(int cols, int rows, String PTMFramesSetPath){
		this(cols,rows, new Image(PTMFramesSetPath));
	}
	
	/**
	 * Create a new animation.
	 * @param horizontalFrames Number of horizontal frames that the PTMFramesSet will be divided.
	 * @param PTMFramesSet PTM image with the animation tiles.
	 */
	public Animation(int horizontalFramesNumber, Image PTMFramesSet){
		this(horizontalFramesNumber, 1, PTMFramesSet);
	}
	
	/**
	 * Create a new animation.
	 * @param horizontalFrames Number of horizontal frames that the PTMFramesSet will be divided.
	 * @param verticalFramesNumber Number of vertical frames that the PTMFramesSet will be divided.
	 * @param PTMFramesSet PTM image with the animation tiles.
	 */
	public Animation(int cols, int rows, Image PTMFramesSet){
		frames = PTMFramesSet.split(cols, rows);
		currentFrame = 0;
		framesNumber = cols*rows;
	}
	
	/**
	 * Returns the next animation image.
	 */
	public Image nextFrame(boolean loop){
		if(currentFrame>=framesNumber-1){
			if(loop){
				currentFrame = 0;
			} else {
				return null;
			}
		}
		
		return frames.get(currentFrame++);
	}
	
	public Image getFrame(int idx){
		return frames.get(idx);
	}
	
	/**
	 * Returns the next animation image.
	 */
	public Image previousFrame(){
		if(currentFrame>0){
			return frames.get(--currentFrame);
		}
		
		return null;
	}
	
	public boolean hasNext(){
		return currentFrame < framesNumber-1;
	}
	
	public boolean has(int fwrdIdx){
		return currentFrame < framesNumber-fwrdIdx;
	}
}
