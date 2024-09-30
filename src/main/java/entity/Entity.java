package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Entity implements Comparable<Entity> {

    public int worldX, worldY, speed;
    public BufferedImage up_idle, up1, up2, down_idle, down1, down2, right_idle, right1, right2, left_idle, left1, left2;
    public String direction;
    public int frameNum = 1;
    public int frameCount = 0;

    abstract public void draw(Graphics2D graphics2D);

    @Override
    public int compareTo(Entity o) {
        if (this.worldY > o.worldY) {
            return 1;
        } else if (this.worldY < o.worldY) {
            return -1;
        }
        return 0;
    }
}
