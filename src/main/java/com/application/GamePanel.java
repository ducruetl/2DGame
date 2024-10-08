package com.application;

import entity.NPC;
import entity.Player;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // Original tile size
    public final int scale = 4; // Screen scale
    final int fps = 60;

    public final int tileSize = originalTileSize * scale; // Current tile size
    public final int maxScreenCol = 16; // Number of tiles displayed per column
    public final int maxScreenRow = 12; // Number of tiles displayed per column
    public final int screenWidth = maxScreenCol * tileSize; // Screen width in pixel
    public final int screenHeight = maxScreenRow * tileSize; // Screen height in pixel

    // World Settings
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 30;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    public TileManager tileManager = new TileManager(this);
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();
    public Player player = new Player(this, mouseHandler, keyHandler);
    NPC npc1 = new NPC(this, 2 * tileSize, 2 * tileSize, "/npc/");

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseHandler);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Cette fonction représente la boucle du jeu, elle va actualiser les informations telles que la posution du
    // personnage et elle va déssiner tout ce qui doit être affiché.
    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int fpsCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta > 1) {
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                repaint();
                delta--;
                fpsCount++;
            }

            if (timer > 1000000000) {
                System.out.println("FPS : " + fpsCount);
                fpsCount = 0;
                timer = 0;
            }
        }

    }

    public void update() throws IOException {
        player.update();
        npc1.update();
    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D)graphics;

        tileManager.draw(graphics2D);
        npc1.draw(graphics2D);
        player.draw(graphics2D);

        Toolkit.getDefaultToolkit().sync();
    }
}
