package entity;

import com.application.GamePanel;
import com.application.KeyHandler;
import com.application.MouseHandler;
import pathfinding.Pathfinding;
import pathfinding.TileNode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gamePanel;
    MouseHandler mouseHandler;
    KeyHandler keyHandler;
    public final int screenX, screenY;
    int targetX, targetY;
    int i;
    boolean pathfind = false;
    ArrayList<TileNode> path;

    public Player (GamePanel gamePanel, MouseHandler mouseHandler, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;
        getPlayerImage();
        screenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        screenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);
        worldX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        worldY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);
        speed = 4;
        direction = "up";
    }

    public void getPlayerImage() {
        try {
            up_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_up_idle.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_up_walk1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_up_walk2.png")));
            down_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_down_idle.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_down_walk1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_down_walk2.png")));
            right_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_right_idle.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_right_walk1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_right_walk2.png")));
            left_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_left_idle.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_left_walk1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/player_left_walk2.png")));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void update() throws FileNotFoundException {

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed || mouseHandler.mouseClicked) {
            frameCount ++;
            if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed) {
                mouseHandler.mouseClicked = false;
            }
            if (mouseHandler.initialize) {
                targetX = (worldX - screenX + mouseHandler.mouseX) / gamePanel.tileSize;
                targetY = (worldY - screenY + mouseHandler.mouseY) / gamePanel.tileSize;
                i = 0;
                TileNode goal = new TileNode(targetX, targetY, targetX/gamePanel.tileSize - worldX/gamePanel.tileSize, targetY/gamePanel.tileSize - worldY/gamePanel.tileSize, null);
                path = (ArrayList<TileNode>) Pathfinding.pathfinding(new TileNode((int) (double) (worldX/gamePanel.tileSize), (int) (double) (worldY / gamePanel.tileSize), 0, Pathfinding.h(worldX/gamePanel.tileSize, worldY/gamePanel.tileSize, goal), null), goal);
                mouseHandler.initialize = false;
                pathfind = true;
            }

            if (pathfind) {
                targetX = path.get(i).getCol() * gamePanel.tileSize;
                targetY = path.get(i).getRow() * gamePanel.tileSize;
                if (mouseHandler.mouseClicked && worldX - targetX < speed  && targetX - worldX < speed && worldY - targetY < speed && targetY - worldY < speed) {
                    if (!(i + 1 < path.size())) {
                        i++;
                    } else {
                        pathfind = false;
                        mouseHandler.mouseClicked = false;
                    }
                }
            }

            if (keyHandler.upPressed || mouseHandler.mouseClicked && worldY - targetY >= speed) {
                direction = "up";
                if (worldY - speed + (gamePanel.tileSize / 2) >= 0 && !gamePanel.tileManager.tiles.get(gamePanel.tileManager.mapTiles[(worldX + 4 * gamePanel.scale) / gamePanel.tileSize][(worldY - speed + (gamePanel.tileSize / 2)) / gamePanel.tileSize]).collision && !gamePanel.tileManager.tiles.get(gamePanel.tileManager.mapTiles[(worldX + gamePanel.tileSize - 4 * gamePanel.scale) / gamePanel.tileSize][(worldY - speed + (gamePanel.tileSize / 2)) / gamePanel.tileSize]).collision) {
                    worldY -= speed;
                }
            }

            else if (keyHandler.downPressed || mouseHandler.mouseClicked && targetY - worldY >= speed) {
                direction = "down";
                if (worldY + speed + gamePanel.tileSize - gamePanel.scale < gamePanel.worldHeight && !gamePanel.tileManager.tiles.get(gamePanel.tileManager.mapTiles[(worldX + 4 * gamePanel.scale) / gamePanel.tileSize][(worldY + speed + gamePanel.tileSize - gamePanel.scale) / gamePanel.tileSize]).collision && !gamePanel.tileManager.tiles.get(gamePanel.tileManager.mapTiles[(worldX + gamePanel.tileSize - 4 * gamePanel.scale) / gamePanel.tileSize][(worldY + speed + gamePanel.tileSize - gamePanel.scale) / gamePanel.tileSize]).collision) {
                    worldY += speed;
                }
            }

            else if (keyHandler.rightPressed || mouseHandler.mouseClicked && targetX - worldX >= speed) {
                direction = "right";
                if (worldX + speed + gamePanel.tileSize - 4 * gamePanel.scale < gamePanel.worldWidth && !gamePanel.tileManager.tiles.get(gamePanel.tileManager.mapTiles[(worldX + speed + gamePanel.tileSize - 4 * gamePanel.scale) / gamePanel.tileSize][(worldY + gamePanel.tileSize - 4 * gamePanel.scale) / gamePanel.tileSize]).collision) {
                    worldX += speed;
                }
            }

            else if (keyHandler.leftPressed || worldX - targetX >= speed) {
                direction = "left";
                if (worldX - speed + 4 * gamePanel.scale >= 0 && !gamePanel.tileManager.tiles.get(gamePanel.tileManager.mapTiles[(worldX - speed + 4 * gamePanel.scale) / gamePanel.tileSize][(worldY + gamePanel.tileSize - 4 * gamePanel.scale) / gamePanel.tileSize]).collision) {
                    worldX -= speed;
                }
            }

            if (frameCount >= 10) {
                if (frameNum == 1) {
                    frameNum = 2;
                } else if (frameNum == 2) {
                    frameNum = 3;
                } else if (frameNum == 3) {
                    frameNum = 4;
                } else if (frameNum == 4) {
                    frameNum = 1;
                }
                frameCount = 0;
            }
        } else {
            frameNum = 1;
            frameCount = 0;
        }

    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;


        if (frameNum == 2) {
            switch (direction) {
                case "up":
                    image = up1;
                    break;
                case "down":
                    image = down1;
                    break;
                case "right":
                    image = right1;
                    break;
                case "left":
                    image = left1;
                    break;
                default:
                    break;
            }

        } else if (frameNum == 4) {
            switch (direction) {
                case "up":
                    image = up2;
                    break;
                case "down":
                    image = down2;
                    break;
                case "right":
                    image = right2;
                    break;
                case "left":
                    image = left2;
                    break;
                default:
                    break;
            }
        } else {
            switch (direction) {
                case "up":
                    image = up_idle;
                    break;
                case "down":
                    image = down_idle;
                    break;
                case "right":
                    image = right_idle;
                    break;
                case "left":
                    image = left_idle;
                    break;
                default:
                    break;
            }
        }

        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.dispose();

    }

}
