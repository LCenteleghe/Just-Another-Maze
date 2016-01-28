package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.dataservices.GameSettings;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;

public class Text extends Entity {

	private String text;
	private float charactersDistance = 38;
	private Animation characters = new Animation(6, 10, "texts.ptm");
	//private Animation characters = new Animation(6, 10, "tennis.ptm");
	//private Image textImage = new Image(500, 500);
	private Image textImage = new Image(GameSettings.getInstance().getScreenWidth(), 
			GameSettings.getInstance().getScreenHeight());
	
	public Text(float x, float y, String text) {
		super(x, y);
		this.text = text;
		this.plotText();
	}

	public Image getImage() {
		return this.textImage;
	}
	
	public int getNumberOfCharacters(){
		return this.text.length();
	}
	
	public Image getImage(int bitmapPosition){
		return this.characters.getFrame(bitmapPosition);
	}
	
	public void plotText(){
		char[] characters = this.text.toLowerCase().toCharArray();
		for(int i = 0; i<characters.length; i++){
			this.textImage.plot(this.getImage(this.characterToBitmapPosition(characters[i])), 
					this.charactersDistance*i, 0);
		}
		
	}

	public int characterToBitmapPosition(char character){
		char lowerCaseCharacter = Character.toLowerCase(character);

		switch(lowerCaseCharacter){
		case 'a':	return 0;
		case 'b':	return 1;
		case 'c':	return 2;
		case 'd':	return 3;
		case 'e':	return 4;
		case 'f':	return 5;
		case 'g':	return 6;
		case 'h':	return 7;
		case 'i':	return 8;
		case 'j':	return 9;
		case 'k':	return 10;
		case 'l':	return 11;
		case 'm':	return 12;
		case 'n':	return 13;
		case 'o':	return 14;
		case 'p':	return 15;
		case 'q':	return 16;
		case 'r':	return 17;
		case 's':	return 18;
		case 't':	return 19;
		case 'u':	return 20;
		case 'v':	return 21;
		case 'w':	return 22;
		case 'x':	return 23;
		case 'y':	return 24;
		case 'z':	return 25;
		case 'á':	return 26;
		case 'à':	return 27;
		case 'â':	return 28;
		case 'é':	return 29;
		case 'ê':	return 30;
		case 'í':	return 31;
		case 'ó':	return 32;
		case 'ô':	return 33;
		case 'ú':	return 34;
		case '<':	return 35;
		case '>':	return 36;
		case '.':	return 37;
		case ',':	return 38;
		case ':':	return 39;
		case '_':	return 40;
		case '+':	return 41;
		case '-':	return 42;
		case '=':	return 43;
		case '*':	return 44;
		case '|':	return 45;
		case '@':	return 46;
		case '#':	return 47;
		case '(':	return 48;
		case ')':	return 49;
		case '§':	return 50;
		case '¹':	return 51;
		case '²':	return 52;
		case '³':	return 53;
		case '/':	return 54;
		case '\\':	return 55;
		case '"':	return 56;
		case '?':	return 57;
		case '!':	return 58;
		case ' ':	return 59;
		}
		
		//Exception launched if the parameter is a character not present in the bitmap
		throw new IllegalArgumentException(); 
	}
	
	@Override
	public float getWidth() {
		return super.getX();
	}

	@Override
	public float getHeight() {
		return super.getY();
	}

}
