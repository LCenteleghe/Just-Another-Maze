package br.edu.unisinos.pgr.lcenteleghe.graphics;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.assync.Scheduler;
import br.edu.unisinos.pgr.lcenteleghe.dataservices.GameSettings;
import br.edu.unisinos.pgr.lcenteleghe.entity.Entity;

public class ViewPort implements IRenderable{
	private float x;
	private float y;
	private float xScrollRate;
	private float yScrollRate;
	private int width;
	private int height;
	private int fullWidth;
	private int fullHeight;
	private List<Entity> drawableEntities;
	private List<List<? extends Entity>> drawableEntitiesLst;
	private Image view;
	private Image background;
	private boolean autoupdate;
	private Entity followed;

	public ViewPort(Boolean autoupdate) {
		super();
		this.autoupdate = autoupdate;
	}
	
	
	public void setFollowed(Entity followed){
		this.followed = followed;
	}
	

	public ViewPort(float x, float y, float xScrollRate, float yScrollRate,
			int width, int height, Image image) {
		super();
		this.x = x;
		this.y = y;
		this.xScrollRate = xScrollRate;
		this.yScrollRate = yScrollRate;
		this.width = width;
		this.height = height;
		this.drawableEntities = new ArrayList<>();
		this.drawableEntitiesLst = new ArrayList<>();
		this.background = image;
		
	//	fullHeight = background.getHeight();
	//	fullWidth = background.getWidth();
		view = new Image(GameSettings.getInstance().getScreenWidth(), GameSettings.getInstance().getScreenHeight());
		autoupdate = true;
		updateView();
	}

	@Override
	public Image getImage() {
		if(autoupdate){
			updateView();
		}
		return view;
	}
	
	public void updateView(){
		syncWithFollowed();
		view = background.subImage(x, y, width, height);
		plotEntities();
	}


	private void syncWithFollowed() {
		if(followed!=null){
			if(followed.getX()-this.width/2>1 && followed.getX()+this.width/2 < background.getWidth()){
				this.x = followed.getX()-this.width/2;
			}
			
			if(followed.getY()-this.height/2>1 && followed.getY()-this.height/2 < background.getHeight()){
				this.y = followed.getY()-this.height/2;
			}

		}
	}
	
	public void addEntity(Entity entiy){
		drawableEntities.add(entiy);
	}
	
	// Observes a list of entities and draw it.
	public void addEntityListRef(List<? extends Entity> entities){
		drawableEntitiesLst.add(entities);
	}
	
	public void scrollLeft(){
		if(x > 0){
			x -= xScrollRate;
		}
	}
	
	public void scrollRight(){		
		if(x+width < background.getWidth()){
			x += xScrollRate;
		}
	}
	
	public void scrollUp(){
		if(y+height < background.getHeight())
			y += yScrollRate;
	}
	
	public void scrollDown(){
		if(y > 0){
			y -= yScrollRate;
		}
	}

	/**
	 * Plot all the drawable entities in the image.
	 */
	private void plotEntities(){
		for (Entity entity : drawableEntities) {
			if(entity.isVisible()){
				view.plot(entity.getImage(), entity.getX()-x, entity.getY()-y);
			}
		}
		
		for (List<? extends Entity> entityLst : drawableEntitiesLst) {
		  //Since an entity list can be concurrently modified we must transform it in an array to iterate over it.
			Entity[] entityArr = new Entity[entityLst.size()];
			entityLst.toArray(entityArr);
			for (Entity entity : entityArr) {
				if(entity.isVisible()){
					view.plot(entity.getImage(), entity.getX()-x, entity.getY()-y);
				}
			}
		}
	}
	
	public void autoScrollRight(final long millisecs){
		Scheduler.repeate(millisecs, "scrollRight", this);
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
	
	public float getYWithOffSet(float y){
		return y+this.y;
	}
	
    public float getXWithOffSet(float x){
		return x+this.x;
	}
}
