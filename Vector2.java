// Eleanor Grutz
// 07-10-2024
// CSE 123 
// P0: Creative Project 2: Abstract Strategy Games
// TA: Trien

// This class a vector with two integer components. this class stores the x and y values of the
// vector. It can also perform translations on the vector by using another vector.
public class Vector2 {
    private int x;
    private int y;

    // Behavior: 
    //   - This constructor creates the vector2 with an x and y component.
    // Parameters:
    //   - x: the x position of the vector.
    //   - y: the y positoin of the vector.
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Behavior: 
    //   - This method returns the x value of the vector.
    // Parameters:
    //   - none
    // Returns:
    //   -  String: the x component of the vector
    // Exceptions:
    //   - none
    public int getX(){
        return x;
    }

    // Behavior: 
    //   - This method returns the y value of the vector.
    // Parameters:
    //   - none
    // Returns:
    //   -  String: the y component of the vector
    // Exceptions:
    //   - none
    public int getY(){
        return y;
    }

    // Behavior: 
    //   - This method translates the vector by another vector
    // Parameters:
    //   - vector: the vector to translate this vector by
    // Returns:
    //   -  none
    // Exceptions:
    //   - none
    public void move(Vector2 vector) {
        x += vector.x;
        y += vector.y;
    }
}
