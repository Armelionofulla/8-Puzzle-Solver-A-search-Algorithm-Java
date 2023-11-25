import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;

    // Constructor
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, n);
        }
    }

    // String representation of the board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // Tile at position (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        return tiles[row][col];
    }

    // Board size n
    public int size() {
        return n;
    }

    // Number of tiles out of place
    public int hamming() {
        int count = 0;
        int goalValue = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != goalValue && tiles[i][j] != 0) {
                    count++;
                }
                goalValue++;
            }
        }
        return count;
    }

    // Sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int goalRow = (tiles[i][j] - 1) / n;
                    int goalCol = (tiles[i][j] - 1) % n;
                    sum += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return sum;
    }

    // Is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // Does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board other = (Board) y;
        return Arrays.deepEquals(this.tiles, other.tiles);
    }

    // All neighboring boards
    // All neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();

        // Find the position of the blank (zero) tile
        int blankRow = -1;
        int blankCol = -1;
        outerLoop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break outerLoop;
                }
            }
        }

        // Check and generate neighboring boards
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right
        for (int[] dir : directions) {
            int newRow = blankRow + dir[0];
            int newCol = blankCol + dir[1];

            if (isValid(newRow, newCol)) {
                // Create a new board by swapping the blank tile with a neighboring tile
                int[][] newTiles = copyTiles();
                swap(newTiles, blankRow, blankCol, newRow, newCol);
                neighbors.add(new Board(newTiles));
            }
        }

        return neighbors;
    }

    // Helper method to check if the given position is within the board boundaries
    private boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    // Helper method to create a deep copy of the tiles array
    private int[][] copyTiles() {
        int[][] newTiles = new int[n][];
        for (int i = 0; i < n; i++) {
            newTiles[i] = Arrays.copyOf(tiles[i], n);
        }
        return newTiles;
    }

    // Helper method to swap two tiles in the tiles array
    private void swap(int[][] array, int row1, int col1, int row2, int col2) {
        int temp = array[row1][col1];
        array[row1][col1] = array[row2][col2];
        array[row2][col2] = temp;
    }

    // Is this board solvable?
    public boolean isSolvable() {
        int[] flattenedTiles = flattenTiles();

        // Count the number of inversions
        int inversions = countInversions(flattenedTiles);

        // If the board size is odd, the number of inversions must be even for solvability
        // If the board size is even, the sum of the row number of the blank tile and the number of inversions must have the same parity
        if (n % 2 == 1) {
            return inversions % 2 == 0;
        }
        else {
            int blankRow = findBlankRow();
            int blankRowFromBottom = n - blankRow;
            return (inversions + blankRowFromBottom) % 2 == 0;
        }
    }

    // Helper method to flatten the 2D array into a 1D array
    private int[] flattenTiles() {
        int[] flattenedTiles = new int[n * n];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                flattenedTiles[index++] = tiles[i][j];
            }
        }
        return flattenedTiles;
    }

    // Helper method to count the number of inversions
    private int countInversions(int[] array) {
        int inversions = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] != 0 && array[j] != 0 && array[i] > array[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    // Helper method to find the row number of the blank tile
    private int findBlankRow() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    return i + 1; // Rows are 1-indexed
                }
            }
        }
        return -1; // Should not reach here in a valid puzzle
    }

    // Testing (required)
    public static void main(String[] args) {
        // Example 3x3 puzzle
        int[][] puzzle1 = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };

        // Example 3x3 puzzle with an inversion for testing solvability
        int[][] puzzle2 = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 8, 7, 0 }
        };

        // Create instances of the Board class
        Board board1 = new Board(puzzle1);
        Board board2 = new Board(puzzle2);

        // Test methods
        System.out.println("Board 1:");
        System.out.println(board1);
        System.out.println("Hamming distance: " + board1.hamming());
        System.out.println("Manhattan distance: " + board1.manhattan());
        System.out.println("Is solvable: " + board1.isSolvable());
        System.out.println();

        System.out.println("Board 2:");
        System.out.println(board2);
        System.out.println("Hamming distance: " + board2.hamming());
        System.out.println("Manhattan distance: " + board2.manhattan());
        System.out.println("Is solvable: " + board2.isSolvable());
    }

}
