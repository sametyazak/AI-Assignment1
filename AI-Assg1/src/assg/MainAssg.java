package assg;

import java.util.ArrayList;

public class MainAssg {
	public static Boolean debugRes = false;
	public static int maxRes = 0;
	
	public static int BFSNodeCount = 0;
	public static int DFSNodeCount = 0;
	public static int UCSNodeCount = 0;
	public static int GBSNodeCount = 0;
	
	public static int IDSTotalNodeCount = 0;
	public static int IDSMemoryNodeCount = 0;
	
	public static int ASSTotalNodeCount = 0;
	public static int ASSMemoryNodeCount = 0;
	
	
	public static SearchTree DFSGoalNode = null;
	public static int DFSFailCount = 0;
	
	public static SearchTree AsGoalNode = null;
	public static SearchTree IDSGoalNode = null;
	
	
	
	public static void main(String[] args) {
		//TestBFS();
		//TestDFS();
		//TestUCS();
		//TestIDS();
		//TestGBS();
		TestASS();
	}
	
	public static void TestIDS() {
		int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
		//int[][] testValue = {{5,4,8},{3,1,6},{7,0,2}};
		
		SearchTree root = new SearchTree();
		//root.setValue(testValue);
		root.GenerateRandomValue(goal.length, goal.length);
		root.setLevel(0);
		
		root.PrintNodeValue();
		
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("down");
		directions.add("up");
		directions.add("right");
		directions.add("left");
		
		IteraticeDeepeningSearch(root, goal, 0, directions, 27);
		
		if (IDSGoalNode != null) {
			PrintGoalNode(IDSGoalNode);
		}
		else {
			System.out.println("ids search not successfull");
		}
	}
	
	
	public static void TestDFS() {
		int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
		int[][] testValue = {{5,4,8},{3,1,6},{7,0,2}};
		
		SearchTree root = new SearchTree();
		root.setValue(testValue);
		//root.GenerateRandomValue(goal.length, goal.length);
		root.setLevel(0);
		
		root.PrintNodeValue();
		
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("down");
		directions.add("up");
		directions.add("right");
		directions.add("left");
		
		DepthFirstSearch(root, goal, directions, 0);
		
		if (DFSGoalNode != null) {
			PrintGoalNode(DFSGoalNode);
		}
		else {
			System.out.println("dfs search not successfull");
		}
	}
	
	public static void TestUCS() {
		int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
		//int[][] testValue = {{5,4,8},{3,1,6},{7,0,2}};
		
		SearchTree root = new SearchTree();
		//root.setValue(testValue);
		root.GenerateRandomValue(goal.length, goal.length);
		root.PrintNodeValue();
		
		ArrayList<SearchTree> rootFrontier = new ArrayList<SearchTree>();
		rootFrontier.add(root);
		
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("down");
		directions.add("up");
		directions.add("right");
		directions.add("left");
		
		SearchTree goalNode = UniformCostSearch(rootFrontier, goal, directions, 0);
		PrintGoalNode(goalNode);
	}
	
	public static void TestBFS() {
		int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
		//int[][] testValue = {{5,4,8},{3,1,6},{7,0,2}};
		
		SearchTree root = new SearchTree();
		//root.setValue(testValue);
		root.GenerateRandomValue(goal.length, goal.length);
		root.PrintNodeValue();
		
		ArrayList<SearchTree> rootFrontier = new ArrayList<SearchTree>();
		rootFrontier.add(root);
		
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("down");
		directions.add("up");
		directions.add("right");
		directions.add("left");
		
		SearchTree goalNode = BreadthFirstSearch(rootFrontier, goal, directions, 0);
		PrintGoalNode(goalNode);
	}
	
	public static void TestGBS() {
		int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
		//int[][] testValue = {{5,4,8},{3,1,6},{7,0,2}};
		
		SearchTree root = new SearchTree();
		//root.setValue(testValue);
		root.GenerateRandomValue(goal.length, goal.length);
		root.PrintNodeValue();
		
		ArrayList<SearchTree> rootFrontier = new ArrayList<SearchTree>();
		rootFrontier.add(root);
		
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("down");
		directions.add("up");
		directions.add("right");
		directions.add("left");
		
		SearchTree goalNode = GreedyBestSearch(rootFrontier, goal, directions, 0);
		PrintGoalNode(goalNode);
	}
	
	public static void TestASS() {
		int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
		//int[][] testValue = {{1,6,8},{3,5,0},{2,4,7}};
		
		SearchTree root = new SearchTree();
		//root.setValue(testValue);
		root.GenerateRandomValue(goal.length, goal.length);
		root.PrintNodeValue();
		
		ArrayList<SearchTree> rootFrontier = new ArrayList<SearchTree>();
		rootFrontier.add(root);
		
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("down");
		directions.add("up");
		directions.add("right");
		directions.add("left");
		
		AStarSearch(rootFrontier, goal, directions, 0);
		PrintGoalNode(AsGoalNode);
	}
	
	public static void PrintGoalNode(SearchTree node) {
		if (node != null) {
			PrintGoalNode(node.getParent());
			System.out.println();
			node.PrintNodeValue();
		}
		else {
			System.out.println("node count bfs:" + BFSNodeCount);
			System.out.println("node count dfs:" + DFSNodeCount);
			System.out.println("node count ucs:" + UCSNodeCount);
			System.out.println("node count ids memory:" + IDSMemoryNodeCount);
			System.out.println("node count ids total:" + IDSTotalNodeCount);
			System.out.println("node count gbs:" + GBSNodeCount);
			
			System.out.println("node count ass memory:" + ASSMemoryNodeCount);
			System.out.println("node count ass total:" + ASSTotalNodeCount);
		}
	}
	
	public static SearchTree BreadthFirstSearch(ArrayList<SearchTree> input, int[][] goal, ArrayList<String> directions, int level) {
		System.out.println("level=" + level);
		ArrayList<SearchTree> frontierList = new ArrayList<SearchTree>();
		
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).IsGoal(goal)){
				return input.get(i);
			}
			else {
				ArrayList<SearchTree> expandedNodes = input.get(i).ExpandNodes(directions);
				frontierList.addAll(expandedNodes);
			}
		}
		
		System.out.println("frontier=" + frontierList.size());
		BFSNodeCount += frontierList.size();
		
		SearchTree goalNode = GoalExists(frontierList, goal);
		if (goalNode != null) {
			return goalNode;
		}
		else {
			return BreadthFirstSearch(frontierList, goal, directions, ++level);
		}
		
	}
	
	public static SearchTree UniformCostSearch(ArrayList<SearchTree> input, int[][] goal, ArrayList<String> directions, int level) {
		System.out.println("level=" + level);
		ArrayList<SearchTree> frontierList = new ArrayList<SearchTree>();
		
		SearchTree goalNode = null;
		
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).IsGoal(goal)){
				return input.get(i);
			}
			else {
				ArrayList<SearchTree> expandedNodes = input.get(i).ExpandNodes(directions);
				UCSNodeCount += expandedNodes.size();
				frontierList.addAll(expandedNodes);
				
				goalNode = GoalExists(expandedNodes, goal);
				if (goalNode != null) {
					break;
				}
			}
		}
		
		if (goalNode != null) {
			return goalNode;
		}
		else {
			return UniformCostSearch(frontierList, goal, directions, ++level);
		}
		
	}
	
	public static void DepthFirstSearch(SearchTree input, int[][] goal, ArrayList<String> directions, int level) {
		//System.out.println("level=" + level);
		
		if (input.IsGoal(goal)){
			DFSGoalNode = input;
		}
		else {
			ArrayList<SearchTree> expandedNodes = input.ExpandNodes(directions);
			int expandSize = expandedNodes.size();
			
			if (expandSize <= 0) {
				//System.out.println("no expand");
				//System.out.println("level=" + level);
				//PrintGoalNode(input);
				DFSFailCount++;
				
				if (DFSFailCount == 2) {
					System.out.println("stop");
				}
				
				return;
			}
			
			++level;
			DFSNodeCount += expandSize;
			
			for (int i = 0; i < expandedNodes.size(); i++) {
				if (DFSGoalNode == null) {
					DepthFirstSearch(expandedNodes.get(i), goal, directions, level);
				}
			}
		}
		
	}
	
	public static void IteraticeDeepeningSearch(SearchTree root, int[][] goal, int depth, ArrayList<String> directions, int maxDepth) {
		while (IDSGoalNode == null && depth < maxDepth) {
			DLS(root, goal, depth, directions);
			depth++;
			
			if (IDSGoalNode == null) {
				IDSMemoryNodeCount = 0;
			}
			
			System.out.println("depth:" + depth);
		}
	}
	
	public static void DLS(SearchTree node, int[][] goal, int depth, ArrayList<String> directions) {
		if (depth == 0 && node.IsGoal(goal)) {
			IDSGoalNode = node;
			return;
		}
		else if (depth > 0) {
			ArrayList<SearchTree> expandedNodes = node.ExpandNodes(directions);
			IDSMemoryNodeCount += expandedNodes.size();
			IDSTotalNodeCount += expandedNodes.size();
			
			for (int i = 0; i < expandedNodes.size(); i++) {
				if (IDSGoalNode != null) {
					return;
				}
				
				DLS(expandedNodes.get(i), goal, depth - 1, directions);
			}
		}
	}
	
	public static SearchTree GreedyBestSearch(ArrayList<SearchTree> input, int[][] goal, ArrayList<String> directions, int level) {
		System.out.println("level=" + level);
		ArrayList<SearchTree> frontierList = new ArrayList<SearchTree>();
		
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).IsGoal(goal)){
				return input.get(i);
			}
			else {
				ArrayList<SearchTree> expandedNodes = input.get(i).ExpandNodesWithManhattan(directions, goal);
				frontierList.addAll(expandedNodes);
			}
		}
		
		if (frontierList.size() == 0) {
			return null;
		}
		
		System.out.println("frontier=" + frontierList.size());
		GBSNodeCount += frontierList.size();
		
		SearchTree goalNode = GoalExists(frontierList, goal);
		if (goalNode != null) {
			return goalNode;
		}
		else {
			return GreedyBestSearch(frontierList, goal, directions, ++level);
		}
	}
	
	public static void AStarSearch(ArrayList<SearchTree> input, int[][] goal, ArrayList<String> directions, int level) {
		if (AsGoalNode != null) {
			return;
		}
		
		//System.out.println("level=" + level);
		
		SearchTree bestNode = null;
		int bestScore = 0;
		
		for (int i = 0; i < input.size(); i++) {
			SearchTree currentNode = input.get(i);
			
			int manhanntan = currentNode.GetManhattanDistance(goal);
			int score = manhanntan + currentNode.getLevel();
			
			if ((bestScore == 0 || score < bestScore) && !currentNode.getIsUsed()) {
				bestNode = currentNode;
				bestScore = score;
			}
		}
		
		if (bestNode != null) {
			bestNode.setIsUsed(true);
			ArrayList<SearchTree> expandedNodes = bestNode.ExpandNodes(directions);
			ASSTotalNodeCount += expandedNodes.size();
			input.remove(bestNode);
			input.addAll(expandedNodes);
		}
		
		if (input.size() == 0) {
			return;
		}
		
		//System.out.println("frontier=" + input.size());
		
		SearchTree goalNode = GoalExists(input, goal);
		if (goalNode != null) {
			AsGoalNode = goalNode;
			ASSMemoryNodeCount = input.size();
			return;
		}
		else {
			AStarSearch(input, goal, directions, ++level);
		}
	}
	
	public static SearchTree GoalExists(ArrayList<SearchTree> nodeList, int[][] goal) {
		for (int i = 0; i < nodeList.size(); i++) {
			SearchTree currentNode = nodeList.get(i);
			
			if (currentNode.IsGoal(goal)) {
				currentNode.setIsGoal(true);
				return currentNode;
			}
			else if (debugRes) {
				int resemb = currentNode.GetResemblance(goal);
				
				if (resemb > maxRes) {
					System.out.println("max resemb=" + resemb);
					maxRes = resemb;
					currentNode.PrintNodeValue();
				}
			}
		}
		
		return null;
	}
}
