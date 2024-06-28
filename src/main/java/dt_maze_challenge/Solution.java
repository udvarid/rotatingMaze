package dt_maze_challenge;

import dt_maze_challenge.action.ActionMaker;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.solver.Solver;
import dt_maze_challenge.xmlHandler.FileReader;
import dt_maze_challenge.xmlHandler.XmlProcessor;
import dt_maze_challenge.xmlHandler.XmlWriter;

public class Solution {
	public static void main(String[] args) {
		String mazeXml = args.length > 0 ? args[0] : FileReader.readFromFile("example_maze_1.txt");
		Maze maze = XmlProcessor.processXml(mazeXml);

		var solver = new Solver();
		solver.solve(maze);

		maze.showMaze();

		var actionSet = ActionMaker.makeActionSet(maze);
		String result = XmlWriter.writeXml(actionSet);
		System.out.println(result);

	}
}