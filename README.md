
# Slider Puzzle Solver
The Slider Puzzle Solver is a Java-based project designed for puzzle enthusiasts and developers alike. It provides an intelligent and efficient solution to the classic challenge of slider puzzles, offering a streamlined approach to solve these engaging tile rearrangement games.



## Additional Resources


[Slider Puzzle Overview](https://en.wikipedia.org/wiki/Sliding_puzzle): Learn more about the history and types of sliding puzzles.



## Puzzle File Format
The project expects puzzle files in a specific format. Each file should begin with an integer n representing the puzzle's dimension, followed by n lines, each containing n space-separated integers representing the puzzle's initial configuration.

3

1 2 3

4 5 6

7 8 0

## Dependencies
The project relies on the edu.princeton.cs.algs4 library for input and priority queue functionalities. Ensure that this library is available in your environment.

[edu.princeton.cs.algs4 documentation](https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/): Explore the documentation for additional details.
## Project Overview
**1-PuzzleChecker Class**

**main(String[] args)**

**Purpose:** The main method of the program. It prompts the user to enter a filename, reads the puzzle from the specified file, creates instances of Board and Solver, and prints the solution or an indication if no solution is possible.

**isValid(int row, int col)**

**Purpose:** A helper method to check if a given position is within the boundaries of the puzzle board.

**copyTiles()**

Purpose: A helper method to create a deep copy of the tiles array in the Board class.

**swap(int[][] array, int row1, int col1, int row2, int col2)**

**Purpose:** A helper method to swap two tiles in a 2D array.

**isSolvable()**

**Purpose:** Determines whether the current puzzle configuration is solvable. It checks the number of inversions and the parity of the blank tile's row.

**2-Board Class**

**hamming()**

**Purpose:** Computes the Hamming distance, which is the number of tiles out of place. It iterates through the tiles, counting those that are not in their goal positions.

**manhattan()**

**Purpose:** Computes the Manhattan distance, which is the sum of the distances between each tile and its goal position. It considers both horizontal and vertical distances.

**isGoal()**

**Purpose:** Checks if the current board configuration is the goal state, i.e., if the Hamming distance is 0.

**equals(Object y)**

**Purpose:** Overrides the equals method to compare two Board objects for equality based on their tiles arrays.

**neighbors()**

**Purpose:** Generates and returns all neighboring boards by swapping the blank tile with its neighboring tiles.

**isSolvable()**

**Purpose:** Determines whether the current puzzle is solvable using the inversion count and the parity of the blank tile's row.

**3-Solver Class**

**Solver(Board initial)**

**Purpose:** The constructor takes an initial board and uses the A* algorithm to find the solution. It creates a priority queue of search nodes, exploring neighboring boards until the goal is reached.

**moves()**

**Purpose:** Returns the minimum number of moves required to reach the goal state.

**solution()**


**Purpose:** Returns an iterable of boards representing the solution steps.

**SearchNode Class**

**Purpose:** A nested class representing search nodes in the A* algorithm. It contains information about the current board, the previous search node, the number of moves, and the priority for comparison.
## A* Search Algorithm
The A* algorithm is a widely used and efficient pathfinding algorithm that combines elements of Dijkstra's algorithm and greedy best-first search. It is particularly effective for solving puzzles and finding the optimal path from a start state to a goal state. The algorithm uses a heuristic function to estimate the cost of reaching the goal from a given state, guiding the search towards the most promising paths.

**Overview**

The A* algorithm maintains a priority queue of search nodes, prioritized by the sum of the cost to reach the node from the start (actual cost) and the heuristic estimate of the cost to reach the goal from the node (estimated cost). In the context of the Slider Puzzle Solver, a search node represents a specific board configuration.

**Implementation**

In the **Solver class**, the A* algorithm is implemented as follows:

**Priority Queue:** A MinPQ (minimum priority queue) is used to maintain the search nodes in ascending order of their priority, where priority is defined as the sum of the actual cost and the heuristic estimate.

**SearchNode Class:** The SearchNode class is a nested class that represents a search node in the A* algorithm. It contains information about the current board configuration, the previous search node, the number of moves made, and the priority.

**Heuristic Function:** The heuristic function used is the sum of the number of moves made and the Manhattan distance of the current board configuration from the goal state. The Manhattan distance is a measure of how far each tile is from its goal position, considering both horizontal and vertical distances.

**Constructor**

The A* algorithm is initiated in the **Solver class** constructor, taking the initial board configuration as a parameter. The constructor performs the following steps:

1-Initializes the priority queue with the initial search node.

2-Repeatedly dequeues the node with the minimum priority, enqueues its neighboring boards, and continues until the goal state is reached.

**Result**


Once the goal state is reached, the A* algorithm provides the following:

-**Minimum Moves:** The minimum number of moves required to reach the goal state.

-**Solution Steps:** The sequence of boards representing the optimal solution path.

**Usage**


To use the A* algorithm for solving a slider puzzle, instantiate the **Solver class** with the initial board configuration. Retrieve the minimum number of moves and the solution steps using the **moves()** and **solution()** methods, respectively.

```
Board initialBoard = new Board(initialTiles);
Solver solver = new Solver(initialBoard);

int minMoves = solver.moves();
Iterable<Board> solutionSteps = solver.solution();

```

The A* algorithm efficiently explores possible paths, ensuring an optimal solution is found.

**Considerations**

The efficiency of the A* algorithm relies heavily on the effectiveness of the heuristic function. In the context of the Slider Puzzle Solver, the Manhattan distance is a suitable heuristic for estimating the cost to reach the goal state.

A* guarantees an optimal solution if the heuristic function is admissible, i.e., it never overestimates the true cost to reach the goal.

This A* search algorithm provides a powerful and efficient method for solving slider puzzles, offering an optimal solution path in terms of the minimum number of moves.
