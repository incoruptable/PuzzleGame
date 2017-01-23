package com.ai;

/**
 * Created by Incoruptable on 1/22/2017.
 */
public class PuzzlePiece {

    public int Id;
    public int red;
    public int green;
    public int blue;
    public int[] xStartings;
    public int[] yStartings;
    public int blocks;

    public PuzzlePiece(int Id, int red, int green, int blue, int x1Starting, int y1Starting, int x2Starting, int y2Starting, int x3Starting, int y3Starting, int x4Starting, int y4Starting) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.Id = Id;
        xStartings = new int[4];
        yStartings = new int[4];
        blocks = 4;
        xStartings[0] = x1Starting;
        xStartings[1] = x2Starting;
        xStartings[2] = x3Starting;
        xStartings[3] = x4Starting;
        yStartings[0] = y1Starting;
        yStartings[1] = y2Starting;
        yStartings[2] = y3Starting;
        yStartings[3] = y4Starting;
    }

    public PuzzlePiece(int Id, int red, int green, int blue, int x1Starting, int y1Starting, int x2Starting, int y2Starting, int x3Starting, int y3Starting) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.Id = Id;
        blocks = 3;
        xStartings = new int[3];
        yStartings = new int[3];
        xStartings[0] = x1Starting;
        xStartings[1] = x2Starting;
        xStartings[2] = x3Starting;
        yStartings[0] = y1Starting;
        yStartings[1] = y2Starting;
        yStartings[2] = y3Starting;

    }

}
