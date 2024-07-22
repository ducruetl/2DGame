package tile;

import com.application.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    public ArrayList<Tile> tiles;
    public int[][] mapTiles;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new ArrayList<>();
        mapTiles = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTilesImages();
        loadMap();
    }

    public void loadMap() {

        try {

            InputStream map = getClass().getResourceAsStream("/maps/map.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(map));

            int worldCol = 0;
            int worldRow = 0;

            while (worldRow < gamePanel.maxWorldRow) {

                String line = bufferedReader.readLine();
                if (line != null) {
                    String[] numbers = line.split(" ");

                    while (worldCol < gamePanel.maxWorldCol) {
                        mapTiles[worldCol][worldRow] = Integer.parseInt(numbers[worldCol]);
                        worldCol++;
                    }
                    worldCol = 0;
                }
                worldRow++;
            }

            bufferedReader.close();

        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    public void getTilesImages() {
        try {

            tiles.add(new Tile());
            tiles.getFirst().image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/grass.png")));
            tiles.add(new Tile());
            tiles.get(1).image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/wall_brick.png")));
            tiles.get(1).collision = true;
            tiles.add(new Tile());
            tiles.get(2).image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/water.png")));
            tiles.get(2).collision = true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D graphics2D) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldRow < gamePanel.maxWorldRow) {

            while (worldCol < gamePanel.maxWorldCol) {

                int worldX = worldCol * gamePanel.tileSize;
                int worldY = worldRow * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                if (worldX > gamePanel.player.worldX - gamePanel.player.screenX - gamePanel.tileSize &&
                    worldX < gamePanel.player.worldX + gamePanel.player.screenX + gamePanel.tileSize &&
                    worldY > gamePanel.player.worldY - gamePanel.player.screenY - gamePanel.tileSize &&
                    worldY < gamePanel.player.worldY + gamePanel.player.screenY + gamePanel.tileSize) {
                        graphics2D.drawImage(tiles.get(mapTiles[worldCol][worldRow]).image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
                worldCol++;
            }
            worldCol = 0;
            worldRow++;

        }

    }

}
