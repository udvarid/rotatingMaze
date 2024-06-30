package dt_maze_challenge.solver.helper;

import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.maze.MazeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SolverUtil {
    private SolverUtil() {}

    public static Map<Integer, Set<String>> fillUpPermutations() {
        Map<Integer, Set<String>> permutations = new HashMap<>();
        for (int i1 = 0; i1 <= 3; i1++) {
            for (int i2 = 0; i2 <= 3; i2++) {
                for (int i3 = 0; i3 <= 3; i3++) {
                    for (int i4 = 0; i4 <= 3; i4++) {
                        for (int i5 = 0; i5 <= 3; i5++) {
                            for (int i6 = 0; i6 <= 3; i6++) {
                                for (int i7 = 0; i7 <= 3; i7++) {
                                    for (int i8 = 0; i8 <= 3; i8++) {
                                        for (int i9 = 0; i9 <= 3; i9++) {
                                            var swv = createPermutationStringWithValue(Arrays.asList(i1, i2, i3, i4, i5, i6, i7, i8, i9));
                                            if (permutations.containsKey(swv.getValue())) {
                                                permutations.get(swv.value).add(swv.getCombination());
                                            } else {
                                                var set = new HashSet<String>();
                                                set.add(swv.combination);
                                                permutations.put(swv.value, set);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return permutations;
    }

    public static StringWithValue createPermutationStringWithValue(List<Integer> numbers) {
        StringBuilder sb = new StringBuilder();
        int value = 0;
        for (Integer number : numbers) {
            sb.append(number);
            value += getNumberOfRotationValue(number);
        }
        return new StringWithValue(sb.toString(), value);
    }

    static int getNumberOfRotationValue(int number) {
        return switch (number) {
            case 1, 2 -> 1;
            case 3 -> 2;
            default -> 0;
        };
    }

    public static int calculateMinimumSteps(Maze maze) {
        var start = maze.getStart();
        var end = maze.getEnd();
        return Math.abs(start.x()- end.x()) + Math.abs(start.y() - end.y());
    }

    public static MazeType[][] rotateLeft(MazeType[][] tiny) {
        MazeType[][] puffer = new MazeType[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                puffer[4 - j][i] = tiny[i][j];
            }
        }
        return puffer;
    }

    public static MazeType[][] rotateRight(MazeType[][] tiny) {
        MazeType[][] puffer = new MazeType[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                puffer[j][4 - i] = tiny[i][j];
            }
        }
        return puffer;
    }
}
