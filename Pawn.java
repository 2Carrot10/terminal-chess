// Eleanor Grutz
// 07-10-2024
// CSE 123 
// P0: Creative Project 2: Abstract Strategy Games
// TA: Trien

// This class represents a pawn. This class contains and returns the unicode symbol for the 
// peice. It also calculates and returns a bitboard of all possible moves the piece can make 
// (not accounting for moves that would put its own king in check). It also contains and 
// returns the color of the peice.
public class Pawn extends Piece {
    
    public final static char UNICODE_SYMBOL_WHITE = '♟';
    public final static char UNICODE_SYMBOL_BLACK = '♙';
    boolean hasMoved = false;

    // Behavior: 
    //   - This constructor creates a pawn of the given color.
    // Parameters:
    //   - isWhite: the color of the piece
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Pawn(boolean isWhite, boolean hasMoved) {
        super(isWhite);
        this.hasMoved = hasMoved;
    }

    // Behavior: 
    //   - This constructor creates a pawn with the same color as other.
    // Parameters:
    //   - other: the piece to be copied.
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Pawn(boolean isWhite) {
        this(isWhite, false);
    }

    // Behavior: 
    //   - This constructor creates a pawn with the same color and movement information as other.
    // Parameters:
    //   - other: the piece to be copied.
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public Pawn(Pawn other) {
        this(other.getIsWhite(), other.getHasMoved());
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
    //   - This method returns a boolean representing weather or not the pawn has moved.
    // Parameters:
    //   - none
    // Returns:
    //   -  boolean: weather the pawn has moved. true if it has moved, false if it has not
    // Exceptions:
    //   - none
    public boolean getHasMoved() {
        return hasMoved;
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
        Bitboard piecesBitboard =  board.getPieceBitboard();
        Bitboard colorPiecesBitboard =  board.getColorBitboard(!getIsWhite());
        Bitboard currentBitboard = new Bitboard();
        int dir = getIsWhite() ? 1 : -1;
        
        // Pawn can either move {1} space or {1, 2} spaces.
        for(int dist : hasMoved ? new int[]{dir} : new int[]{dir,dir * 2}) {
            if(!piecesBitboard.isPositionInBitboard(position.getX(), position.getY() + dist)) {
                currentBitboard.tryAddPosition(position.getX(), position.getY() + dist);
            }
        }
        for(int offset : new int[]{1, -1}) {
            if(colorPiecesBitboard.isPositionInBitboard(position.getX() + offset, position.getY() + dir)) {
                currentBitboard.tryAddPosition(position.getX() + offset, position.getY() + dir);
            }
        }

        return currentBitboard;
    }

    // Behavior:
    //   - This method completes all neccecary state changes that occur to a peice after a move.
    // Parameters:
    //   - none
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    @Override
    public void makeMove() {
        hasMoved = true;
    }
}
