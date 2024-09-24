package pathfinding;

import java.io.*;
import java.util.*;

import com.application.GamePanel;
import org.w3c.dom.Node;

import static java.lang.Character.getNumericValue;

public class Pathfinding {

static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
static String mapLine;
static int i;

public static List<TileNode> pathfinding(TileNode start, TileNode goal) throws FileNotFoundException {
    i = 0;
    Scanner scanner = new Scanner(new InputStreamReader(Pathfinding.class.getResourceAsStream("/maps/map.txt")));
    int[][] map = new int[30][30];
    while (scanner.hasNextLine()) {
        mapLine = scanner.nextLine();
        mapLine = mapLine.replace(" ", "");
        for (int j = 0; j < mapLine.length(); j++) {
            map[j][i] = getNumericValue(mapLine.charAt(j));
        }
        i++;
    }
    scanner.close();

    PriorityQueue<TileNode> openList = new PriorityQueue<>();
    Set<TileNode> closedList = new HashSet<>();

    openList.add(start);

    while (!openList.isEmpty()) {
        TileNode current = openList.poll();

        if (current.equals(goal)) {
            return reconstructPath(current);
        }

        closedList.add(current);

        for (int[] direction : DIRECTIONS) {
            int newRow = current.row + direction[0];
            int newCol = current.col + direction[1];


            if (isInBounds(map, newRow, newCol) && map[newRow][newCol] == 0) {
                TileNode neighbor = new TileNode(newRow, newCol, current.g + 1, h(newRow, newCol, goal), current);

                if (closedList.contains(neighbor)) {
                    continue;
                }

                if (!openList.contains(neighbor)) {
                    openList.add(neighbor);
                }

            }


        }

    }

    return null;

}

    private static boolean isInBounds(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public static int h(int row, int col, TileNode goal) {
        return Math.abs(row - goal.row) + Math.abs(col - goal.col);
    }

    private static List<TileNode> reconstructPath(TileNode current) {
        List<TileNode> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parentNode;
        }
        Collections.reverse(path);
        return path;
    }


}
