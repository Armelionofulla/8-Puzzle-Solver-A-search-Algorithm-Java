import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final int moves;
    private final Iterable<Board> solutionSteps;

    // Constructor
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null");
        }

        // Initialize priority queue for the initial board
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, null, 0));

        // A* algorithm
        while (!pq.isEmpty()) {
            SearchNode minNode = pq.delMin();

            // Check if the goal is reached
            if (minNode.board.isGoal()) {
                moves = minNode.moves;
                solutionSteps = buildSolution(minNode);
                return;
            }

            // Insert neighboring boards into the priority queue
            for (Board neighbor : minNode.board.neighbors()) {
                // Avoid enqueueing the same board as the previous board
                if (minNode.previous == null || !neighbor.equals(minNode.previous.board)) {
                    pq.insert(new SearchNode(neighbor, minNode, minNode.moves + 1));
                }
            }
        }

        // If the loop exits and the goal is not reached, the puzzle is unsolvable
        throw new IllegalArgumentException("Puzzle is unsolvable");
    }

    // Helper method to build the solution steps
    private Iterable<Board> buildSolution(SearchNode node) {
        Stack<Board> solution = new Stack<>();
        while (node != null) {
            solution.push(node.board);
            node = node.previous;
        }
        return solution;
    }

    // Minimum number of moves to solve the initial board
    public int moves() {
        return moves;
    }

    // Sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solutionSteps;
    }

    // Helper class to represent search nodes
    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previous;
        private final int moves;
        private final int priority;

        // Constructor
        public SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }

        // Compare nodes based on their priority
        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example 3x3 puzzle
        int[][] puzzle = {
                { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }
        };

        // Create an instance of the Solver class
        Board initialBoard = new Board(puzzle);
        Solver solver = new Solver(initialBoard);

        // Print the results
        System.out.println("Minimum number of moves: " + solver.moves());
        System.out.println("Solution steps:");
        for (Board board : solver.solution()) {
            System.out.println(board);
        }
    }
}