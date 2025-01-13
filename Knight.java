// Eleanor Grutz
// 07-10-2024
// CSE 123 
// P0: Creative Project 2: Abstract Strategy Games
// TA: Trien

// This class represents a knight. This class contains and returns the unicode symbol for the 
// peice. It also calculates and returns a bitboard of all possible moves the piece can make 
// (not accounting for moves that would put its own king in check).
public class Knight extends Piece {

    public final static char UNICODE_SYMBOL_WHITE = '♞';
    public final static char UNICODE_SYMBOL_BLACK = '♘';

    // Behavior: 
    //   - This constructor creates a knight of the given color.
    // Parameters:
    //   - isWhite: the color of the piece
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    // Behavior: 
    //   - This constructor creates a knight with the same color as other.
    // Parameters:
    //   - other: the piece to be copied.
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Knight(Knight other) {
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
        int x = position.getX();
        int y = position.getY();
        Bitboard ourPiecesBitboard =  board.getColorBitboard(getIsWhite());
        Bitboard cb = new Bitboard(); //currentBitboard

        if (!ourPiecesBitboard.isPositionInBitboard(x + 2, y + 1)) cb.tryAddPosition(x + 2, y + 1);
        if (!ourPiecesBitboard.isPositionInBitboard(x - 2, y + 1)) cb.tryAddPosition(x - 2, y + 1);
        if (!ourPiecesBitboard.isPositionInBitboard(x + 2, y + 1)) cb.tryAddPosition(x + 2, y - 1);
        if (!ourPiecesBitboard.isPositionInBitboard(x - 2, y + 1)) cb.tryAddPosition(x - 2, y - 1);

        if (!ourPiecesBitboard.isPositionInBitboard(x + 1, y + 2)) cb.tryAddPosition(x + 1, y + 2);
        if (!ourPiecesBitboard.isPositionInBitboard(x - 1, y + 2)) cb.tryAddPosition(x - 1, y + 2);
        if (!ourPiecesBitboard.isPositionInBitboard(x + 1, y - 2)) cb.tryAddPosition(x + 1, y - 2);
        if (!ourPiecesBitboard.isPositionInBitboard(x - 1, y - 2)) cb.tryAddPosition(x - 1, y - 2);

        
        return cb;
    }
}
