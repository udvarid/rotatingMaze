package dt_maze_challenge;

import dt_maze_challenge.action.Rotate;
import dt_maze_challenge.action.Step;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.xmlHandler.FileReader;
import dt_maze_challenge.xmlHandler.XmlProcessor;
import dt_maze_challenge.xmlHandler.XmlWriter;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	public static void main(String[] args) {
		String mazeXml = args.length > 0 ? args[0] : FileReader.readFromFile("example_maze_1.txt");
		Maze maze = XmlProcessor.processXml(mazeXml);

		maze.showMaze();

		List<Step> steps = new ArrayList<>();
		steps.add(new Step(1,2));
		steps.add(new Step(3,4));

		List<Rotate> rotates = new ArrayList<>();
		rotates.add(new Rotate(1,2));
		rotates.add(new Rotate(7,2));

		String result = XmlWriter.writeXml(steps, rotates);

		System.out.println("----------------------");
		System.out.println(result);

		String actionsXml = "";	
		System.out.println(actionsXml);
	}
}