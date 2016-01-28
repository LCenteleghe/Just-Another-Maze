package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;

public class Inventory extends Entity {

	public Inventory(float x, float y) {
		super(x, y);
		
	}
	
	public Inventory() {
		super();
	}

	private Item itens[] = new Item[9];
	private Image background_base = new Image("inventory.ptm");
	private Image background = new Image(background_base.getWidth(), background_base.getHeight());

	public Image getImage(){
		this.updateImage();
		return this.background;
	}
	
	public void updateImage(){
		background.plot(background_base);
		for(int i = 0; i< this.itens.length; i++){
			if(this.itens[i] != null){
				Image item = this.itens[i].getImage();
				switch (i){
				case 0:
				case 1:
				case 2:
					this.background.plot(item, (70 + i*153), 375);
					break;
				case 3:
				case 4:
				case 5:
					this.background.plot(item, (70 + (i-3)*153), 225);
					break;
				case 6:
				case 7:
				case 8:
					this.background.plot(item, (70 + (i-6)*153), 50);
					break;
				}
			}
		}
	}
	
	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}
	
	public void insertItem(Item item){
		for(int i = 0; i<this.itens.length; i++){
			if(this.itens[i] == null){
				this.itens[i] = item;
				return;
			}
		}
	}
	
	public void insertItem(Item item, int position){
		if(this.itens[position] != null)
			return;
		else{
			this.itens[position] = item;
		}
	}
	
	public void swapItens(int position1, int position2){
		if(position1 > 8 || position1 < 0 || position2 > 8 || position2 < 0)
			throw new IllegalArgumentException();
		
		if(this.itens[position1] == null && this.itens[position2] == null)
			return;
		else if(this.itens[position1] != null && this.itens[position2] != null){
			Item temp = this.itens[position1];
			this.itens[position1] = this.itens[position2];
			this.itens[position2] = temp;
		}
		else if(this.itens[position1] == null)
			this.itens[position1] = this.itens[position2];
		else
			this.itens[position2] = this.itens[position1];
	}
	
	public Item removeItem(int position){
		if(this.itens[position] != null){
			Item temp = this.itens[position];
			this.itens[position] = null;
			return temp;
		}
		else
			return null;
	}

	public int getLength(){
		return this.itens.length;
	}
	
	public Item getItem(int position){
		if(this.itens[position] != null)
			return this.itens[position];
		else
			return null;
	}
	
	public boolean hasKey(boolean remove){
		for (int i = 0; i < itens.length; i++) {
			if(getItem(i)!=null && getItem(i) instanceof Key){
				removeItem(i);
				return true;
			}
		}
		
		return false;
	}
	
	public Item getItem(int x, int y, boolean removeAfterGet) {
		int pressedButton;
		if(x > 350 && x < 450 && y > 450 && y < 590)
			pressedButton = 0;
		else if(x > 500 && x < 600 && y > 450 && y < 590)
			pressedButton = 1;
		else if(x > 650 && x < 750 && y > 450 && y < 590)
			pressedButton = 2;
		else if(x > 350 && x < 450 && y > 290 && y < 430)
			pressedButton = 3;
		else if(x > 500 && x < 600 && y > 290 && y < 430)
			pressedButton = 4;
		else if(x > 650 && x < 750 && y > 290 && y < 430)
			pressedButton = 5;
		else if(x > 350 && x < 450 && y > 130 && y < 270)
			pressedButton = 6;
		else if(x > 500 && x < 600 && y > 130 && y < 270)
			pressedButton = 7;
		else if(x > 650 && x < 750 && y > 130 && y < 270)
			pressedButton = 8;
		else
			return null;
		
		Item item =  getItem(pressedButton);
		if(removeAfterGet){
			removeItem(pressedButton);
			updateImage();
		}
		
		return item;
	}

}
