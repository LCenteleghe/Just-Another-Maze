package br.edu.unisinos.pgr.lcenteleghe.tiles;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.data.EntitiesMap;
import br.edu.unisinos.pgr.lcenteleghe.entity.Entity;
import br.edu.unisinos.pgr.lcenteleghe.entity.MapEntity;
import br.edu.unisinos.pgr.lcenteleghe.entity.MoveToCursor;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.position.Point;

public abstract class  TileMapView extends Entity{
	protected float width;
	protected float height;
	protected float x;
	protected float y;
	protected TileSet tileSet;
	protected TileMap tileMap;
	protected Image image;
	protected boolean spotLightMode;
	protected EntitiesMap entitiesMap;
	protected MapEntity player;
	protected List<SpotLight> spotLights;
	protected MapEntity moveToCursor;
	
	public TileMapView(TileSet tileSet, TileMap tileMap) {
		super(0,0);
		this.tileSet = tileSet;
		this.tileMap = tileMap;
		this.spotLights = new ArrayList<>();
		this.entitiesMap = new EntitiesMap(tileMap.getRows(), tileMap.getCols(), tileMap.getLayers());
		
		createBaseImage();
	}
	
	public void setMoveToCursor(MoveToCursor moveToCursor){
		this.moveToCursor = moveToCursor;
	}
	
	public TileMapView(TileSet tileSet, TileMap tileMap, boolean spotLightMode){
		this(tileSet, tileMap);
		spotLightMode = true;
	}
	
	public void addPlayer(MapEntity player) {
		this.player = player;
	}

	private void createBaseImage() {
		int tilesWidth = tileMap.getTilesWidth();
		int tilesHeight = tileMap.getTilesHeight();
		int halfTilesWidth = tileMap.getTilesWidth() / 2;
		int halfTilesHeight = tileMap.getTilesHeight() / 2;

		image = new Image(tileMap.getCols() * tilesWidth + halfTilesWidth,
				tileMap.getRows() * tilesHeight + halfTilesHeight);
	}

	private void renderFullMap() {
		for(int i = 0; i < tileMap.getMapLayers().size(); i++){
			quadRender(0, 0, tileMap.getCols(), tileMap.getRows(), i);
		}
	}
	
	private void circularRender(int row, int col, int radious, int layer, boolean hasBorder) {
			int blackTile = -1;
			quadRenderByTileId(row-8, col-radious-4, 4, 10, blackTile);
			
			for (int i = col-radious, j = 0; i <= col; i++, j++) {
				quadRenderByTileId(row-j-8, i,  1, 8, blackTile);

				quadRender(row-j, i, 1, j*2+1, layer);
			
				quadRenderByTileId(row-j+j*2, i, 1, 3, blackTile);
			}
			
			if(player.getLayer()==layer){
				renderPlayer();
			}
			
			for (int i = col+1, j = radious-1; i <= col+radious; i++, j--) {
				quadRenderByTileId(row-j-8, i, 1, 8, blackTile);
				quadRender(row-j, i, 1, j*2+1, layer);
				quadRenderByTileId(row-j+j*2, i, 1, 3, blackTile);
			}
			
			quadRenderByTileId(row-1, col+radious+1, 1, 3, blackTile);
	}

	private void renderPlayer() {
		image.plot(player.getImage(), player.getX(), player.getY());
	}
	
	public void addSpotLight(SpotLight spotLight){
		spotLights.add(spotLight);
	}
	
	private void circularRender(int row, int col, int radious, boolean hasBorder) {
		for(int i = 0; i < tileMap.getMapLayers().size(); i++){
			circularRender(row, col, radious, i, hasBorder);
		}
	}

	public float getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public float getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean hasColisionTileAt(int row, int col){
		return tileMap.hasColisionTileAt(row, col);
	}
	
	public void removeColisionTileAt(int row, int col){
		 tileMap.removeColisionTileAt(row, col);
	}
	
	public void quadRender(int row, int col, int width, int height, int layer) {
		for (int i = row; i < row+height && i < tileMap.getRows(); i++) {
			for (int j = col; j < col+width && j < tileMap.getCols(); j++) {
				Point screenPoint = matrixPointToScreenPoint(i, j, false);
				this.image.plot(tileSet.getTileById(tileMap.getTileId(i, j, layer)).getImage(), screenPoint.getX(), screenPoint.getY());
				
				if(this.entitiesMap.hasEntityAt(i, j, layer)){
					MapEntity mapEntity = this.entitiesMap.get(i, j, layer);
					if(mapEntity.isVisible()){
						this.image.plot(mapEntity.getImage(), mapEntity.getX(), mapEntity.getY());
					}
				}
			}
		}
	}
	public void quadRenderByTileId(int row, int col, int width, int height, int tileId) {
		for (int i = row; i < row+height && i < tileMap.getRows(); i++) {
			for (int j = col; j < col+width && j < tileMap.getCols(); j++) {
				Point screenPoint = matrixPointToScreenPoint(i, j, false);
				this.image.plot(tileSet.getTileById(tileId).getImage(), screenPoint.getX(), screenPoint.getY());
			}
		}
	}
	
	public void turnOnSpotLightMode(){
		this.spotLightMode = true;
	}
	
	public void turnOffSpotLightMode(){
		this.spotLightMode = false;
	}
	

//	public void updateDynamicMapEntitiesOnMap() {
//		for (MapEntity mapEntity : mapEntities) {
//			//TODO HARD CODED HARD CODED HARD CODED
//			for (int i = 0; i < mapEntity.getLayer(); i++) {
//				MatrixPosition matrixIni = this.screenPointToMatrixPoint(mapEntity.getX(), mapEntity.getY(), true);
//				quadRender(matrixIni.getRow()-5, matrixIni.getCol()-5, 50, 50, i);
//			}
//			
//			{
//				MatrixPosition matrixIni = this.screenPointToMatrixPoint(mapEntity.getX(), mapEntity.getY(), true);
//				quadRender(matrixIni.getRow()-5, matrixIni.getCol()-5, 6, 50, mapEntity.getLayer());
//				
//				this.image.plot(mapEntity.getImage(), mapEntity.getX(), mapEntity.getY()); 
//				
//				matrixIni = this.screenPointToMatrixPoint(mapEntity.getX(), mapEntity.getY(), true);
//				quadRender(matrixIni.getRow()-5, matrixIni.getCol()+1, 50, 50, mapEntity.getLayer());
//			}
//			
//			for (int i = mapEntity.getLayer()+1; i < tileMap.getMapLayers().size(); i++) {
//				MatrixPosition matrixIni = this.screenPointToMatrixPoint(mapEntity.getX(), mapEntity.getY(), true);
//				quadRender(matrixIni.getRow(), matrixIni.getCol(), 5, 5, i);
//			}
//			
//		}
//	}
	
	private void renderByStoplights() {
		for (SpotLight spotLight : spotLights) {
			circularRender(spotLight.getMatrixPosition().getRow(), 
						   spotLight.getMatrixPosition().getCol(), spotLight.getRadious(), 
						   true);
		}
	}
	
	public abstract MatrixPosition calculateMatrixWalk(MatrixPosition matrixPosition, Direction direction);
	
	public abstract Point matrixPointToScreenPoint(int row, int col, boolean centralized);
	
	public abstract MatrixPosition screenPointToMatrixPoint(float x, float y, boolean autoBound);
	
	public abstract MatrixPosition screenPointToMatrixPoint(float x, float y);

	@Override
	public Image getImage() {
		if(spotLightMode){
			renderByStoplights();
		} else {
			renderFullMap();
		}
		renderCursor();
		return image;
	}

	public void addMapEntity(MapEntity mapEntity) {
		this.entitiesMap.addEntity(mapEntity);
	}

	public boolean hasEntityAt(int row, int col, int layer) {
		return entitiesMap.hasEntityAt(row, col, layer);
	}
	
	public MapEntity get(int row, int col, int layer){
		return entitiesMap.get(row, col, layer);
	}
	
	public int getMaxLayer(){
		return tileMap.getLayers()-1;
	}
	
	public void showMoveToCursorAt(MatrixPosition matrixPosition){
		moveToCursor.setMatrixPosition(matrixPosition);
	}
	
	private void renderCursor(){
		if(moveToCursor.isVisible()){
			this.image.plot(moveToCursor.getImage(), moveToCursor.getX(), moveToCursor.getY());
		}
	}
}
