
// This class represents a bishop. This class contains and returns the unicode symbol for the 
// peice. It also calculates and returns a bitboard of all possible moves the piece can make 
// (not accounting for moves that would put its own king in check).
public class Bishop extends SlidingPiece {

    public final static char UNICODE_SYMBOL_WHITE = '♝';
    public final static char UNICODE_SYMBOL_BLACK = '♗';

    // Behavior: 
    //   - This constructor creates a bishop of the given color.
    // Parameters:
    //   - isWhite: the color of the piece
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Bishop(boolean isWhite) {
        super(isWhite, DIAGONAL_DIRECTIONS);
    }

    // Behavior: 
    //   - This constructor creates a bishop with the same color as other.
    // Parameters:
    //   - other: the piece to be copied.
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Bishop(Bishop other) {
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
