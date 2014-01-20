/*
 * 8 neighbors
 * live- < 2 dies
 * live- 2-3 live
 * live- > 3 dies
 * dead- 3 live
 */
package gameoflife;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustave Michel
 */
public class GameofLife {
    public static int boardSize = 35;
    public static int startingColonies = (int) Math.pow(boardSize, 2)/2;
    public static int numGen = 350;
    public static Random random = new Random();
    public static boolean board[][] = new boolean[boardSize][boardSize];
    
    public static final String clearCommand = "\033[" + "2J";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        board = startBoard();
        int gen = 0;
//        board[16][18] = true;
//        board[16][17] = true;
//        board[17][17] = true;
//        board[17][16] = true;
//        board[18][17] = true;
        while(true) {
            System.out.println();
            System.out.print(clearCommand);
            if(gen > numGen) {
                board = startBoard();
                gen = 0;
            }
            renderBoard(board);
            board = nextGen(board);
            System.out.print(gen+" of "+numGen);
            //System.out.print("Generation: "+gen);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameofLife.class.getName()).log(Level.SEVERE, null, ex);
            }
            gen++;
        }
    }
    
    public static boolean[][] startBoard() {
        boolean out[][] = clearBoard();
        if(startingColonies > Math.pow(boardSize, 2)) {
            startingColonies = (int) Math.pow(boardSize, 2);
        }
        for(int i = 0; i < startingColonies; i++) {
            int x = random.nextInt(boardSize);
            int y = random.nextInt(boardSize);
            if(!out[x][y]) { //checks if randomly selected colony is already live
                out[x][y] = !out[x][y];
            } else {
                i--; //restarts selection of this colony
            }
        }
        return out;
    }
    
    public static boolean[][] clearBoard() {
        boolean clean[][] = new boolean[boardSize][boardSize];
        for(int i = 0; i < clean.length; i++) {
            for(int j = 0; j < clean[i].length; j++) {
                clean[i][j] = false;
            }
        }
        return clean;
    }
    
    public static boolean[][] nextGen(boolean[][] board) {
        boolean out[][] = board; //makes it the right size
        out = clearBoard(); //clears board
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j <board[i].length; j++) {
                int neighbors = numNeighbors(board, i, j);
                switch(neighbors) {
                    case 0:
                    case 1:
                        out[i][j] = false;
                        break;
                    case 2:
                        out[i][j] = board[i][j];
                        break;
                    case 3:
                        out[i][j] = true;
                        break;
                    default:
                        out[i][j] = false;
                }
            }
        }
        return out;
    }
    
    public static int numNeighbors(boolean[][] board, int row, int column) {
        int out = 0;
        if((row > 0 && column > 0) && (board[row-1][column-1])) //Up, Left
            out++;
        if((row > 0) && (board[row-1][column])) //Up, Center
            out++;
        if((row > 0 && column < board[row].length-1) && (board[row-1][column+1])) //Up, Right
            out++;
        if((column > 0) && (board[row][column-1])) //Center, Left
            out++;
        if((column < board[row].length-1) && (board[row][column+1])) //Center, Right
            out++;
        if((row < board.length-1 && column > 0) && (board[row+1][column-1])) //Down, Left
            out++;
        if((row < board.length-1) && (board[row+1][column])) //Down, Center
            out++;
        if((row < board.length-1 && column < board[row].length-1) && (board[row+1][column+1])) //Down, Right
            out++;
        return out;
    }
    
    public static void renderBoard(boolean[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j]) {
                    System.out.print(" #");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
    }
}
