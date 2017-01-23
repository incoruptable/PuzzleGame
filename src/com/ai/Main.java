package com.ai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

class View extends JPanel implements MouseListener {
    Main viz;
    Random rand;
    GameState currentState;
    Graphics graphics;
    int size;
    Solver solver;

    View(Main v) throws IOException {
        viz = v;
        solver = new Solver();
        rand = new Random(0);
        currentState = new GameState();
        size = 48;
    }

    public void mousePressed(MouseEvent e) {

        GameState newState = new GameState(currentState);
        newState.state = currentState.state;
        newState.state[20]--;
        if (!solver.checkIfValidState(newState)) {
            System.out.print("Not a valid state. Returning now.");
            return;
        }


        //currentState.state[rand.nextInt(22)] += (rand.nextInt(2) == 0 ? -1 : 1);


        for (int i = 0; i < 11; i++)
            System.out.print("(" + currentState.state[2 * i] + "," +
                    currentState.state[2 * i + 1] + ") ");
        System.out.println();
        viz.repaint();

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    // Draw a block
    public void b(int x, int y) {
        graphics.fillRect(size * x, size * y, size, size);
    }

    // Draw a 3-block piece
    public void shape(int id, int red, int green, int blue,
                      int x1, int y1, int x2, int y2, int x3, int y3) {
        graphics.setColor(new Color(red, green, blue));
        b(currentState.state[2 * id] + x1, currentState.state[2 * id + 1] + y1);
        b(currentState.state[2 * id] + x2, currentState.state[2 * id + 1] + y2);
        b(currentState.state[2 * id] + x3, currentState.state[2 * id + 1] + y3);
    }

    // Draw a 4-block piece
    public void shape(int id, int red, int green, int blue,
                      int x1, int y1, int x2, int y2,
                      int x3, int y3, int x4, int y4) {
        shape(id, red, green, blue, x1, y1, x2, y2, x3, y3);
        b(currentState.state[2 * id] + x4, currentState.state[2 * id + 1] + y4);
    }

    public void paintComponent(Graphics g) {
        // Draw the black squares
        graphics = g;
        g.setColor(new Color(0, 0, 0));
        for (int i = 0; i < 10; i++) {
            b(i, 0);
            b(i, 9);
        }
        for (int i = 1; i < 9; i++) {
            b(0, i);
            b(9, i);
        }
        b(1, 1);
        b(1, 2);
        b(2, 1);
        b(7, 1);
        b(8, 1);
        b(8, 2);
        b(1, 7);
        b(1, 8);
        b(2, 8);
        b(8, 7);
        b(7, 8);
        b(8, 8);
        b(3, 4);
        b(4, 4);
        b(4, 3);

        // Draw the pieces
        shape(0, 255, 0, 0, 1, 3, 2, 3, 1, 4, 2, 4);
        shape(1, 0, 255, 0, 1, 5, 1, 6, 2, 6);
        shape(2, 128, 128, 255, 2, 5, 3, 5, 3, 6);
        shape(3, 255, 128, 128, 3, 7, 3, 8, 4, 8);
        shape(4, 255, 255, 128, 4, 7, 5, 7, 5, 8);
        shape(5, 128, 128, 0, 6, 7, 7, 7, 6, 8);
        shape(6, 0, 128, 128, 5, 4, 5, 5, 5, 6, 4, 5);
        shape(7, 0, 128, 0, 6, 4, 6, 5, 6, 6, 7, 5);
        shape(8, 0, 255, 255, 8, 5, 8, 6, 7, 6);
        shape(9, 0, 0, 255, 6, 2, 6, 3, 5, 3);
        shape(10, 255, 128, 0, 5, 1, 6, 1, 5, 2);
    }
}

public class Main extends JFrame {
    public Main() throws Exception {
        View view = new View(this);
        view.addMouseListener(view);
        this.setTitle("Puzzle");
        this.setSize(482, 505);
        this.getContentPane().add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}