package slick;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
 
public class Game extends BasicGame {
 
        private float playerX=340;
        private float playerY=240;
        private Animation player;
        private Polygon playerPoly;
        public BlockMap map;
 
        public Game() {
                super("one class barebone game");
        }
 
        public void init(GameContainer container) throws SlickException {
                container.setVSync(true);
                SpriteSheet sheet = new SpriteSheet("ressources/images/karbonator.png",32,32);
                map = new BlockMap("ressources/images/map01.tmx");           
                player = new Animation();
                player.setAutoUpdate(false);
                for (int frame=0;frame<3;frame++) {
                        player.addFrame(sheet.getSprite(frame,0), 150);
                }
                playerPoly = new Polygon(new float[]{
                                playerX,playerY,
                                playerX+32,playerY,
                                playerX+32,playerY+32,
                                playerX,playerY+32
                });
        }
 
        public void update(GameContainer container, int delta) throws SlickException {
                if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
                        playerX--;
                        playerPoly.setX(playerX);
                        if (entityCollisionWith()){
                                playerX++;
                                playerPoly.setX(playerX);
                        }
                }
                if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
 
                        playerX++;
                        playerPoly.setX(playerX);
                        if (entityCollisionWith()){
                                playerX--;
                                playerPoly.setX(playerX);
                        }
                }
                if (container.getInput().isKeyDown(Input.KEY_UP)) {
 
                        playerY--;
                        playerPoly.setY(playerY);
                        if (entityCollisionWith()){
                                playerY++;
                                playerPoly.setY(playerY);
                        }
                }
                if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
                        playerY++;
                        playerPoly.setY(playerY);
                        if (entityCollisionWith()){
                                playerY--;
                                playerPoly.setY(playerY);
                        }
                }
        }
 
        public boolean entityCollisionWith() throws SlickException {
                for (int i = 0; i < BlockMap.entities.size(); i++) {
                        Block entity1 = (Block) BlockMap.entities.get(i);
                        if (playerPoly.intersects(entity1.poly)) {
                                return true;
                        }       
                }       
                return false;
        }
 
        public void render(GameContainer container, Graphics g)  {
                BlockMap.tmap.render(0, 0);
                g.drawAnimation(player, playerX, playerY);
                g.draw(playerPoly);
 
        }
 
        public static void main(String[] argv) throws SlickException {
                AppGameContainer container = 
                        new AppGameContainer(new Game(), 640, 480, false);
                container.start();
        }
}