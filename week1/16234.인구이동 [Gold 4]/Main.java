import java.io.*;
import java.util.*;

public class Main {

  public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public static boolean[][] visited;
  public static int[][] matrix;
  public static int N, L, R;

  public static ArrayList<ArrayList<Node>> union;

  public static void main(String[] args) throws IOException {
    N = read();
    L = read();
    R = read();

    //2차원 배열에 저장
    matrix = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        matrix[i][j] = read();
      }
    }

    //인구이동 횟수 -> 이하 while문 돌아가는 횟수 
    int count = 0;

    //사람이동이 존재하면 계속 진행
    while (true) {
      //연합한 마을들을 리스트로 저장 -> `1국 연합 포함
      union = new ArrayList<>();
      visited = new boolean[N][N];

      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          BFS(new Node(i, j));
        }
      }

      if (movePeople())
        break;
      else
        count++;
    }

    //출력
    System.out.println(count);
  }

  //BFS -> visited가 false이면 Queue에 들어간 적이 없으므로 아직 연합이 없음
  public static void BFS(Node start) {
    //연합에 들어간 국가면 BFS 종료
    if (visited[start.x][start.y])
      return;

    int[][] deltas = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    Queue<Node> q = new LinkedList<>();
    union.add(new ArrayList<>());

    //연합에 저장 및 BFS실행
    q.add(start);
    union.get(union.size() - 1).add(start);
    visited[start.x][start.y] = true;

    while (!q.isEmpty()) {
      Node node = q.poll();

      for (int[] d : deltas) {
        int x = node.x + d[0];
        int y = node.y + d[1];

        if (x < 0 || x >= N || y < 0 || y >= N)
          continue;
        if (visited[x][y])
          continue;

        //인구수 차이 확인
        int diff = Math.abs(matrix[node.x][node.y] - matrix[x][y]);
        if (diff < L || diff > R)
          continue;

        visited[x][y] = true;
        Node unionNode = new Node(x, y);
        q.add(unionNode);
        union.get(union.size() - 1).add(unionNode);
      }
    }
  }

  //인구이동
  public static boolean movePeople() {
    //인구이동 여부 확인
    boolean finish = true;

    for (ArrayList<Node> nodes : union) {
      //단일 국가는 인구이동이 없음
      if (nodes.size() == 1)
        continue;

      finish = false;
      int sum = 0;

      for (Node node : nodes) {
        sum += matrix[node.x][node.y];
      }
      sum /= nodes.size();

      //인구이동
      for (Node node : nodes) {
        matrix[node.x][node.y] = sum;
      }
    }

    return finish;
  }

  public static class Node {
    int x, y;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  public static int read() throws IOException {
    int res = 0;
    while (true) {
      int r = br.read();
      if (r == 10 || r == 32)
        return res;
      if (r == 13)
        continue;
      res = 10 * res + r - 48;
    }
  }
}
