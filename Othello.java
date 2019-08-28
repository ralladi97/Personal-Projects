
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Othello {

    private static Scanner in = new Scanner(System.in);

    // Force the user to enter an integer
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine();

            try {
                return Integer.parseInt(line);
            } catch (Exception e) {
                System.out.println("Error: Please enter a numeric value.");
            }
        }
    }

    // Let the user decide the size of the board
    private static Board createBoard() {
        // Board should be even size
        while (true) {
            int size = readInt("Enter board size: ");

            if (size < 4) {
                System.out.println("Error: Board size must at least be 4.");
            } else if (size % 2 != 0) {
                System.out.println("Error: Board size should be an even value.");
            } else {
                return new Board(size);
            }
        }
    }

    // Entry point of the program
    public static void main(String[] args) {
        // Create a board
        Board board = createBoard();

        // Let user decide whether to play black or white 
        int option = readInt("Would you like to play black? (1 = Yes, 0 = No): ");

        while (option != 0 && option != 1) {
            System.out.println("Error: Please enter 1 or 0 only.");
            option = readInt("Would you like to play black? (1 = Yes, 0 = No): ");
        }

        char playerChip = Board.BLACK_CHIP;
        char computerChip = Board.WHITE_CHIP;

        if (option == 0) {
            playerChip = Board.WHITE_CHIP;
            computerChip = Board.BLACK_CHIP;
        }

        // Black always move first
        Random random = new Random();
        char currentChip = Board.BLACK_CHIP;

        int blackScore = board.getScore(Board.BLACK_CHIP);
        int whiteScore = board.getScore(Board.WHITE_CHIP);

        System.out.println();

        while (true) {
            String currentPlayer = "Black";

            if (currentChip == Board.WHITE_CHIP) {
                currentPlayer = "White";
            }

            System.out.println("Current Player: " + currentPlayer);
            System.out.println(board);
            System.out.println("Score: Black: " + blackScore + ", White: " + whiteScore);

            // Check if there are legal moves for the current chip
            List<Location> legalLocations = board.getLegalMoves(currentChip);

            if (legalLocations.isEmpty()) {
                System.out.println("There is no more legal moves for current player. Game over.");
                break;
            }

            // Make current player move
            if (currentChip == computerChip) {
                // If current player is a computer make a random move
                Location location = legalLocations.get(random.nextInt(legalLocations.size()));
                board.placeChip(currentChip, location);
                System.out.println("Success: " + currentPlayer + " move at " + location);
            } else if (currentChip == playerChip) {
                // If current player is a user, then input is required
                while (true) {
                    int row = readInt("Enter Row: ");
                    int col = readInt("Ener col: ");
                    Location location = new Location(row, col);

                    if (board.placeChip(currentChip, location)) {
                        System.out.println("Success: " + currentPlayer + " move at " + location);
                        break;
                    }

                    System.out.println("Error: Invalid move.");
                }
            }

            // Update the scores
            blackScore = board.getScore(Board.BLACK_CHIP);
            whiteScore = board.getScore(Board.WHITE_CHIP);

            // Switch players
            if (currentChip == Board.BLACK_CHIP) {
                currentChip = Board.WHITE_CHIP;
            } else {
                currentChip = Board.BLACK_CHIP;
            }

            System.out.println();
        }

        System.out.println();

        // Print out winner
        if (blackScore > whiteScore) {
            System.out.println("Black wins!");
        } else if (blackScore < whiteScore) {
            System.out.println("White wins!");
        } else {
            System.out.println("Draw!");
        }
    }
}
