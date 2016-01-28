package br.edu.unisinos.pgr.lcenteleghe.dataservices;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import br.edu.unisinos.pgr.lcenteleghe.data.EntitiesMap;
import br.edu.unisinos.pgr.lcenteleghe.entity.Chest;
import br.edu.unisinos.pgr.lcenteleghe.entity.FinalOrb;
import br.edu.unisinos.pgr.lcenteleghe.entity.Gate;
import br.edu.unisinos.pgr.lcenteleghe.entity.HpPotion;
import br.edu.unisinos.pgr.lcenteleghe.entity.Item;
import br.edu.unisinos.pgr.lcenteleghe.entity.Key;
import br.edu.unisinos.pgr.lcenteleghe.entity.LanternFuel;
import br.edu.unisinos.pgr.lcenteleghe.entity.MapEntity;
import br.edu.unisinos.pgr.lcenteleghe.entity.MpPotion;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.tiles.IsometricDiamondView;
import br.edu.unisinos.pgr.lcenteleghe.tiles.MapLayer;
import br.edu.unisinos.pgr.lcenteleghe.tiles.PartialTileSet;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMap;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileSet;
import br.edu.unisinos.pgr.lcenteleghe.tmx.Layer;
import br.edu.unisinos.pgr.lcenteleghe.tmx.Map;
import br.edu.unisinos.pgr.lcenteleghe.tmx.Property;
import br.edu.unisinos.pgr.lcenteleghe.tmx.Tileset;
import br.edu.unisinos.pgr.lcenteleghe.tmx.Tileset.Tile;

public class TileMapDataService {
	private static TileMapDataService instance;
	private HashMap<Integer, List<Property>> tilesProperty;
	private List<MapEntityData> entitiesToCreate;

	public static TileMapDataService getInstance() {
		if (instance == null) {
			instance = new TileMapDataService();
		}
		return instance;
	}

	public TileMapDataService() {
		super();
		this.tilesProperty = new HashMap<>();
		this.entitiesToCreate = new ArrayList<>();
	}

	public TileMapView getTileMapViewFromXML() {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Map.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			File XMLfile = new File("map.tmx");
			Map tmxMap = (Map) jaxbUnmarshaller.unmarshal(XMLfile);

			/*----------------------------------------------------------------------*/
			TileSet tileSetMV = new TileSet();
			for (Tileset tileSet : tmxMap.getTileset()) {
				tileSetMV.add(new PartialTileSet(tileSet.getImage().getSource()
						.replaceAll(".png", ".ptm"), tileSet.getFirstgid()
						.intValue(), tileSet.getTilewidth().intValue(), tileSet
						.getTileheight().intValue()));

				for (Tile tile : tileSet.getTile()) {
					tilesProperty.put(tile.getId().intValue()
							+ tileSet.getFirstgid().intValue(), tile
							.getProperties().getProperty());
				}
			}

			/*----------------------------------------------------------------------*/
			List<MapLayer> mapLayers = new ArrayList<>();
			for (int k = 0; k < tmxMap.getLayerOrImagelayerOrObjectgroup()
					.size(); k++) {
				if (tmxMap.getLayerOrImagelayerOrObjectgroup().get(k) instanceof Layer) {
					Layer layer = (Layer) tmxMap
							.getLayerOrImagelayerOrObjectgroup().get(k);
					int cols = layer.getWidth().intValue();
					int rows = layer.getHeight().intValue();
					int ids[][] = new int[cols][rows];

					for (int i = 0; i < rows; i++) {
						for (int j = 0; j < cols; j++) {
							int id = layer.getData().getTile()
									.get(j + i * cols).getGid().intValue();
							if (tilesProperty.containsKey(id)) {
								ids[i][j] = handleProperties(id, j, i, k);
							} else {
								ids[i][j] = id;
							}
						}
					}

					mapLayers.add(new MapLayer(ids));
				}
			}

			TileMap tilemapMV = new TileMap(mapLayers.subList(1,
					mapLayers.size()), mapLayers.get(0), tmxMap.getWidth()
					.intValue(), tmxMap.getHeight().intValue(), tmxMap
					.getTilewidth().intValue(), tmxMap.getTileheight()
					.intValue(), tileSetMV.getTileById(1).getImage());

			TileMapView tileMapView = new IsometricDiamondView(tileSetMV,
					tilemapMV);

			for (MapEntityData mapEntityData : entitiesToCreate) {
				tileMapView.addMapEntity(createMapEntitiesBasedOnEntityData(
						mapEntityData, tileMapView));
			}

			return tileMapView;

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	private int handleProperties(int id, int row, int col, int layer) {
		boolean visible = false;
		for (Property property : tilesProperty.get(id)) {
			visible = property.getName().equals("visible")
					&& property.getValue().equals("true");

			if (property.getName().equals("class")) {
				entitiesToCreate.add(new MapEntityData(new MatrixPosition(row,
						col), layer, tilesProperty.get(id)));
			}
		}

		return visible ? id : 0;
	}

	private MapEntity createMapEntitiesBasedOnEntityData(
			MapEntityData mapEntityData, TileMapView tileMapView) {

		if (mapEntityData.getLayer() == 0) {
			throw new IllegalArgumentException(
					"Map Entities are not allowed on the collision layer.");
		}

		for (Property property : mapEntityData.getProperties()) {
			if (property.getName().equals("class")) {
				Item chestItem = null;
				switch (property.getValue().toLowerCase()) {

				case "chest":
					boolean hasItem = false;
					Direction direction = Direction.SOUTHWEST;
					for (Property prop : mapEntityData.getProperties()) {
						if (prop.getName().equals("item")) {
							hasItem = true;
							switch (prop.getValue().toLowerCase()) {
							case "lanternfuel":
								chestItem = new LanternFuel();
								break;
							case "hppotion":
								chestItem = new HpPotion();
								break;
							case "mppotion":
								chestItem = new MpPotion();
								break;
							case "key":
								chestItem = new Key();
								break;
							default:
								throw new IllegalArgumentException(
										"Invalid chest item.");
							}
						}
						if (prop.getName().equals("direction")) {
							hasItem = true;
							switch (prop.getValue().toLowerCase()) {
							case "southeast":
								direction = Direction.SOUTHEAST;
								break;
							case "southwest":
								direction = Direction.SOUTHWEST;
								break;
							case "northwest":
								direction = Direction.NORTHWEST;
								break;
							case "northeast":
								direction = Direction.NORTHEAST;
								break;
							default:
								throw new IllegalArgumentException(
										"Invalid chest item.");
							}
						}
					}
					if(!hasItem){
						throw new IllegalArgumentException("A chest has to have at least on item.");
					}

					return new Chest(mapEntityData.matrixPosition, tileMapView,
							mapEntityData.getLayer() - 1, chestItem, direction);
					
				case "gate":
					return new Gate(mapEntityData.matrixPosition, tileMapView, mapEntityData.getLayer() - 1);

				case "finalorb":
					return new FinalOrb(mapEntityData.matrixPosition,
							tileMapView, mapEntityData.getLayer() - 1);
				default:
					throw new IllegalArgumentException("Invalid class.");
				}
			}
		}

		return null;
	}

	protected class MapEntityData {

		private MatrixPosition matrixPosition;
		private int layer;
		private List<Property> properties;

		protected MatrixPosition getMatrixPosition() {
			return matrixPosition;
		}

		public MapEntityData(MatrixPosition matrixPosition, int layer,
				List<Property> properties) {
			super();
			this.matrixPosition = matrixPosition;
			this.layer = layer;
			this.properties = properties;
		}

		protected void setMatrixPosition(MatrixPosition matrixPosition) {
			this.matrixPosition = matrixPosition;
		}

		protected int getLayer() {
			return layer;
		}

		protected void setLayer(int layer) {
			this.layer = layer;
		}

		protected List<Property> getProperties() {
			return properties;
		}

		protected void setProperties(List<Property> properties) {
			this.properties = properties;
		}

	}

}
