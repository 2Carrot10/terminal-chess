import java.util.*;


// This class represents a king. This class contains and returns the unicode symbol for the 
// peice. It also calculates and returns a bitboard of all possible moves the piece can make 
// (not accounting for moves that would put itself in check).
public class King extends Piece {

    public final static char UNICODE_SYMBOL_WHITE = '♚';
    public final static char UNICODE_SYMBOL_BLACK = '♔';

    // Behavior: 
    //   - This constructor creates a king of the given color.
    // Parameters:
    //   - isWhite: the color of the piece
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public King(boolean isWhite) {
        super(isWhite);
    }

    // Behavior: 
    //   - This constructor creates a king with the same color as other.
    // Parameters:
    //   - other: the piece to be copied.
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public King(King other) {
        super(other.getIsWhite());
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

    // Behavior:
    //   - This method returns all locations the peice can make (not accounting for checks).
    // Parameters:
    //   - board: the current board state
    //   - position: the current position of the piece
    // Returns:
    //   -  Bitboard: the bitboard of all locations the piece can get to
    // Exceptions:
    //   - none
    @Override
    public Bitboard getMoveBitboard(Board board, Position position) {
        Bitboard colorPiecesBitboard =  board.getColorBitboard(getIsWhite());
        colorPiecesBitboard.invert();
        
        Bitboard currentBitboard = new Bitboard();
        for (Vector2[] directionArray : List.of(ORTHOGONAL_DIRECTIONS, DIAGONAL_DIRECTIONS)) {
            for(Vector2 direction : directionArray) {
                int x = position.getX() + direction.getX();
                int y = position.getY() + direction.getY();
                if (colorPiecesBitboard.isPositionInBitboard(x, y)) {
                    currentBitboard.tryAddPosition(x, y);
                }
            }
        }
        return currentBitboard;

    }
}