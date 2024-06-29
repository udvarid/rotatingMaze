package dt_maze_challenge;

import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.solver.Solver;
import dt_maze_challenge.xmlHandler.FileReader;
import dt_maze_challenge.xmlHandler.XmlProcessor;
import dt_maze_challenge.xmlHandler.XmlWriter;

public class Solution {
	public static void main(String[] args) {
		var testFileName = "example_maze_3.txt";
		String mazeXml = args.length > 0 ? args[0] : FileReader.readFromFile(testFileName);
		Maze maze = XmlProcessor.processXml(mazeXml);

		var solver = new Solver();
		var actionSet = solver.solve(maze);

		String result = XmlWriter.writeXml(actionSet);
		System.out.println(result);
	}
}