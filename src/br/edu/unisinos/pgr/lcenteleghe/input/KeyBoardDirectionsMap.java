package br.edu.unisinos.pgr.lcenteleghe.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import br.edu.unisinos.pgr.lcenteleghe.position.Direction;

public class KeyBoardDirectionsMap {
	private static KeyBoardDirectionsMap instance;
	private Map<Integer, Direction> map;	
	
	public KeyBoardDirectionsMap() {
		super();
		this.map = new HashMap<Integer, Direction>();
		
		this.map.put(Keyboard.KEY_W, Direction.NORTH);
		this.map.put(Keyboard.KEY_E, Direction.NORTHEAST);
		this.map.put(Keyboard.KEY_Q, Direction.NORTHWEST);
		this.map.put(Keyboard.KEY_A, Direction.WEST);
		this.map.put(Keyboard.KEY_D, Direction.EAST);
		this.map.put(Keyboard.KEY_S, Direction.SOUTH);
		this.map.put(Keyboard.KEY_C, Direction.SOUTHEAST);
		this.map.put(Keyboard.KEY_Z, Direction.SOUTHWEST);
		
	}

	public static KeyBoardDirectionsMap getInstance(){
		if(instance == null){
			instance = new KeyBoardDirectionsMap();
		}
		
		return instance;
	}
	
	public Direction getDirection(Integer key){
		return map.get(key);
	}
	
	public boolean hasDirectionForKey(Integer key){
		return map.containsKey(key);
	}
}
