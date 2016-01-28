package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.IRenderable;

public abstract class Entity implements IRenderable{
	protected float x;
	protected float y;
	protected boolean visible;
	protected boolean inMovement;
	
	public Entity(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		this.visible = true;
	}
	
	public Entity(float x, float y, boolean visible) {
		super();
		this.x = x;
		this.y = y;
		this.visible = visible;
	}
	
	

	public Entity() {
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

	public void setVisible(){
		this.visible = true;
	}
	
	public void setInvisible(){
		this.visible = false;
	}
	
	public void moveTo(final float x, final float y, final long speed){
		inMovement = true;
		bresenhamMove((int)x, (int)y, speed);
		inMovement = false;
	}
	
	private void bresenhamMove(int x2, int y2, long speed) {
	    int w = x2 - (int)x ;
	    int h = y2 - (int)y ;
	    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
	    if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
	    if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
	    if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
	    int longest = Math.abs(w) ;
	    int shortest = Math.abs(h) ;
	    if (!(longest>shortest)) {
	        longest = Math.abs(h) ;
	        shortest = Math.abs(w) ;
	        if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
	        dx2 = 0 ;            
	    }
	    int numerator = longest >> 1 ;
	    for (int i=0;i<=longest;i++) {
	        try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {e.printStackTrace();}
	        numerator += shortest ;
	        if (!(numerator<longest)) {
	            numerator -= longest ;
	            x += dx1 ;
	            y += dy1 ;
	        } else {
	            x += dx2 ;
	            y += dy2 ;
	        }
	    }
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public boolean polygonIntersects(Entity entity){
		return this.getX() + this.getWidth() > entity.getX()   &&
			   this.getX() < entity.getX() + entity.getWidth() &&
			   this.getY() + this.getHeight() > entity.getY()  &&
			   this.getY() < entity.getY() + entity.getHeight();
	}
	
	public boolean imageIntersects(Entity entity){
		float insideX = (this.getX()+this.getWidth())-entity.getX();
		float insideY = (this.getY()+this.getHeight())-entity.getY();;
		
		return entity.getImage().intersects(getImage(), insideX, insideY);
	}
	
	public abstract float getWidth();
	
	public abstract float getHeight();
	
	public void onEntityColision(Entity withWho){}

	protected boolean isInMovement() {
		return inMovement;
	}

	protected void setInMovement(boolean inMovement) {
		this.inMovement = inMovement;
	}
	
	protected void waitMovementToFinish(){
		while(isInMovement()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void interactWith(Entity entity){}
		
}
