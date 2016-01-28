package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.*;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;

public class Button extends Entity {

	private boolean active;
	private boolean pressed;
	private Animation buttons = new Animation(1, 2, "buttons.ptm");
	private Animation buttonsPressed = new Animation(1, 2, "buttons_pressed.ptm");
	private Text text;
	private Image buttonImage = this.buttons.getFrame(0);
	
	public Button(float x, float y, String text) {
		super(x, y);
		this.active = false;
		this.text = new Text(x, y, text);
	}
	
	public Button(String text, double positionY){
		super(300f, (float)positionY*100);
		this.active = false;
		this.text = new Text(300f, (float)positionY*100, text);
	}

	public Image getImage() {
		this.updateImage();
		return this.buttonImage;
	}

	public void updateImage(){
		if(this.pressed == false){
			if(this.active == true){
				this.buttonImage = this.buttons.getFrame(0);
				this.buttonImage.plot(this.text.getImage(), (210 - this.text.getNumberOfCharacters()*17), 42);
			}
			else{
				this.buttonImage = this.buttons.getFrame(1);
				this.buttonImage.plot(this.text.getImage(), (210 - this.text.getNumberOfCharacters()*17), 32);
			}
		}
		else{
			if(this.active == true){
				this.buttonImage = this.buttonsPressed.getFrame(0);
				this.buttonImage.plot(this.text.getImage(), (210 - this.text.getNumberOfCharacters()*17), 32);
			}
			else{
				this.buttonImage = this.buttonsPressed.getFrame(1);
				this.buttonImage.plot(this.text.getImage(), (210 - this.text.getNumberOfCharacters()*17), 32);
			}
		}
	}
	
	@Override
	public float getWidth() {
		return super.getX();
	}

	@Override
	public float getHeight() {
		return super.getY();
	}

	public boolean isActive() {
		return active;
	}
	
	public boolean checkActive(int x, int y){
		if(x > (this.getWidth()+10) && x < (this.getWidth() + 435) &&
				y > (this.getHeight()+15) && y < (this.getHeight()+115))
			return true;

		//if(y > (this.getHeight()+15) && y < (this.getHeight()+115))
			//return true;	
		
		return false;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

}
