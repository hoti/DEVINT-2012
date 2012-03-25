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
    private float playerX = 340;
    private float playerY = 240;
    private Animation player;
    private Polygon playerPoly;
    public BlockMap map;
    public Polygon plateforme;

    private boolean SAUT;
    private boolean GAUCHE;
    private boolean DROIT;
    private static final int NBSTEPSAUT = 25;
    private static final int NBSTEPCLIGNOTEMENT = 150;
    private static boolean CLIGNOTEMENT;
    private static final int NBSTEPALLER = 150;
    private boolean ALLER;
    private int step1;
    private int step;

    public Game() {
        super("one class barebone game");
    }

    public void init(GameContainer container) throws SlickException {
        //container.setFullscreen(true);
        container.setVSync(true);
        container.setMinimumLogicUpdateInterval(12);
        container.setMaximumLogicUpdateInterval(12);
        
        SpriteSheet sheet = new SpriteSheet("ressources/images/arret.png", 32,
                32);
        map = new BlockMap("ressources/images/map1.tmx");
        player = new Animation();
        // player.setAutoUpdate(true);
        for (int frame = 0; frame < 1; frame++) {
            player.addFrame(sheet.getSprite(frame, 0), 150);
        }
        playerPoly = new Polygon(new float[] { playerX, playerY, playerX + 32,
                playerY, playerX + 32, playerY + 32, playerX, playerY + 32 });

        plateforme = new Polygon(new float[] { 6 * 15, 3 * 15, 10 * 15, 3 * 15,
                10 * 15, 4 * 15, 6 * 15, 4 * 15 });

        // BlockMap.entities.add(plateforme);
        SAUT = false;
        GAUCHE = false;
        DROIT = false;
        ALLER = true;
        CLIGNOTEMENT = false;
        step1 = 0;
        step = 0;
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        step++;
        if (step % NBSTEPCLIGNOTEMENT == 0) {
            if (CLIGNOTEMENT) {
                map = new BlockMap("ressources/images/map2.tmx");
                CLIGNOTEMENT = false;
            } else {
                map = new BlockMap("ressources/images/map1.tmx");
                CLIGNOTEMENT = true;
            }
        }

        if (step % NBSTEPALLER == 0) {
            if (ALLER) {
                ALLER = false;
            } else {
                ALLER = true;
            }
        }
        if (ALLER) {
            plateforme.setX(plateforme.getX() + 1);
        } else {
            plateforme.setX(plateforme.getX() - 1);
        }

        if (container.getInput().isKeyDown(Input.KEY_LEFT)) {

            if (!GAUCHE) {
                SpriteSheet sheet = new SpriteSheet(
                        "ressources/images/gif3.png", 32, 32);
                player = new Animation();
                for (int frame = 0; frame < 6; frame++) {
                    player.addFrame(sheet.getSprite(frame, 0), 150);
                }
                GAUCHE = true;
            }
            if (SAUT) {
                playerX--;
                playerPoly.setX(playerX);
            }
            playerX--;
            playerPoly.setX(playerX);
            if (entityCollisionWith()) {
                playerX++;
                playerPoly.setX(playerX);
                if (SAUT) {
                    playerX++;
                    playerPoly.setX(playerX);
                }
            }
        } else if (GAUCHE && !DROIT) {
            SpriteSheet sheet = new SpriteSheet("ressources/images/arret.png",
                    32, 32);
            player = new Animation();
            for (int frame = 0; frame < 1; frame++) {
                player.addFrame(sheet.getSprite(frame, 0), 150);
            }
            GAUCHE = false;
        }

        if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
            if (!DROIT) {
                SpriteSheet sheet = new SpriteSheet(
                        "ressources/images/gif4.png", 32, 32);
                player = new Animation();
                for (int frame = 0; frame < 6; frame++) {
                    player.addFrame(sheet.getSprite(frame, 0), 150);
                }
                DROIT = true;
            }

            if (SAUT) {
                playerX++;
                playerPoly.setX(playerX);
            }
            playerX++;
            playerPoly.setX(playerX);
            if (entityCollisionWith()) {
                playerX--;
                playerPoly.setX(playerX);
                if (SAUT) {
                    playerX--;
                    playerPoly.setX(playerX);
                }
            }
        } else if (DROIT && !GAUCHE) {
            SpriteSheet sheet = new SpriteSheet("ressources/images/arret.png",
                    32, 32);
            player = new Animation();
            for (int frame = 0; frame < 1; frame++) {
                player.addFrame(sheet.getSprite(frame, 0), 150);
            }
            DROIT = false;
        }

        if ((DROIT && GAUCHE) || (!DROIT && !GAUCHE)) {
            SpriteSheet sheet = new SpriteSheet("ressources/images/arret.png",
                    32, 32);
            player = new Animation();
            for (int frame = 0; frame < 1; frame++) {
                player.addFrame(sheet.getSprite(frame, 0), 150);
            }
            DROIT = false;
            GAUCHE = false;
        }

        if (!SAUT) {
            // if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
            playerY += 2;
            playerPoly.setY(playerY);
            if (entityCollisionWith()) {
                playerY -= 2;
                ;
                playerPoly.setY(playerY);
            }
        }

        playerY += 2;
        playerPoly.setY(playerY);
        if (entityCollisionWith() || SAUT) {
            playerY -= 2;
            ;
            playerPoly.setY(playerY);
            if (container.getInput().isKeyDown(Input.KEY_UP) || SAUT) {

                playerY -= 2;
                playerPoly.setY(playerY);
                if (entityCollisionWith()) {
                    playerY += 2;
                    playerPoly.setY(playerY);
                    step1 = 0;
                    SAUT = false;
                } else {
                    if (step1 < NBSTEPSAUT) {
                        SAUT = true;
                        step1++;
                    } else {
                        SAUT = false;
                        step1 = 0;
                    }
                }
            }
        } else {
            playerY -= 2;
            ;
            playerPoly.setY(playerY);
        }
    }

    public boolean entityCollisionWith() throws SlickException {
        for (int i = 0; i < BlockMap.entities.size(); i++) {
            Block entity1 = (Block) BlockMap.entities.get(i);
            if (playerPoly.intersects(entity1.poly) || playerPoly.getX() < 0
                    || playerPoly.getMaxX() > BlockMap.mapWidth
                    || playerPoly.getY() < 0
                    || playerPoly.getMaxY() > BlockMap.mapHeight
                    || playerPoly.intersects(plateforme)) {
                return true;
            }
        }
        return false;
    }

    public void render(GameContainer container, Graphics g) {
        BlockMap.tmap.render(0, 0);
        g.drawAnimation(player, playerX, playerY);
        g.draw(playerPoly);
        g.draw(plateforme);

    }

    public static void main(String[] argv) throws SlickException {
        AppGameContainer container = new AppGameContainer(new Game(), 640, 480,
                false);
        container.start();
    }
}