// Eleanor Grutz
// 07-10-2024
// CSE 123 
// P0: Creative Project 2: Abstract Strategy Games
// TA: Trien

// This class represents a a bitboard. A bitboard is a 2d field of booleans. In this context, the 
// array is 8x8, and represents the chess board. Bitboards can be used for a variety of purposes.
// This board contains many utility methods to work with and opperate upon bitboards.
public class Bitboard {
    private long bits;

    // Behavior: 
    //   - This constructor assigns the bits to the bitboard
    // Parameters:
    //   - bits: the bits to set the bitboard to.
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public Bitboard(int bits){
        this.bits = bits;
    }

    // Behavior: 
    //   - This constructor adds a position to the bitboard
    // Parameters:
    //   - position: the position to set the bitboard to.
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public Bitboard(Position position){
        this.bits = 0;
        addPosition(position);
    }
    
    // Behavior: 
    //   - This constructor creates an empty bitboard.
    // Parameters:
    //   - none
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public Bitboard(){
        new Bitboard(0);
    }

    // Behavior: 
    //   - This method returns the bitboard in binary.
    // Parameters:
    //   - none
    // Returns:
    //   - bits: the binary interpretation of the bitboard
    // Exceptions:
    //   - none
    public long getBitboard(){
        return bits;
    }

    // Behavior: 
    //   - This method checks if a position is in the bitboard.
    // Parameters:
    //   - position: the position to check
    // Returns:
    //   - boolean: if the position is in the bitboard
    // Exceptions:
    //   - none
    public boolean isPositionInBitboard(Position position){
        return isPositionInBitboard(position.getX(), position.getY());
    }

    // Behavior: 
    //   - This method checks if a position is in the bitboard.
    // Parameters:
    //   - x: the x value of the position to check
    //   - x: the x value of the position to check
    // Returns:
    //   - boolean: if the position is in the bitboard
    // Exceptions:
    //   - none
    public boolean isPositionInBitboard(int positionX, int positionY) {
        long location = positionX + (positionY * 8);
        return (bits & (1L << location)) != 0;
    }

    // Behavior: 
    //   - This method adds a position to the bitboard.
    // Parameters:
    //   - position: the position to add
    // Returns:
    //   - boolean: if the position could be added. Will fail if the position is already full.
    // Exceptions:
    //   - none
    public boolean addPosition(Position position) {
        return addPosition(position.getX(), position.getY());
    }

    // Behavior: 
    //   - This method adds a position to the bitboard.
    // Parameters:
    //   - positionX: the x value of the position
    //   - positionX: the y value of the position
    // Returns:
    //   - boolean: if the position could be added. Will fail if the position is already full.
    // Exceptions:
    //   - IllegalArgumentException: the position is outside the board
    public boolean addPosition(int positionX, int positionY) {
        if (positionX > 7 || positionX < 0 || positionY > 7 || positionY < 0) {
            throw new IllegalArgumentException("Position is out of bounds");
        }
        if(isPositionInBitboard(positionX, positionY)) return false;
        long location = positionX + positionY * 8;
        bits += 1L << location;
        return true;
    }

    // Behavior: 
    //   - This method adds a position to the bitboard.
    // Parameters:
    //   - positionX: the x value of the position to add
    //   - positionX: the y value of the position to add
    // Returns:
    //   - boolean: if the position could be added. Will fail if the position is already full.
    //     will also fail if the position is out of bounds.
    // Exceptions:
    //   - none
    public boolean tryAddPosition(int positionX, int positionY) {
        if (positionX > 7 || positionX < 0 || positionY > 7 || positionY < 0) return false;
        return addPosition(positionX, positionY);
    }

    // Behavior: 
    //   - This method removes a position from the bitboard.
    // Parameters:
    //   - position: the position to remove
    // Returns:
    //   - boolean: if the position could be added. Will fail if the position is already full.
    // Exceptions:
    //   - none
    public boolean removePosition(Position position){
        return removePosition(position.getX(), position.getY());
    }

    // Behavior: 
    //   - This method removes a position from the bitboard.
    // Parameters:
    //   - positionX: the x value of the position to remove
    //   - positionX: the y value of the position to remove
    // Returns:
    //   - boolean: if the position could be removed. Will fail if the position is already empty.
    // Exceptions:
    //   - IllegalArgumentException: the position is outside the board
    public boolean removePosition(int positionX, int positionY) {
        if (positionX > 7 || positionX < 0 || positionY > 7 || positionY < 0) {
            throw new IllegalArgumentException("Position is out of bounds");
        }
        if(!isPositionInBitboard(positionX, positionY)) return false;
        long location = positionX + positionY * 8;
        bits -= 1L << location;
        return true;
    }

    // Behavior: 
    //   - This method changes the state of this bitboard to the OR of itself and 
    //     another bitboard.
    // Parameters:
    //   - otherBitboard: the other bitboard to combine
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public void or(Bitboard otherBitboard) {
        bits = bits | otherBitboard.bits;
    }

    // Behavior: 
    //   - This method changes the state of this bitboard to the AND of itself and 
    //     another bitboard.
    // Parameters:
    //   - otherBitboard: the other bitboard to combine
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public void and(Bitboard otherBitboard) {
        bits = bits & otherBitboard.bits;
    }

    // Behavior: 
    //   - This method changes the state of this bitboard to the NOT of itself, flipping true and 
    //     false.
    // Parameters:
    //   - none
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public void invert() {
        bits = ~bits;
    }
}
