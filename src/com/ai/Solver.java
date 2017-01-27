package com.ai;


import java.util.*;


public class Solver {

    TreeSet<GameState> previouslyVisited;
    boolean[][] occupiedBlocks;
    Map<Integer, PuzzlePiece> puzzlePieceMap;
    List<GameState> correctOrder;

    public Solver() {
        occupiedBlocks = new boolean[10][10];
        previouslyVisited = new TreeSet<>();
        puzzlePieceMap = new HashMap<>();
        correctOrder = new LinkedList<>();

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

    public List<GameState> searchForSolution() {
        GameState root = new GameState();
        StateComparator stateComparator = new StateComparator();
        Queue<GameState> queue = new LinkedList<>();
        TreeSet<GameState> visitedGameStates = new TreeSet<>(stateComparator);
        List<GameState> visitedStates = new LinkedList<>();

        queue.add(root);
        visitedGameStates.add(root);
        GameState current = new GameState();
        while (!queue.isEmpty()) {
            current = queue.poll();
            visitedStates.add(current);
            GameState nextGameState = new GameState(current);
            generateOccupiedBlocks(current.state);
            //if(visitedStates.size() == 10000){
            //correctOrder = visitedStates;
            //  return correctOrder;
            //}


            if (current.state[0] == 4 && current.state[1] == -2) {
                buildListOfGameStates(current);
                return correctOrder;
            }
            for (int i = 0; i < 11; i++) {
                nextGameState.state = current.state.clone();
                nextGameState.state[2 * i]--;
                if (!visitedGameStates.contains(nextGameState) && checkIfValidState(nextGameState.state, i)) {
                    visitedGameStates.add(nextGameState);
                    queue.add(nextGameState);
                    nextGameState = new GameState(current);
                }
                nextGameState.state = current.state.clone();
                nextGameState.state[2 * i]++;
                if (!visitedGameStates.contains(nextGameState) && checkIfValidState(nextGameState.state, i)) {
                    visitedGameStates.add(nextGameState);
                    queue.add(nextGameState);
                    nextGameState = new GameState(current);
                }
                nextGameState.state = current.state.clone();
                nextGameState.state[2 * i + 1]--;
                if (!visitedGameStates.contains(nextGameState) && checkIfValidState(nextGameState.state, i)) {
                    visitedGameStates.add(nextGameState);
                    queue.add(nextGameState);
                    nextGameState = new GameState(current);
                }
                nextGameState.state = current.state.clone();
                nextGameState.state[2 * i + 1]++;
                if (!visitedGameStates.contains(nextGameState) && checkIfValidState(nextGameState.state, i)) {
                    visitedGameStates.add(nextGameState);
                    queue.add(nextGameState);
                    nextGameState = new GameState(current);
                }

            }

        }

        return null;
    }


    public boolean checkIfValidState(byte[] state, int ID) {
        PuzzlePiece changingPiece = puzzlePieceMap.get(ID);
        boolean[][] temp = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                temp[i][j] = occupiedBlocks[i][j];
            }
        }
        for (int i = 0; i < changingPiece.blocks; i++) {
            occupiedBlocks[changingPiece.currentX[i]][changingPiece.currentY[i]] = false;
        }

        for (int i = 0; i < changingPiece.blocks; i++) {
            if (occupiedBlocks[changingPiece.xStartings[i] + state[2 * ID]][changingPiece.yStartings[i] + state[2 * ID + 1]]) {
                occupiedBlocks = temp.clone();
                return false;
            }

        }
        occupiedBlocks = temp.clone();
        return true;
    }

    public void generateOccupiedBlocks(byte[] state) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                occupiedBlocks[i][j] = false;
            }
        }

        //draw black blocks
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

        //draw puzzle pieces for state
        for (int i = 0; i < 11; i++) {
            if (puzzlePieceMap.get(i).blocks == 3) {
                for (int j = 0; j < 3; j++) {
                    occupiedBlocks[puzzlePieceMap.get(i).xStartings[j] + state[2 * i]][puzzlePieceMap.get(i).yStartings[j] + state[2 * i + 1]] = true;
                    puzzlePieceMap.get(i).currentX[j] = puzzlePieceMap.get(i).xStartings[j] + state[2 * i];
                    puzzlePieceMap.get(i).currentY[j] = puzzlePieceMap.get(i).yStartings[j] + state[2 * i + 1];
                }

            } else {
                for (int j = 0; j < 4; j++) {
                    occupiedBlocks[puzzlePieceMap.get(i).xStartings[j] + state[2 * i]][puzzlePieceMap.get(i).yStartings[j] + state[2 * i + 1]] = true;
                    puzzlePieceMap.get(i).currentX[j] = puzzlePieceMap.get(i).xStartings[j] + state[2 * i];
                    puzzlePieceMap.get(i).currentY[j] = puzzlePieceMap.get(i).yStartings[j] + state[2 * i + 1];
                }
            }
        }
    }

    public void buildListOfGameStates(GameState state) {

        if (state.prev != null) {
            buildListOfGameStates(state.prev);
        }
        correctOrder.add(state);

    }
}

