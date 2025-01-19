import java.util.*;

// This class represents a a sliding piece. This class calculates and returns a bitboard
// of all possible moves the piece can make based on a list of dirrections the piece can
// slide passed in through the constructor (not accounting for moves that would put its
// own king in check). Rooks, bishops, and queens are all types of sliding pieces.
public abstract class SlidingPiece extends Piece {

    private Vector2[] completeDirectionArray;

    //   - This constructor creates a sliding piece and sets the color, and directions of travel of the piece.
    // Parameters:
    //   - isWhite: weather or not the piece is white
    //   - directionList1: the first array of all directions the piece can travel
    //   - directionList2: the second arrayof all directions the piece can travel
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public SlidingPiece(boolean isWhite, Vector2[] directionList1, Vector2[] directionList2) {
        this(isWhite, combineArrays(directionList1, directionList2));
    }

    //   - This constructor creates a sliding piece and sets the color, and directions of travel of
    //     the piece.
    // Parameters:
    //   - isWhite: weather or not the piece is white
    //   - completeDirectionList: an array of all directions the piece can travel
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public SlidingPiece(boolean isWhite, Vector2[] completeDirectionList) {
        super(isWhite);
        this.completeDirectionArray = completeDirectionList;
    }
    
    // Behavior:
    //   - This method combines two direction arrays into one direction array.
    // Parameters:
    //   - directionList1: the first direction array
    //   - directionList2: the second direction array
    // Returns:
    //   -  Vector2[]: the combined direction array
    // Exceptions:
    //   - none
    private static Vector2[] combineArrays(Vector2[] directionList1, Vector2[] directionList2)  {
        Vector2[] completeDirectionArray = new Vector2[directionList1.length + directionList2.length];
        int i = 0;
        for(Vector2[] directionArray : List.of(directionList1, directionList2)) {
            for(Vector2 direction : directionArray) {
                completeDirectionArray[i] = direction;
                i++;
            }
        }
        return completeDirectionArray;
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
        Bitboard ourPiecesBitboard =  board.getColorBitboard(getIsWhite());
        Bitboard theirPiecesBitboard =  board.getColorBitboard(!getIsWhite());
        Bitboard currentBitboard = new Bitboard();
            for(Vector2 direction : completeDirectionArray) {
                Position currPos = new Position(position.getX(), position.getY());

                // Using break would be much more clean, but it is not allowed, so I am using
                // the hasRunIntoOtherPiece variable.
                boolean hasRunIntoOtherPiece = false;
                while(currPos.tryMove(direction) && !hasRunIntoOtherPiece){
                    if(ourPiecesBitboard.isPositionInBitboard(currPos)) {
                        hasRunIntoOtherPiece = true;
                    } else {
                        if (theirPiecesBitboard.isPositionInBitboard(currPos)) {
                            hasRunIntoOtherPiece = true;
                        }
                        currentBitboard.addPosition(currPos.getX(), currPos.getY());
                    }
                    }
                    
                }
            
        return currentBitboard;
    }
}