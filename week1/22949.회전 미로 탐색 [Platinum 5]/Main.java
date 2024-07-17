import java.io.*;
import java.util.*;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static char[][] matrix;
  static Node start, end;
  static int k;

  public static void main(String[] args) throws Exception {
    k = Integer.parseInt(br.readLine());

    matrix = new char[4 * k][4 * k];
    for (int i = 0; i < 4 * k; i++) {
      matrix[i] = br.readLine().toCharArray();
    }

    // 출발점과 도착점 저장
    for (int i = 0; i < 4 * k; i++) {
      for (int j = 0; j < 4 * k; j++) {
        if (matrix[i][j] == 'S') {
          start = new Node(i, j, 0, 0);
        }
        if (matrix[i][j] == 'E')
          end = new Node(i, j, 0, 0);
      }
    }

    // BFS 결과 출력
    System.out.println(BFS());
  }

  public static int BFS() {
    int[][] deltas = { { 0, 0 }, { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    Queue<Node> q = new LinkedList<>();
    q.add(start);

    boolean[][][] visited = new boolean[4 * k][4 * k][4];
    visited[start.x][start.y][0] = true;

    while (!q.isEmpty()) {
      Node cur = q.poll();

      // 도착점 도착
      if (end.x == cur.x && end.y == cur.y) {
        return cur.t;
      }

      // 현재의 구역을 확인하기 위한 변수
      int sectionX = cur.x / 4;
      int sectionY = cur.y / 4;

      for (int[] d : deltas) {
        int x = cur.x + d[0];
        int y = cur.y + d[1];
        int r = (cur.r + 1) % 4;

        // 현재의 구역을 벗어났다면 x, y 값 재조정, r은 언제나 1이 됨
        if (x < 0 || y < 0 || x / 4 != sectionX || y / 4 != sectionY) {
          int[] newXY = convert(cur.x, cur.y, cur.r, d);
          x = newXY[0];
          y = newXY[1];
          r = 1;
        }

        // 보정된 x, y를 통해서 미로를 벗어나는지 확인
        if (x < 0 || y < 0 || x >= 4 * k || y >= 4 * k)
          continue;
        if (matrix[x][y] == '#')
          continue;
        if (visited[x][y][r])
          continue;
        visited[x][y][r] = true;
        q.add(new Node(x, y, r, cur.t + 1));
      }
    }

    return -1;
  }

  /*
   * 각 구역 회전에 따른 x, y 보정값을 위한 메서드
   */
  public static int[] convert(int x, int y, int r, int[] d) {
    int innerX = x % 4;
    int innerY = y % 4;

    switch (r) {
      case 0:
        return new int[] { x + d[0], y + d[1] };
      case 1:
        return new int[] { x - innerX + innerY + d[1], y - innerY + 3 - innerX - d[0] };
      case 2:
        return new int[] { x - innerX + 3 - innerX - d[0], y - innerY + 3 - innerY - d[1] };
      case 3:
        return new int[] { x - innerX + 3 - innerY - d[1], y - innerY + innerX + d[0] };
    }
    return null;
  }

  /*
   * r : 해당 구역 회전량
   * 0 -> 원상태
   * 1 -> 90도 회전
   * 2 -> 180도 회전
   * 3 -> 270도 회전
   */
  public static class Node {
    int x, y, r, t;

    public Node(int x, int y, int r, int t) {
      this.x = x;
      this.y = y;
      this.r = r;
      this.t = t;
    }
  }
}