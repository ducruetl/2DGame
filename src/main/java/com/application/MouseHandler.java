package com.application;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pathfinding.Pathfinding;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    public int mouseX, mouseY;
    public boolean mouseClicked = false, initialize = false, pathfind = false;


    @Override
    public void mouseClicked(MouseEvent e) {

        if (!pathfind) {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseClicked = true;
            initialize = true;
            System.out.println("X : " + mouseX + " Y : " + mouseY);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
