package com.ai;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class Solver {

    TreeSet<GameState> previouslyVisited;
    boolean[][] occupiedBlocks;
    Map<Integer, PuzzlePiece> puzzlePieceMap;


    public Solver() {
        occupiedBlocks = new boolean[10][10];
        previouslyVisited = new TreeSet<>();
        puzzlePieceMap = new HashMap<>();

        //initialize all members of occupiedBlocks to false
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                occupiedBlocks[i][j] = false;
            }
        }

        //Initialize black blocks
        for (int i = 0; i < 10; i++) {
            occupiedBlocks[i][0] = true;
            occupiedBlocks[i][9] = true;
        }
        for (int i = 1; i < 9; i++) {
            occupiedBlocks[0][i] = true;
            occupiedBlocks[9][i] = true;
        }

        occupiedBlocks[1][1] = true;
        occupiedBlocks[1][2] = true;
        occupiedBlocks[2][1] = true;
        occupiedBlocks[7][1] = true;
        occupiedBlocks[8][1] = true;
        occupiedBlocks[8][2] = true;
        occupiedBlocks[1][7] = true;
        occupiedBlocks[1][8] = true;
        occupiedBlocks[2][8] = true;
        occupiedBlocks[8][7] = true;
        occupiedBlocks[7][8] = true;
        occupiedBlocks[8][8] = true;
        occupiedBlocks[3][4] = true;
        occupiedBlocks[4][4] = true;
        occupiedBlocks[4][3] = true;

        //Initialize Puzzle Pieces
        //piece1
        puzzlePieceMap.put(0, new PuzzlePiece(0, 255, 0, 0, 1, 3, 2, 3, 1, 4, 2, 4));
        puzzlePieceMap.put(1, new PuzzlePiece(1, 0, 255, 0, 1, 5, 1, 6, 2, 6));
        puzzlePieceMap.put(2, new PuzzlePiece(2, 128, 128, 255, 2, 5, 3, 5, 3, 6));
        puzzlePieceMap.put(3, new PuzzlePiece(3, 255, 128, 128, 3, 7, 3, 8, 4, 8));
        puzzlePieceMap.put(4, new PuzzlePiece(4, 255, 255, 128, 4, 7, 5, 7, 5, 8));
        puzzlePieceMap.put(5, new PuzzlePiece(5, 128, 128, 0, 6, 7, 7, 7, 6, 8));
        puzzlePieceMap.put(6, new PuzzlePiece(6, 0, 128, 128, 5, 4, 5, 5, 5, 6, 4, 5));
        puzzlePieceMap.put(7, new PuzzlePiece(7, 0, 128, 0, 6, 4, 6, 5, 6, 6, 7, 5));
        puzzlePieceMap.put(8, new PuzzlePiece(8, 0, 255, 255, 8, 5, 8, 6, 7, 6));
        puzzlePieceMap.put(9, new PuzzlePiece(9, 0, 0, 255, 6, 2, 6, 3, 5, 3));
        puzzlePieceMap.put(10, new PuzzlePiece(10, 255, 128, 0, 5, 1, 6, 1, 5, 2));


        //Put PuzzlePiece locations into occupied Space locations
        for (int i = 0; i < 4; i++) {
            occupiedBlocks[puzzlePieceMap.get(0).xStartings[i]][puzzlePieceMap.get(0).yStartings[i]] = true;
            occupiedBlocks[puzzlePieceMap.get(6).xStartings[i]][puzzlePieceMap.get(6).yStartings[i]] = true;
            occupiedBlocks[puzzlePieceMap.get(7).xStartings[i]][puzzlePieceMap.get(7).yStartings[i]] = true;
        }
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                occupiedBlocks[puzzlePieceMap.get(i).xStartings[j]][puzzlePieceMap.get(i).yStartings[j]] = true;
            }
        }
        for (int i = 8; i < 11; i++) {
            for (int j = 0; j < 3; j++) {
                occupiedBlocks[puzzlePieceMap.get(i).xStartings[j]][puzzlePieceMap.get(i).yStartings[j]] = true;
            }
        }

    }


    public boolean checkIfValidState(GameState state) {
        boolean occupiedStatesFuture[][] = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                occupiedStatesFuture[i][j] = false;
            }
        }

        //draw black blocks
        for (int i = 0; i < 10; i++) {
            occupiedStatesFuture[i][0] = true;
            occupiedStatesFuture[i][9] = true;
        }
        for (int i = 1; i < 9; i++) {
            occupiedStatesFuture[0][i] = true;
            occupiedStatesFuture[9][i] = true;
        }

        occupiedStatesFuture[1][1] = true;
        occupiedStatesFuture[1][2] = true;
        occupiedStatesFuture[2][1] = true;
        occupiedStatesFuture[7][1] = true;
        occupiedStatesFuture[8][1] = true;
        occupiedStatesFuture[8][2] = true;
        occupiedStatesFuture[1][7] = true;
        occupiedStatesFuture[1][8] = true;
        occupiedStatesFuture[2][8] = true;
        occupiedStatesFuture[8][7] = true;
        occupiedStatesFuture[7][8] = true;
        occupiedStatesFuture[8][8] = true;
        occupiedStatesFuture[3][4] = true;
        occupiedStatesFuture[4][4] = true;
        occupiedStatesFuture[4][3] = true;

        //draw puzzle pieces for state
        for (int i = 0; i < 11; i++) {
            if (puzzlePieceMap.get(i).blocks == 3) {
                for (int j = 0; j < 3; j++) {
                    if (occupiedStatesFuture[puzzlePieceMap.get(i).xStartings[j] + state.state[2 * i]][puzzlePieceMap.get(i).yStartings[j] + state.state[2 * i + 1]])
                        return false;
                    else
                        occupiedStatesFuture[puzzlePieceMap.get(i).xStartings[j] + state.state[2 * i]][puzzlePieceMap.get(i).yStartings[j] + state.state[2 * i + 1]] = true;

                }
            } else {
                for (int j = 0; j < 4; j++) {
                    if (occupiedStatesFuture[puzzlePieceMap.get(i).xStartings[j] + state.state[2 * i]][puzzlePieceMap.get(i).yStartings[j] + state.state[2 * i + 1]])
                        return false;
                    else
                        occupiedStatesFuture[puzzlePieceMap.get(i).xStartings[j] + state.state[2 * i]][puzzlePieceMap.get(i).yStartings[j] + state.state[2 * i + 1]] = true;

                }
            }
        }

        return true;
    }
}

