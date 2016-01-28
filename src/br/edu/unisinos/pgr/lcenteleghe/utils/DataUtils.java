package br.edu.unisinos.pgr.lcenteleghe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;

public class DataUtils {

	 public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
	        Random random = new Random();
			int x = random.nextInt(clazz.getEnumConstants().length);
	        return clazz.getEnumConstants()[x];
	 }
}
