// Eleanor Grutz
// 07-10-2024
// CSE 123 
// P0: Creative Project 2: Abstract Strategy Games
// TA: Trien

// This class represents the state board state of a chess game. It stores a board, and exposes
// many methods to analyse the current position. This class does not keep track of the current 
// player. This class can be constructed with a specific board layout, although if a layout is not
// specified it will default to the standerd setup.
// Pieces can be moved, although the code using this class must ensure that the move is valid
// before hand because this class will throw an exception if the move attempted is not valid.
public class Board {

    public static final String NO_STYLES = "\u001B[0m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String BLUE_TEXT = "\u001B[34m";
    public static final String RED_BACKGROUND = "\u001B[41m";

    private Piece[][] board;

    private int currentWinner;

    // Behavior: 
    //   - This creates a new board based on an array pieces
    // Parameters:
    //   - board: the pieces on the board
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public Board(Piece[][] board){
        this.board = board;
        currentWinner = -1;
    }

     // Behavior: 
    //   - This creates a new board using the default configuration
    // Parameters:
    //   - none
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public Board(){
        this(new char[][] {
            {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}
        });
    }

    // Behavior: 
    //   - This method copies a board based on a board to copy.
    // Parameters:
    //   - copy: the board that should be cloned
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    private Board(Board copy) {
        board = new Piece[8][8];
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                Piece currPiece = copy.pieceAtPosition(x,y);
                if (currPiece instanceof Pawn) {
                    board[x][y] = new Pawn((Pawn)currPiece);
                }
                else if (currPiece instanceof Knight) {
                    board[x][y] = new Knight((Knight)currPiece);
                }                
                else if (currPiece instanceof King) {
                    board[x][y] = new King((King)currPiece);
                }        
                else if (currPiece instanceof Queen) {
                    board[x][y] = new Queen((Queen)currPiece);
                }                
                else if (currPiece instanceof Rook) {
                    board[x][y] = new Rook((Rook)currPiece);
                }                
                else if (currPiece instanceof Bishop) {
                    board[x][y] = new Bishop((Bishop)currPiece);
                }
            }
        }
    }

    // Behavior: 
    //   - This method creates a new board based on an 8x8 array of characters that represent a board
    //     using the character associated with each piece in algebraic notation.
    // Parameters:
    //   - boardAsChars: the character representation of the board
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    public Board(char[][] boardAsChars){
        this(turnCharArrayIntoPieceArray(boardAsChars));
    }

    // Behavior: 
    //   - This method returns the current board in the format of an 8x8 array of pieces.
    // Parameters:
    //   - none
    // Returns:
    //   - Piece[][]: the current board
    // Exceptions:
    //   - none
    public Piece[][] getBoard(){
        return board;
    }

    // Behavior: 
    //   - This method converts an array of characters into an array of pieces.
    // Parameters:
    //   - charArray: an array of characters representing pieces
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    private static Piece[][] turnCharArrayIntoPieceArray(char[][] charArray){
        Piece[][] board = new Piece[8][8];
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                char pieceLetter = charArray[7-y][x];
                if(pieceLetter == 'P') board[x][y] = new Pawn(true);
                if(pieceLetter == 'p') board[x][y] = new Pawn(false);
                if(pieceLetter == 'Q') board[x][y] = new Queen(true);
                if(pieceLetter == 'q') board[x][y] = new Queen(false);
                if(pieceLetter == 'K') board[x][y] = new King(true);
                if(pieceLetter == 'k') board[x][y] = new King(false);
                if(pieceLetter == 'B') board[x][y] = new Bishop(true);
                if(pieceLetter == 'b') board[x][y] = new Bishop(false);
                if(pieceLetter == 'R') board[x][y] = new Rook(true);
                if(pieceLetter == 'r') board[x][y] = new Rook(false);
                if(pieceLetter == 'N') board[x][y] = new Knight(true);
                if(pieceLetter == 'n') board[x][y] = new Knight(false);
            } 
        }
        return board;
    }

    // Behavior: 
    //   - This method returns a string representing the board that is highlighted based on a
    //     position to highlight.
    // Parameters:
    //   - position: the position to highlight.
    // Returns:
    //   - String: the string of a highlighted board
    // Exceptions:
    //   - none
    public String toStringhighlightedBoard(Position position){
        Bitboard possibleMoves = willNotResultInCheckBitboard(
            position, pieceAtPosition(position).getMoveBitboard(this, position)
        );
        return toStringhighlightedBoard(new Bitboard(position), possibleMoves);
    }

    // Behavior: 
    //   - This method returns a string representing the board that is highlighted based on
    //     highlight bitboards.
    // Parameters:
    //   - currentPieceBitboard: the bitboard of the current piece
    //   - attackBitboard: the bitboard of the attacked positions
    // Returns:
    //   - the string of a highlighted board
    // Exceptions:
    //   - none
    public String toStringhighlightedBoard(Bitboard currentPieceBitboard, 
        Bitboard attackBitboard) {
        String stringToReturn = "";
        Bitboard kingBitboard;

        if(isInCheckmate(true)) {
            stringToReturn += "White (player 1) has been checkmated!\n";
            kingBitboard = kingBitboard(true);
        } else if(isInCheckmate(false)) {
            stringToReturn += "Black (player 2) has been checkmate!\n";
            kingBitboard = kingBitboard(false);
        } else if (isInCheck(true)) {
            stringToReturn += "White (player 1) is in check!\n";
            kingBitboard = kingBitboard(true);
        } else if (isInCheck(false)) {
            stringToReturn += "Black (player 2) is in check!\n";
            kingBitboard = kingBitboard(false);
        }
        else {
            kingBitboard = new Bitboard();
        }

        stringToReturn += " ╔═══════════════╗\n";
        for(int y = 7; y >= 0; y--){
            stringToReturn += y + 1;
            for(int x = 0; x < 8; x++){
                if(x == 0){
                    stringToReturn += "║";
                }
                else{
                    stringToReturn += " ";  
                }
                if(kingBitboard != null && kingBitboard.isPositionInBitboard(x,y)) {
                    stringToReturn += RED_BACKGROUND;
                }
                else if(attackBitboard != null && attackBitboard.isPositionInBitboard(x, y)) {
                    stringToReturn += RED_TEXT;
                    
                }
                else if (currentPieceBitboard != null 
                    && currentPieceBitboard.isPositionInBitboard(x, y)
                ) {
                    stringToReturn += BLUE_TEXT;
                }

                if(board[x][y] == null) {
                    if(attackBitboard != null && attackBitboard.isPositionInBitboard(x, y))
                    {
                        stringToReturn += "🞬";
                    }
                    else {
                        stringToReturn += "•";
                    }
                }
                else {
                    stringToReturn += board[x][y].getUnicodeSymbol();  
                }
                stringToReturn += NO_STYLES;
            }
            stringToReturn += "║\n";
        }
        stringToReturn += " ╚═══════════════╝\n";
        stringToReturn += "  A B C D E F G H\n";
        stringToReturn += RED_TEXT;
        stringToReturn += NO_STYLES;

        return stringToReturn;
    }


    // Behavior: 
    //   - returns a string of important information in the board including an ascii representation
    //     of the board and weather or not the players are in check or checkmate
    // Parameters:
    //   - none
    // Returns:
    //   - String: the string of the board
    // Exceptions:
    //   - none
    public String toString(){
        return toStringhighlightedBoard(null, null);
    }

    // Behavior: 
    //   - This method returns the piece at a certain position.
    // Parameters:
    //   - position: the position to check
    // Returns:
    //   - Piece: the piece at the position
    // Exceptions:
    //   - none
    public Piece pieceAtPosition(Position position){
        return(pieceAtPosition(position.getX(), position.getY()));
    }

    // Behavior: 
    //   - This method returns the piece at a certain position.
    // Parameters:
    //   - x: the x value of the position to check
    //   - y: the y value of the position to check
    // Returns:
    //   - Piece: the piece at the position
    // Exceptions:
    //   - none
    public Piece pieceAtPosition(int x, int y){
        return board[x][y];
    }

    // Behavior: 
    //   - This method sets the piece at a position
    // Parameters:
    //   - x: the x value of the position to change
    //   - y: the y value of the position to cchange
    //   - piece: the piece to add
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    private void setPosition(int x, int y, Piece piece){
        board[x][y] = piece;
    }

    // Behavior: 
    //   - This method sets the piece at a position.
    // Parameters:
    //   - position: the position to change
    //   - piece: the piece to add
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    private void setPosition(Position position, Piece piece){
        setPosition(position.getX(), position.getY(), piece);
    }

    // Behavior: 
    //   - This method moves a a piece at a position to another position and updates the current
    //     winner based on the new position. The move must be valid or an exception will be thrown.
    //     that the move is valid.
    // Parameters:
    //   - startPos: the origional position of the piece.
    //   - endPos: the ending position of the piece.
    // Returns:
    //   - none
    // Exceptions:
    //   - IllegalArgumentException: the move attempted is not a valid move. 
    // Note: this function should not be used to validate possible moves use getMoveBitboard to
    // ensure the move is valid before making it!
    public void completeMoveProcess(Position startPos, Position endPos, int nextPlayer){
        if(!getMoveBitboard(startPos).isPositionInBitboard(endPos)) {
            throw new IllegalArgumentException("Move is not legal!");
        }
        move(startPos, endPos);
        checkForWins(nextPlayer);
    }


    // Behavior: 
    //   - This method moves a ition to another position.
    // Parameters:piece at a pos
    //   - startPos: the origional position of the piece.
    //   - endPos: the ending position of the piece.
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    private void move(Position startPos, Position endPos) {
        pieceAtPosition(startPos).makeMove();
        setPosition(endPos, pieceAtPosition(startPos));
        setPosition(startPos, null);
    }

    // Behavior: 
    //   - This method updates the currentWinner so that it still reflects the current board.
    // Parameters:
    //   - player: The player who played most recently. This is also the player that has the chance
    //     to win.
    // Returns:
    //   - none
    // Exceptions:
    //   - none
    private void checkForWins(int player) {
        if(isInCheckmate(player == 2)) currentWinner = player;
    }

    // Behavior: 
    //   - This method returns the player id of the current winner. If the game is not over, it
    //     returns -1.
    // Parameters:
    //   - isWhite: a boolean representing weather or not the pieces of the player who might be 
    //     in checkmate are white. isWhite is true if they are, false if they are not.
    // Returns:
    //   - int: the id of the winning player or -1 if the game is not over.
    // Exceptions:
    //   - none
    public int getWinner(){
        return currentWinner;
    }

    // Behavior: 
    //   - This method checks if the player isWhite currently is in checkmate
    // Parameters:
    //   - isWhite: a boolean representing weather or not the pieces of the player who might be 
    //     in checkmate are white. isWhite is true if they are, false if they are not.
    // Returns:
    //   - boolean: a boolean representing weather the player being analysed is in checkmate.
    // Exceptions:
    //   - none
    private boolean isInCheckmate(boolean isWhite) {

        //If a player is not even being attacked, they can not possibly be in checkmate.
        if(!isInCheck(isWhite)) return false;

        Bitboard thisBitboard = getColorBitboard(isWhite);
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {

                // If the player can still play valid moves, they are not in checkmate.
                if (thisBitboard.isPositionInBitboard(i,j)) {
                    if(getMoveBitboard(new Position(i, j)).getBitboard() > 0) return false;
                }
            }
        }
        return true;
    }
    
    // Behavior: 
    //   - This method checks if a certian player is in check in the current position.
    // Parameters:
    //   - isWhite: a boolean representing weather or not the pieces of the player who might be 
    //     in check are white. isWhite is true if they are, false if they are not.
    // Returns:
    //   - boolean: a boolean representing weather the player being analysed is in check.
    // Exceptions:
    //   - none.
    private boolean isInCheck(boolean isWhite) {
        Bitboard thisBitboard = getColorBitboard(!isWhite);
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if (thisBitboard.isPositionInBitboard(i,j)) {
                    if(canTakeKing(new Position(i, j), !isWhite)) return true;
                }
            }
        }
        return false;
    }

    // Behavior: 
    //   - This method returns the valid moves a piece can make based on the position and possible
    //     moves the piece can make without taking into acount checks. In this context, valid moves
    //     mean moves that are both allowed for a specific piece and do not put the person who 
    //     moved in check (which would be illegal).
    // Parameters:
    //   - position: the position of the moving piece
    //   - endpoints: the positions it would be able to move to without factoring in the chance of
    //     the player puting themself in checkmate. The enpoints value acounts for pieces that are 
    //     in the way of being able to make the move.
    // Returns:
    //   - Bitboard: a bitboard representing the positions the piece can move to
    // Exceptions:
    //   - none
    private Bitboard willNotResultInCheckBitboard(Position position, Bitboard endpoints) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(endpoints.isPositionInBitboard(i, j)){
                    Board hypotheticalMove = new Board(this);
                    hypotheticalMove.move(position, new Position(i, j));
                    if(hypotheticalMove.isInCheck(pieceAtPosition(position).getIsWhite())) {
                        endpoints.removePosition(i, j);
                    }
                }
            }
        }
        return endpoints;
    }

    // Behavior: 
    //   - This method returns the valid moves a piece can make based on the position of the piece.
    //     In this context, valid moves mean moves that are both allowed for a specific piece and
    //     do not put the person who moved in check (which would be illegal).
    // Parameters:
    //   - position: the position of the moving piece
    // Returns:
    //   - Bitboard: a bitboard representing the positions the piece can move to
    // Exceptions:
    //   - none
    public Bitboard getMoveBitboard(Position position) {
        return willNotResultInCheckBitboard(
            position, 
            pieceAtPosition(position).getMoveBitboard(this, position)
        );
    }

    // Behavior: 
    //   - This method checks weather a hypothetical piece would be able to take the oposing king.
    // Parameters:
    //   - position: the position of the attacking piece
    //   - isWhite: weather or not the attacking player is using the white pieces.
    // Returns:
    //   - Bitboard: a bitboard representing the position of the pieces
    // Exceptions:
    //   - none.
    private boolean canTakeKing(Position position, boolean isWhite) {

        //all moves are attacks if they land on an enemy piece
        Bitboard attackBitboard = pieceAtPosition(position).getMoveBitboard(this, position);
        Bitboard kingBitboard = kingBitboard(!isWhite);
        Bitboard combination = attackBitboard;
        combination.and(kingBitboard);
        return combination.getBitboard() > 0;
    }

    // Behavior: 
    //   - This method returns a bitboard containing the positions all pieces of both colors.
    // Parameters:
    //   - none
    // Returns:
    //   - Bitboard: a bitboard representing the position of the pieces
    // Exceptions:
    //   - none.
    public Bitboard getPieceBitboard() {
        Bitboard bitboard = getColorBitboard(true);
        bitboard.or(getColorBitboard(false));
        return bitboard;
    }

    // Behavior: 
    //   - This method returns a bitboard containing the positions all pieces of a specific color.
    // Parameters:
    //   - isWhite: a boolean representing weather or not the pieces being checked are white. 
    //     isWhite is true if they are, false if they are not.
    // Returns:
    //   - Bitboard: a bitboard representing the position of the pieces
    // Exceptions:
    //   - none.
    public Bitboard getColorBitboard(boolean isWhite) {
        Bitboard bitboard = new Bitboard();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null && (board[i][j].getIsWhite() == isWhite)) {
                    bitboard.addPosition(i,j);
                }
            }
        }
        return bitboard;
    }

    // Behavior: 
    //   - This method returns a bitboard containing the position of the king of a specific color.
    // Parameters:
    //   - isWhite: a boolean representing weather or not the king is a white piece. 
    //     isWhite is true if they are, false if they are not.
    // Returns:
    //   - Bitboard: a bitboard representing the position of the king
    // Exceptions:
    //   - none.
    private Bitboard kingBitboard(boolean isWhite) {
        Bitboard bitboard = new Bitboard();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null && (board[i][j].getIsWhite() == isWhite
                && board[i][j] instanceof King)) {
                    bitboard.addPosition(i, j);
                }
            }
        }
        return bitboard;
    }
}
