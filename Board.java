
import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final char BLACK_CHIP = 'B';
    public static final char WHITE_CHIP = 'W';
    private static final char EMPTY = '_';

    private char[][] grid;

    // Create a board with a square size
    // We expect the size of the board to be an even number
    public Board(int size) {
        grid = new char[size][size];

        // Clear the grid
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                grid[r][c] = EMPTY;
            }
        }

        // Place white and back chips a the center
        int midRow = (size / 2) - 1;
        int midCol = (size / 2) - 1;

        grid[midRow][midCol] = WHITE_CHIP;
        grid[midRow + 1][midCol] = BLACK_CHIP;
        grid[midRow][midCol + 1] = BLACK_CHIP;
        grid[midRow + 1][midCol + 1] = WHITE_CHIP;
    }

    // Put a chip on the board if possible
    public boolean placeChip(char chip, Location location) {
        if (chip != BLACK_CHIP && chip != WHITE_CHIP) {
            // Reject unknown type of chip
            return false;
        }

        if (location.getRow() < 0 || location.getRow() >= grid.length || location.getColumn() < 0 || location.getColumn() >= grid.length) {
            // Reject wrong coordinates
            return false;
        }

        if (grid[location.getRow()][location.getColumn()] != EMPTY) {
            // Reject if slot is already taken
            return false;
        }

        // Check if the user can score with this move
        boolean scored = false;

        // Set all directions:   N, NE,  E, SE,  S, SW,  W, NW
        int[] rowDirections = {-1, -1, +0, +1, +1, +1, +0, -1};
        int[] colDirections = {+0, +1, +1, +1, +0, -1, -1, -1};

        for (int direction = 0; direction < rowDirections.length; direction++) {
            int targetRow = location.getRow() + rowDirections[direction];
            int targetCol = location.getColumn() + colDirections[direction];

            // Check one step ahead, chip should be the opposite
            if (targetRow >= 0 && targetRow < grid.length
                    && targetCol >= 0 && targetCol < grid.length
                    && grid[targetRow][targetCol] != EMPTY
                    && grid[targetRow][targetCol] != chip) {

                // Check if we can score on this direction
                int startRow = targetRow;
                int startCol = targetCol;

                int stopRow = -1;
                int stopCol = -1;

                for (int row = startRow, col = startCol;
                        row >= 0 && row < grid.length
                        && col >= 0 && col < grid.length;
                        row += rowDirections[direction], col += colDirections[direction]) {
                    if (grid[row][col] == chip) {
                        stopRow = row;
                        stopCol = col;
                        break;
                    }

                    if (grid[row][col] == EMPTY) {
                        break;
                    }
                }

                // We found something to score
                if (stopRow != -1 && stopCol != -1) {
                    for (int row = startRow, col = startCol;
                            row != stopRow || col != stopCol;
                            row += rowDirections[direction], col += colDirections[direction]) {
                        grid[row][col] = chip;
                    }

                    scored = true;
                }
            }
        }

        if (scored) {
            grid[location.getRow()][location.getColumn()] = chip;
        }

        return scored;
    }

    // Check if move is legal but will not flip any chip
    public boolean isLegalMove(char chip, Location location) {
        if (chip != BLACK_CHIP && chip != WHITE_CHIP) {
            // Reject unknown type of chip
            return false;
        }

        if (location.getRow() < 0 || location.getRow() >= grid.length || location.getColumn() < 0 || location.getColumn() >= grid.length) {
            // Reject wrong coordinates
            return false;
        }

        if (grid[location.getRow()][location.getColumn()] != EMPTY) {
            // Reject if slot is already taken
            return false;
        }

        // Set all directions:   N, NE,  E, SE,  S, SW,  W, NW
        int[] rowDirections = {-1, -1, +0, +1, +1, +1, +0, -1};
        int[] colDirections = {+0, +1, +1, +1, +0, -1, -1, -1};

        for (int direction = 0; direction < rowDirections.length; direction++) {
            int targetRow = location.getRow() + rowDirections[direction];
            int targetCol = location.getColumn() + colDirections[direction];

            // Check one step ahead, chip should be the opposite
            if (targetRow >= 0 && targetRow < grid.length
                    && targetCol >= 0 && targetCol < grid.length
                    && grid[targetRow][targetCol] != EMPTY
                    && grid[targetRow][targetCol] != chip) {

                // Check if we can score on this direction
                int startRow = targetRow;
                int startCol = targetCol;

                int stopRow = -1;
                int stopCol = -1;

                for (int row = startRow, col = startCol;
                        row >= 0 && row < grid.length
                        && col >= 0 && col < grid.length;
                        row += rowDirections[direction], col += colDirections[direction]) {
                    if (grid[row][col] == chip) {
                        stopRow = row;
                        stopCol = col;
                        break;
                    }

                    if (grid[row][col] == EMPTY) {
                        break;
                    }
                }

                // We found something to score
                if (stopRow != -1 && stopCol != -1) {
                    return true;
                }
            }
        }

        return false;
    }

    // Find all legal moves of a chip
    public List<Location> getLegalMoves(char chip) {
        List<Location> locations = new ArrayList<>();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                Location location = new Location(r, c);

                if (isLegalMove(chip, location)) {
                    locations.add(location);
                }
            }
        }

        return locations;
    }

    // Get the score of a chip
    public int getScore(char chip) {
        int score = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                if (grid[r][c] == chip) {
                    score++;
                }
            }
        }

        return score;
    }

    // Return a string representation of a board for display
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                sb.append(grid[r][c]);
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
