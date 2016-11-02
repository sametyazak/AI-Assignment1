package assg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTree {
	private int[][] nodeValue;
	
	private ArrayList<SearchTree> childNodes;
	
	private SearchTree parent;
	
	private Boolean isGoal;
	
	private int level;
	
	private int manhattanDistance;
	
	private boolean isUsed;
	
	public boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Boolean getIsGoal() {
		return isGoal;
	}

	public void setIsGoal(Boolean isGoal) {
		this.isGoal = isGoal;
	}

	public int[][] getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(int[][] nodeValue) {
		this.nodeValue = nodeValue;
	}

	public ArrayList<SearchTree> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(ArrayList<SearchTree> childNodes) {
		this.childNodes = childNodes;
	}

	public SearchTree getParent() {
		return parent;
	}

	public void setParent(SearchTree parent) {
		this.parent = parent;
	}

	public int[][] getValue() {
		return nodeValue;
	}

	public void setValue(int[][] value) {
		nodeValue = value;
	}
	
	public void InsertChildNode(SearchTree node) {
		if (this.childNodes == null) {
			this.childNodes = new ArrayList<SearchTree>();
		}
		
		this.childNodes.add(node);
	}
	
	public Boolean IsGoal(int[][] goal) {
		return Arrays.deepEquals(this.nodeValue, goal);
	}
	
	public int GetResemblance(int[][] goal) {
		int resemblance = 0;
		
		for(int i = 0; i < this.nodeValue.length; i++) {
			for(int j = 0; j < this.nodeValue[i].length; j++) {
				if (this.nodeValue[i][j] == goal[i][j]) {
					resemblance++;
				}
			}
		}
		
		return resemblance;
	}
	
	public void GenerateRandomValue(int iLength, int jLength) {
		int[][] innerNode = new int[iLength][jLength];
		ArrayList<Integer> validNumbers = GetValidNumbers(iLength*jLength);
		
		for (int i = 0; i < iLength; i++) {
			for (int j = 0; j < jLength; j++) {
				innerNode[i][j] = (int) validNumbers.get(i*iLength + j);
			}
		}
		
		this.nodeValue = innerNode;
	}
	
	public ArrayList<Integer> GetValidNumbers(int length) {
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < length; i++) {
            list.add(new Integer(i));
        }
        
        Collections.shuffle(list);
        return list;
	}
	
	private void PrintNodeRecursive(SearchTree node, int level, int index) {
		int[][] innerValue = node.getNodeValue();
		
		System.out.println("d=" + level + ", index=" + index + ", isgoal=" + node.getIsGoal());
		for (int i = 0; i < innerValue.length; i++) {
			System.out.println(Arrays.toString(innerValue[i]));
		}
		
		List<SearchTree> innerChilNodes = node.getChildNodes();
		
		if (innerChilNodes != null) {
			level++;
			for (int i = 0; i < innerChilNodes.size(); i++) {
				PrintNodeRecursive(innerChilNodes.get(i), level, i);
			}
		}
	}
	
	public void PrintNodeValue() {
		System.out.println(this.level + ".level");
		for (int i = 0; i < this.nodeValue.length; i++) {
			System.out.println(Arrays.toString(this.nodeValue[i]));
		}
	}
	
	public void PrintAllNodes() {
		PrintNodeRecursive(this, 0, 0);
	}
	
	public Map<String, Integer> GetCellIndex(int cellValue) {
		
		Map<String, Integer> emptyCell = new HashMap<String, Integer>(); 
		
			for (int i = 0; i < this.nodeValue.length; i++) {
				for (int j = 0; j < this.nodeValue[i].length; j++) {
					if (this.nodeValue[i][j] == cellValue) {
						emptyCell.put("x", i);
						emptyCell.put("y", j);
						break;
						
					}
				}
			}
		
		return emptyCell;
	}
	
	public Map<String, Boolean> GetDirections() {
		Map<String, Boolean> directions = new HashMap<String, Boolean>();
		Map<String, Integer> emptyCell = this.GetCellIndex(0);
		
		int x = emptyCell.get("x");
		int y = emptyCell.get("y");
		
		directions.put("up", x > 0 && x < this.nodeValue.length);
		directions.put("down", x >= 0 && x < this.nodeValue.length - 1);
		directions.put("left", y > 0 && y < this.nodeValue[y].length);
		directions.put("right", y >= 0 && y < this.nodeValue[y].length - 1);
		
		return directions;
	}
	
	public SearchTree MoveEmptyCell(String direction) {
		Map<String, Integer> emptyCell = this.GetCellIndex(0);
		
		int x = emptyCell.get("x");
		int y = emptyCell.get("y");
		
		int newX = x;
		int newY = y;
		
		switch (direction){
			case "up":
				newX = x -1;
				break;
			case "down":
				newX = x + 1;
				break;
			case "left":
				newY = y -1;
				break;
			case "right":
				newY = y + 1;
				break;
		}
		
		int[][] swapValue = this.SwapCell(x, y, newX, newY);
		
		SearchTree swapTree = new SearchTree();
		swapTree.setNodeValue(swapValue);
		
		return swapTree;
	}
	
	public int[][] deepCopyValue(int[][] original) {
	    if (original == null) {
	        return null;
	    }

	    final int[][] result = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	    }
	    return result;
	}
	
	public int[][] SwapCell(int x1, int y1, int x2, int y2) {
		int[][] swapValue = this.deepCopyValue(this.nodeValue);
		
		int oldValue = swapValue[x1][y1];
		int newValue = swapValue[x2][y2];
		
		swapValue[x2][y2] = oldValue;
		swapValue[x1][y1] = newValue;
		
		return swapValue;
	}
	
	public ArrayList<SearchTree> ExpandNodes(ArrayList<String> directions) {
		ArrayList<SearchTree> expandedNodes = new ArrayList<SearchTree>();
		
		Map<String, Boolean> validDirections = this.GetDirections();
		
		for (int i = 0; i < directions.size(); i++) {
			String direction = directions.get(i);
			
			if (validDirections.get(direction)) {
				SearchTree node = this.MoveEmptyCell(direction);
				node.parent = this;
				node.level = this.level + 1;
				node.isUsed = false;
				
				if (!CheckDuplicate(node))
				{
					expandedNodes.add(node);
				}
			}
		}
		
		this.childNodes = expandedNodes;
		return expandedNodes;
	}
	
	public ArrayList<SearchTree> ExpandNodesWithManhattan(ArrayList<String> directions, int[][] goal) {
		ArrayList<SearchTree> expandedNodes = new ArrayList<SearchTree>();
		Map<String, Boolean> validDirections = this.GetDirections();
		
		int lowestManhattan = 0;
		
		for (int i = 0; i < directions.size(); i++) {
			String direction = directions.get(i);
			
			if (validDirections.get(direction)) {
				SearchTree node = this.MoveEmptyCell(direction);
				node.parent = this;
				node.level = this.level + 1;
				
				if (!CheckDuplicate(node))
				{
					int getCurrentManhattan = node.GetManhattanDistance(goal);
					if (lowestManhattan == 0 || getCurrentManhattan < lowestManhattan)
					{
						lowestManhattan = getCurrentManhattan;
					}
					expandedNodes.add(node);
				}
			}
		}
		
		this.childNodes = new ArrayList<SearchTree>();
		
		for (int i = 0; i < expandedNodes.size(); i++) {
			if (expandedNodes.get(i).manhattanDistance == lowestManhattan) {
				this.childNodes.add(expandedNodes.get(i));
				break;
			}
		}
		
		return this.childNodes;
	}
	
	public int GetManhattanDistance(int[][] goal) {
		int manhattan = 0;
		for (int i = 0; i < goal.length; i++) {
			for (int j = 0; j < goal[i].length; j++) {
				if (goal[i][j] != 0) {
				
					Map<String, Integer> cellIndex = this.GetCellIndex(goal[i][j]);
					
					int x = cellIndex.get("x");
					int y = cellIndex.get("y");
					
					manhattan += Math.abs(x - i);
					manhattan += Math.abs(j - y);
				}
			}
		}
		
		this.manhattanDistance = manhattan;
		return manhattan;
	}
	
	public Boolean CheckDuplicate(SearchTree node) {
		boolean retValue = false;
		SearchTree checkNode = node;

		while (node.getParent() != null && !retValue)
		{
			if (Arrays.deepEquals(node.getParent().nodeValue, checkNode.nodeValue))
			{
				retValue = true;
			}
			
			node = node.getParent();
		}

		return retValue;
	}
	
	public Boolean IsAnyChildGoal(int[][] goal) {
		for (int i = 0; i < this.childNodes.size(); i++) {
			if (this.childNodes.get(i).IsGoal(goal)) {
				this.childNodes.get(i).setIsGoal(true);
				return true;
			}
		}
		
		return false;
	}
}
