
// This class represents a rook. This class contains and returns the unicode symbol for the 
// peice. It also calculates and returns a bitboard of all possible moves the piece can make 
// (not accounting for moves that would put its own king in check).
public class Rook extends SlidingPiece {
    
    public final static char UNICODE_SYMBOL_WHITE = '♜';
    public final static char UNICODE_SYMBOL_BLACK = '♖';
    
    // Behavior: 
    //   - This constructor creates a rook of the given color.
    // Parameters:
    //   - isWhite: the color of the piece
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Rook(boolean isWhite) {
        super(isWhite, ORTHOGONAL_DIRECTIONS);
    }

    // Behavior: 
    //   - This constructor creates a knight with the same color as other.
    // Parameters:
    //   - other: the piece to be copied.
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Rook(Rook other) {
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
