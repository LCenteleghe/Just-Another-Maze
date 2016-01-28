package br.edu.unisinos.pgr.lcenteleghe.state;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import br.edu.unisinos.pgr.lcenteleghe.entity.Button;
import br.edu.unisinos.pgr.lcenteleghe.entity.Text;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;



public class MenuGameState extends AbstractGameState{
	private static MenuGameState instance; 
	private AbstractGameState nextGameState;
    private Image background;
    private Button[] buttons;
    private int activeButton;
    public final int a = 3;
	private String[] texts = new String[]{"Jêgar", "Sobre", "Sair"};
    private Text[] about;
    private String[] aboutContent = new String[]{"Autores:"
    		+ "Luís Centeleghe,",
    		"Maurício Barbosa",
    		"",
    		"Universidade Unisinos"};
    
    
	
	@Override
	public Image getImage() {
		this.updateImage();
		return background;
	}
	
	public static MenuGameState getInstance(){
		if(instance == null){
			instance = new MenuGameState();
		}
		return instance;
	}
	
	private MenuGameState() {
		super();
		this.nextGameState = this;
		this.background = new Image("menu.ptm");
		this.buttons = new Button[3];
		for(int i = 0; i<this.a; i++)
			this.buttons[i] = new Button(this.texts[i], 6-(1.2*i+1));
		this.about = new Text[4];
		for(int i = 0; i< this.about.length; i++)
			this.about[i] = new Text((300 - i*150), 200, this.aboutContent[i].toString());
		//this.buttons[0].setActive(true);
	}

	@Override
	public void handleMouseInput(int x, int y, boolean eventButtonState) {
		boolean activatedAButton = false;
		boolean pressedAButton = false;
		for(int i = 0; i<this.buttons.length; i++)
			if(this.buttons[i].checkActive(x, y) == true){
				if(i==this.activeButton && eventButtonState == true &&
						this.buttons[i].isPressed()==true){
					return;
				}
				if(i==this.activeButton && eventButtonState == false &&
						this.buttons[i].isPressed()==false){
					return;
				}
				else if(i==this.activeButton && eventButtonState == true
						&& this.buttons[i].isPressed() == false){
					this.buttons[i].setPressed(true);
					return;
				}
				else if(i==this.activeButton && eventButtonState == false
						&& this.buttons[i].isPressed() == true){
					this.buttons[i].setPressed(false);
					if(this.activeButton == 0){
						this.buttons[this.activeButton].setPressed(true);
						try {
							Thread.sleep(100);
							this.buttons[this.activeButton].setPressed(false);
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.clickedPlayGame();
					}
					else if(this.activeButton == 1){
						this.buttons[this.activeButton].setPressed(true);
						try {
							Thread.sleep(100);
							this.buttons[this.activeButton].setPressed(false);
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.clickedAbout();
					}
					else if(this.activeButton == 2){
						this.buttons[this.activeButton].setPressed(true);
						try {
							Thread.sleep(100);
							this.buttons[this.activeButton].setPressed(false);
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.clickedExitGame();
					}
				}
					
				this.activateAButton(i);
				activatedAButton = true;
				if(eventButtonState == true){
					this.buttons[i].setPressed(true);
					pressedAButton = true;
					}
				
			}
		if(activatedAButton == false)
			this.deactivateAllButtons();
		if(pressedAButton == false)
			this.releaseAllButtons();
	}
	
	public void clickedPlayGame(){
		nextGameState = InGameState.getInstance();
	}
	
	public void clickedAbout(){
		for(int i = 0; i<this.about.length; i++)
			this.background.plot(this.about[i].getImage(), 100, 600-i*75);

	}
	
	public void clickedExitGame(){
		System.exit(0);
		Display.destroy();
	}
	
	@Override
	public void handleKeyboardInput(int eventKey, char eventCharacter,
			boolean eventKeyState) {
		if(eventKey == Keyboard.KEY_RETURN && this.activeButton != -1){
			this.buttons[this.activeButton].setPressed(true);
			try {
				Thread.sleep(100);
				this.buttons[this.activeButton].setPressed(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		if(eventKey == Keyboard.KEY_RETURN && this.activeButton == 0){
			this.clickedPlayGame();
		}
		else if(eventKey == Keyboard.KEY_RETURN && this.activeButton == 1){
			this.clickedAbout();
		}
		else if(eventKey == Keyboard.KEY_RETURN && this.activeButton == 2){
			this.clickedExitGame();
		}
		else if(eventKeyState && eventKey == Keyboard.KEY_DOWN){
			this.activateBelowButton();
		}
		else if(eventKeyState && eventKey == Keyboard.KEY_UP){
			this.activateAboveButton();
		}
	}

	public void updateImage(){
		if(this.buttons[1].isPressed() == false){
			this.background.plot(new Image("menu.ptm"));
			for(int i = 0; i<this.buttons.length; i++){
				this.background.plot(this.buttons[i].getImage(), this.buttons[i].getWidth(),
						this.buttons[i].getHeight());
			}
		}
		else{
			this.background.plot(new Image("blank.ptm"));
			this.clickedAbout();
		}
		
	}
	
	public void releaseAllButtons(){
		for(int i = 0; i<this.buttons.length; i++)
			this.buttons[i].setPressed(false);
	}
	
	public void deactivateAllButtons(){
		this.activateAButton(-1);
	}
	
	public void activateBelowButton(){
		if(this.activeButton == -1)
			this.activateAButton(0);
		else if(this.activeButton+1<this.buttons.length){
			this.buttons[this.activeButton].setActive(false);
			this.buttons[this.activeButton+1].setActive(true);
			this.activeButton++;
		}
	}
	
	public void activateAboveButton(){
		if(this.activeButton == -1)
			this.activateAButton(0);
		if(this.activeButton>0){
			this.buttons[this.activeButton].setActive(false);
			this.buttons[this.activeButton-1].setActive(true);
			this.activeButton--;
		}
	}
	
	public void activateAButton(int buttonId){
		if(buttonId == -1){
			if(this.activeButton != -1){
				for(int i = 0; i<this.buttons.length; i++)
					this.buttons[i].setActive(false);
				this.activeButton = -1;
			}
		}
		else if(buttonId<this.buttons.length && buttonId>-1 && buttonId != this.activeButton){
			if(this.activeButton == -1){
				this.buttons[buttonId].setActive(true);
				this.activeButton = buttonId;
			}
			else{
				this.buttons[this.activeButton].setActive(false);
				this.buttons[buttonId].setActive(true);
				this.activeButton = buttonId;
			}
		}
	}
	
	@Override
	public void stateUpdate() {}

	@Override
	public AbstractGameState getNextState() {
		return nextGameState;
	}

	public int getActiveButton() {
		return activeButton;
	}

	public void setActiveButton(int activeButton) {
		this.activeButton = activeButton;
	}

}
