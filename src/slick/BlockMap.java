package slick;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
 
public class BlockMap {
	public  TiledMap tmap;
	public static int mapWidth;
	public static int mapHeight;
	private int square[] = {0,0,16,0,16,16,0,16}; //square shaped tile
	public ArrayList<Object> entities;
 
	public BlockMap(String ref) throws SlickException {
		entities = new ArrayList<Object>();
		tmap = new TiledMap(ref, "../ressources/images");
		mapWidth = tmap.getWidth() * tmap.getTileWidth();
		mapHeight = tmap.getHeight() * tmap.getTileHeight();
 
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				int tileID = tmap.getTileId(x, y, 0);
				if (tileID == 1 || tileID==100) {
					entities.add(new Block(x * 16, y * 16, square, "square"));
				}
			}
		}
	}
	
	
	public void draw(Graphics g){
		Iterator itr=entities.iterator();
		while(itr.hasNext()){
			Block b=(Block)itr.next();
			b.draw(g);
		}
	}
}