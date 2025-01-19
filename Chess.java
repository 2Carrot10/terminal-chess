import java.util.*;
import java.io.*;


// This class represents a chess game. It inherits from AbstractStrategyGame. Like all classes
// that inherit from AbstractStrategyGame, this class can return the game instructions, get a
// string of the current game state, get the current player, get the next player, and make moves.
public class Chess extends AbstractStrategyGame {

    public static final String NO_STYLES = "\u001B[0m";
    public static final String RED_HIGHLIGHT = "\u001B[31m";
    public static final String BLUE_HIGHLIGHT = "\u001B[34m";

    private boolean whitePlays;
    private Board board;
    private String instructionsFromFile;

    // Behavior: 
    //   - This constructor creates a Chess object and sets up all neccecary fields.
    // Parameters:
    //   - none
    // Returns:
    //   - none
    // Exceptions:
    //   - FileNotFoundException: If instructions.txt is deleted
    public Chess() throws FileNotFoundException{
        whitePlays = true;
        board = new Board();
        instructionsFromFile = "";

        File f = new File("instructions.txt");
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            instructionsFromFile += nextLine + "\n";
        }
        sc.close();
    }

    // Behavior: 
    //   - This method returns the instructions of the game.
    // Parameters:
    //   - none
    // Returns:
    //   - String: the instructions
    // Exceptions:
    //   - none
    public String instructions() {
        String instructions = "";
        instructions += instructionsFromFile;
        char[][][] boardRepresentation = new char[][][]{
            {
            
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', 'p', 'p', 'p', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'R', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', 'P', 'P', 'P', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            },

            {
            
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', 'p', 'p', 'p', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'B', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'P', 'P', 'P', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            },
            {
            
                {' ', ' ', ' ', ' ', 'r', ' ', ' ', ' '},
                {' ', 'p', 'p', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'Q', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'P', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            },
            {
            
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', 'r', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'K', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', 'P', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            },
            {
            
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'p', 'p', 'p', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'N', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'P', 'P', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            },
            {
            
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'p', ' ', ' ', ' '},
                {' ', ' ', ' ', 'p', 'p', 'P', ' ', ' '},
                {' ', ' ', ' ', ' ', 'P', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            },
            {
            
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'r', ' ', ' ', ' ', 'K', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            },
            {
            
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'Q', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'K', ' ', ' ', ' ', ' ', ' ', ' ', 'r'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
            }
        };
        for(char[][] c : boardRepresentation){
            Board exampleBoard = new Board(c);
            Position positionStart = new Position(4, 4);
            instructions += exampleBoard.toStringhighlightedBoard(positionStart);
        }
        instructions += 
            "For further information, go to https://www.chess.com/learn-how-to-play-chess.\n";

        return instructions;
    }

    // Behavior: 
    //   - This method returns the ID of the next (or current) player. Returns 0 if the game is
    //     over.
    // Parameters:
    //   - none
    // Returns:
    //   - int: the id of the current player or 0 if the game is over
    // Exceptions:
    //   - none
    public int getNextPlayer(){
        if (getWinner() != -1) return 0;
        return whitePlays? 1 : 2;
    }

    // Behavior: 
    //   - This method returns an ascii board to show important information about the Chess object.
    // Parameters:
    //   - none
    // Returns:
    //   - String: the string containing a ascii board representing the current state.
    // Exceptions:
    //   - none
    public String toString(){
        return board.toString();
    }

    // Behavior: 
    //   - This method returns the id of the player who has won or -1 if no player has won yet.
    // Parameters:
    //   - none
    // Returns:
    //   - int: the id of the winner or -1 if no player has won
    // Exceptions:
    //   - none
    public int getWinner(){
        return board.getWinner();
    }

    // Behavior: 
    //   - This prosseses a move based on an input
    // Parameters:
    //   - input: the input of the player
    // Returns:
    //   - none
    // Exceptions:
    //   - IllegalArgumentException: player does not follow instructions or does not format
    //     the move correctly.
    public void makeMove(Scanner input) {
        System.out.println("What is the square of the piece you want to move? (ex. F4). " + 
            "Alternatively, select the square of the piece you want to move and the location" + 
            " you want to move it to. (ex. F4 F5):");
        String inputString = input.nextLine();
        Boolean useTwoInputs = inputString.indexOf(" ") == -1;

        Position positionStart;
        Position positionEnd;

        if(useTwoInputs) {
            positionStart = new Position(inputString);
        }
        else {
            positionStart = new Position(inputString.split(" ")[0]);
        }
        Piece piece = board.pieceAtPosition(positionStart);
        if(piece == null) {
            throw new IllegalArgumentException(
                "Piece does not exist at the desired location!"
            );
        }
        if (piece.getIsWhite() != whitePlays){
            throw new IllegalArgumentException(
                "You can not move the other player's pieces!"
            );
        }
        Bitboard possibleMoves = board.getMoveBitboard(positionStart);
        if (useTwoInputs) {
            System.out.println(board.toStringhighlightedBoard(positionStart));
            System.out.println(
                "Your piece (higlighted in blue) can move to any of the red squares."
            );
            System.out.println("What square should your piece move to? (ex. G5): ");
            positionEnd = new Position(input.nextLine());
        }
        else {
            positionEnd = new Position(inputString.split(" ")[1]);
        }
        if(!possibleMoves.isPositionInBitboard(positionEnd)){
            throw new IllegalArgumentException(
                "Your piece can not move there! Choose a square that is highlighted red."
            );
        }
        board.completeMoveProcess(positionStart, positionEnd, getNextPlayer());
        whitePlays = !whitePlays;
    }
}