package br.edu.unisinos.pgr.lcenteleghe.graphics.animation;

import java.util.HashMap;
import java.util.Map;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.exception.DuplicatedAnimationException;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;

public class ActionsAnimationSet {
	private Map<ActionType, Map<Direction, Animation>> animations;
	
	public ActionsAnimationSet() {
		super();
		this.animations = new HashMap<ActionType, Map<Direction,Animation>>();
	}


	public void add(ActionType actionType, Direction direction, Animation animation){
		if(!animations.containsKey(actionType)){
			Map<Direction,Animation> directions = new HashMap<>();
			directions.put(direction, animation);
			
			animations.put(actionType, directions);
		} 
		else if(!animations.get(actionType).containsKey(animation)){
			animations.get(actionType).put(direction, animation);
		} else {
			throw new DuplicatedAnimationException(actionType, direction);
		}
	}
	
	public Animation get(ActionType actionType, Direction direction){
		return animations.get(actionType).get(direction);
	}
}
