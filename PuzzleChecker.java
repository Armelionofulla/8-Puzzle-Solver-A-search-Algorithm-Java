import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Scanner;

public class PuzzleChecker {

    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the filename
        System.out.print("Enter the filename (without extension): ");
        String filenameWithoutExtension = scanner.nextLine();

        // Close the Scanner to avoid resource leaks
        scanner.close();

        // Append ".txt" extension to the filename
        String filename = "puzzles/" + filenameWithoutExtension + ".txt";

        // Read in the Board specified in the filename
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!initial.isSolvable()) StdOut.println("No solution possible");
        else {
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
        }
    }
}
