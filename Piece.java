
// Eleanor Grutz
// 07-10-2024
// CSE 123 
// P0: Creative Project 2: Abstract Strategy Games
// TA: Trien

// This abstract class represents a chess piece. It contains constants that many chess pieces use
// such as diffrent directions on the chess board, and a placeholder variable for unicode symbols.
// This class stores and returns the color of the piece and contains multiple methods that concrete
// classes (actual pieces) must build on.
public abstract class Piece {

    final private boolean isWhite;

    public final static Vector2 UP = new Vector2(0, 1);
    public final static Vector2 DOWN = new Vector2(0, -1);
    public final static Vector2 LEFT = new Vector2(1, 0);
    public final static Vector2 RIGHT = new Vector2(-1, 0);

    public final static Vector2 UP_RIGHT = new Vector2(1, 1);
    public final static Vector2 DOWN_RIGHT = new Vector2(1, -1);
    public final static Vector2 UP_LEFT = new Vector2(-1, 1);
    public final static Vector2 DOWN_LEFT = new Vector2(-1, -1);

    public final static Vector2[] ORTHOGONAL_DIRECTIONS = {UP, DOWN, LEFT, RIGHT};
    public final static Vector2[] DIAGONAL_DIRECTIONS = {UP_RIGHT, DOWN_RIGHT, UP_LEFT, DOWN_LEFT};

    // Behavior: 
    //   - This method creates a piece with a color
    // Parameters:
    //   - color: color of the piece
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    // Behavior: 
    //   - This method returns the unicode symbol of the piece.
    // Parameters:
    //   - none
    // Returns:
    //   -  char: the unicode symbol of the piece
    // Exceptions:
    //   - none
    public abstract char getUnicodeSymbol();

    // Behavior:
    //   - This method returns all locations the peice can make (not accounting for checks).
    // Parameters:
    //   - board: the current board state
    //   - position: the current position of the piece
    // Returns:
    //   -  Bitboard: the bitboard of all locations the piece can get to
    // Exceptions:
    //   - none
    public abstract Bitboard getMoveBitboard(Board board, Position position);
    
    // Behavior:
    //   - This method completes all neccecary state changes that occur to a peice after a move.
    // Parameters:
    //   - none
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public void makeMove() {

    }

    // Behavior: 
    //   - This method returns the color of the piece
    // Parameters:
    //   - none
    // Returns:
    //   -  isWhite: weather the peice is white. (if it is not white, it is black).
    // Exceptions:
    //   - none
    public boolean getIsWhite(){
        return isWhite;
    }
}