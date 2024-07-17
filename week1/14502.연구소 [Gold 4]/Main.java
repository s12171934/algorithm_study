import java.io.*;
import java.util.*;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static ArrayList<Node> newWall = new ArrayList<>();
  static ArrayList<Node> virus = new ArrayList<>();

  public static void main(String[] args) throws Exception {
    int n = read();
    int m = read();

    /**
     * newWall -> 벽을 세로 세울 수 있는 빈 공간
     * virus -> 바이러스 위치 파악
     */
    int[][] matrix = new int[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        matrix[i][j] = read();
        if (matrix[i][j] == 0) {
          newWall.add(new Node(i, j));
        }
        if (matrix[i][j] == 2) {
          virus.add(new Node(i, j));
        }
      }
    }

    int max = 0;

    // 부르트포스로 모든 벽을 세우고 바이러스 확산 시뮬레이션
    for (int i = 0; i < newWall.size(); i++) {
      for (int j = i + 1; j < newWall.size(); j++) {
        for (int k = j + 1; k < newWall.size(); k++) {
          int[][] newMatrix = matrixClone(matrix, i, j, k);
          // (기존 빈구역의 갯수) - (새롭게 설치된 벽) - (확산된 바이러스의 수)
          max = Math.max(max, newWall.size() - 3 - BFS(newMatrix));
        }
      }
    }

    System.out.println(max);
  }

  // 기존 matrix를 계속 재활용하기 위한 깊은 복사 생성
  public static int[][] matrixClone(int[][] matrix, int i, int j, int k) {
    int[][] newMatrix = new int[matrix.length][matrix[0].length];
    for (int row = 0; row < matrix.length; row++) {
      newMatrix[row] = matrix[row].clone();
    }

    newMatrix[newWall.get(i).x][newWall.get(i).y] = 1;
    newMatrix[newWall.get(j).x][newWall.get(j).y] = 1;
    newMatrix[newWall.get(k).x][newWall.get(k).y] = 1;

    return newMatrix;
  }

  // 추가되는 바이러스의 수를 Return함
  public static int BFS(int[][] matrix) {
    int count = 0;
    int[][] deltas = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    Queue<Node> q = new LinkedList<>(virus);

    while (!q.isEmpty()) {
      Node node = q.poll();

      for (int[] d : deltas) {
        int x = node.x + d[0];
        int y = node.y + d[1];

        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length)
          continue;
        if (matrix[x][y] != 0)
          continue;
        matrix[x][y] = 2;
        count++;

        q.add(new Node(x, y));
      }
    }

    return count;
  }

  public static class Node {
    int x, y;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  // Int 입력을 위한 메서드
  public static int read() throws Exception {
    int res = 0;
    while (true) {
      int r = br.read();
      if (r == 10 || r == 32)
        return res;
      res = 10 * res + r - 48;
    }
  }
}