package entity;

import com.application.GamePanel;
import com.application.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;
    public final int screenX, screenY;

    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
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

    public void update() {

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed) {
            frameCount ++;

            if (keyHandler.upPressed) {
                direction = "up";
                if (worldY - speed + (gamePanel.tileSize / 2) >= 0 && !gamePanel.tileManager.tiles.get(gamePanel.tileManager.mapTiles[(worldX + (gamePanel.tileSize / 2)) / gamePanel.tileSize][(worldY - speed + (gamePanel.tileSize / 2)) / gamePanel.tileSize]).collision) {
                    worldY -= speed;
                }
            }

            else if (keyHandler.downPressed) {
                direction = "down";
                worldY += speed;
            }

            else if (keyHandler.rightPressed) {
                direction = "right";
                worldX += speed;
            }

            else if (keyHandler.leftPressed) {
                direction = "left";
                worldX -= speed;
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
            };

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
            };
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
            };
        }

        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.dispose();

    }

}
