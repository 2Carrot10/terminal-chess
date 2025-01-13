// Eleanor Grutz
// 07-10-2024
// CSE 123 
// P0: Creative Project 2: Abstract Strategy Games
// TA: Trien

// This class represents a position on the chess board. This class must recive a position,
// either in the form of a rank and file, or in algebraic notation. It can perform translations
// on the position, and stores the position (x and y value) of the object.
public class Position {
    Vector2 position;
    public Position(int x, int y){
        checkIllegalPosition(x, y);
        position = new Vector2(x, y);
    }

    public Position(String algebraicPosition){
        if(algebraicPosition.length() != 2) {
            throw new IllegalArgumentException("Algebraic position must be 2 characters long!");
        }
        int x = algebraicPosition.toUpperCase().charAt(0) - 'A';
        int y = algebraicPosition.charAt(1) - '1';
        checkIllegalPosition(x, y);
        position = new Vector2(x, y);
    }

    // Behavior: 
    //   - This method returns the x value of the position.
    // Parameters:
    //   - none
    // Returns:
    //   -  String: the x component of the position
    // Exceptions:
    //   - none
    public int getX(){
        return position.getX();
    }

    // Behavior: 
    //   - This method returns the y value of the position.
    // Parameters:
    //   - none
    // Returns:
    //   -  String: the y component of the position
    // Exceptions:
    //   - none
    public int getY(){
        return position.getY();
    }

    // Behavior: 
    //   - This method translates the position by a vector
    // Parameters:
    //   - vector: the vector to translate this position by
    // Returns:
    //   -  none
    // Exceptions:
    //   - IllegalArgumentException: position is outside the board.
    public void move(Vector2 vector) {
        checkIllegalPosition(vector.getX(), vector.getY());
        position.move(vector);
    }

    // Behavior: 
    //   - This method attempts to translate the position by a vector
    // Parameters:
    //   - vector: the vector to translate this position by
    // Returns:
    //   -  boolean: was the translation sucsessful
    // Exceptions:
    //   - none
    public boolean tryMove(Vector2 vector) {
        // We are not allowed to use try and catch so I am using a repetetive solution
        if(position.getX() + vector.getX() > 7 || position.getX() + vector.getX() < 0) {
            return false;
        }
        if(position.getY() + vector.getY() > 7 || position.getY() + vector.getY() < 0) {
            return false;
        }
        position.move(vector);
        return true;
    }

    // Behavior: 
    //   - This method checks for illegal positions
    // Parameters:
    //   - x: the x value of the position
    //   - y: the y value of the position
    // Returns:
    //   -  none
    // Exceptions:
    //   - IllegalArgumentException: the position is outside the board
    private static void checkIllegalPosition(int x, int y) {
        if(x > 7 || x < 0) throw new IllegalArgumentException(
            "The x paramater must be between 0 and 7 inclusive!"
        );
        if(y > 7 || y < 0) throw new IllegalArgumentException(
            "The y paramater must be between 0 and 7 inclusive!"
        );
    }
}
