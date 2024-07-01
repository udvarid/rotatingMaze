package dt_maze_challenge;

import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.solver.Solver;
import dt_maze_challenge.xmlHandler.FileReader;
import dt_maze_challenge.xmlHandler.XmlProcessorEasy;
import dt_maze_challenge.xmlHandler.XmlWriterEasy;

public class Solution {
	public static void main(String[] args) {
		String testFileName = "example_maze_3.txt";
		String mazeXml = args.length > 0 ? args[0] : FileReader.readFromFile(testFileName);
		long st = System.currentTimeMillis();
		mazeXml = mazeXml.replaceAll("\r","")
				.replaceAll("\n","")
				.replaceAll("\t","")
				.replaceAll(" ","");
		Maze maze = XmlProcessorEasy.processXml(mazeXml);
		ActionSet actionSet = (new Solver()).solve(maze);
		System.out.println(XmlWriterEasy.writeXml(actionSet));
		long end = System.currentTimeMillis();
		System.out.println("Full time: " + (end - st));
	}
}