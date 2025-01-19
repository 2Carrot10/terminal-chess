
// This class represents a queen. This class contains and returns the unicode symbol for the 
// peice. It also calculates and returns a bitboard of all possible moves the piece can make 
// (not accounting for moves that would put its own king in check). It also contains and 
// returns the color of the peice.
public class Queen extends SlidingPiece {

    public final static char UNICODE_SYMBOL_WHITE = '♛';
    public final static char UNICODE_SYMBOL_BLACK = '♕';

    // Behavior: 
    //   - This constructor creates a queen of the given color.
    // Parameters:
    //   - isWhite: the color of the piece
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Queen(boolean isWhite) {
        super(isWhite, ORTHOGONAL_DIRECTIONS, DIAGONAL_DIRECTIONS);
    }
    
    // Behavior: 
    //   - This constructor creates a queen with the same color as other.
    // Parameters:
    //   - other: the piece to be copied.
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Queen(Queen other) {
        this(other.getIsWhite());
    }
    
    // Behavior: 
    //   - This method returns the unicode symbol of the piece.
    // Parameters:
    //   - none
    // Returns:
    //   -  char: the unicode symbol of the piece
    // Exceptions:
    //   - none
    @Override
    public char getUnicodeSymbol(){
        return getIsWhite() ? UNICODE_SYMBOL_WHITE : UNICODE_SYMBOL_BLACK;
    }
}

