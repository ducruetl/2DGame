package entity;

import com.application.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC extends Entity {

    GamePanel gamePanel;

    public NPC(GamePanel gamePanel, int worldX, int worldY, String imagePath) {

        this.gamePanel = gamePanel;
        this.worldX = worldX;
        this.worldY = worldY;
        getNPCImage(imagePath);
        direction = "down";

    }

    private void getNPCImage(String imagePath) {
        try {
            up_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "up_idle.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "up_walk1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "up_walk2.png")));
            down_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "down_idle.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "down_walk1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "down_walk2.png")));
            right_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "right_idle.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "right_walk1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "right_walk2.png")));
            left_idle = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "left_idle.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "left_walk1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + "left_walk2.png")));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D graphics2D) {

        if (worldX <= (gamePanel.player.worldX + gamePanel.screenWidth/2) && worldX >= (gamePanel.player.worldX - gamePanel.screenWidth/2) && worldY <= (gamePanel.player.worldY+ gamePanel.screenHeight/2) && worldY >= (gamePanel.player.worldY - gamePanel.screenHeight/2)) {

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

            graphics2D.drawImage(image, worldX - gamePanel.player.worldX + gamePanel.player.screenX, worldY - gamePanel.player.worldY + gamePanel.player.screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }

    }

}
