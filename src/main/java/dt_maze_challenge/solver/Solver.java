package dt_maze_challenge.solver;

import dt_maze_challenge.maze.CoordinateWithPrevious;
import dt_maze_challenge.maze.Maze;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Solver {
    private final SolverTypeOne solverTypeOne = new SolverTypeOne();
    public void solve(Maze maze) {
        switch (maze.getLevel()) {
            case 1 -> solverTypeOne.solve(maze);
            case 2 -> solverTypeOne.solve(maze);
            case 3 -> solverTypeOne.solve(maze);
            default -> System.out.println("Unhandled level");
        }

    }
}

class SolverTypeOne extends Solver {
    public void solve(Maze maze) {
        Set<CoordinateWithPrevious> visited = new HashSet<>();
        Queue<CoordinateWithPrevious> actual = new LinkedList<>();

        /*
        actual.offer();
        actual.poll();
        actual.isEmpty()
        actual.contains()
        */



        // Kiindulunk a start helyzetből

        // Megnézzük a start cella szabad szomszédait -> metódus a Maze-be
        // Betesszük őket a kiinduló 'zsákba', mint CoordinateWithPrevious Set, a previous a start

        // Végigmegyónk a start zsákon, minden elemet átteszünk a visited-be, majd megnézzük a szomszédos szabad helyeket (szabad + exit)
        // Amelyik szabad hely nem szerepel sem a visitedben, sem az actual-ben, azt betesszük az actualbe, a previous elem a mostani kiinduló

        // Akkor van vége, ha kiürült az actual vagy megtaláltuk az exit helyet. Exit helyet is betenni a visitedbe

        // Ha meg van az exit hely, akkor visszafelé megfejteni az utat és STEP jelölőt tenni oda
    }
}
