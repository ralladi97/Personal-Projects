
public class Location {

    private int row;
    private int col;

    // Create a location to be used to access the board
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Get row location
    public int getRow() {
        return row;
    }

    // Get column location
    public int getColumn() {
        return col;
    }

    // Return a string representation
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
