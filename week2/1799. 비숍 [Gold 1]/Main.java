import java.io.*;
import java.util.*;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static int[][] bishop = { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };

  static ArrayList<int[]> list;
  static int N, max;

  public static void main(String[] args) throws IOException {
    N = read();
    // 흑백의 가능한 지점 나눠서 저장
    ArrayList<int[]>[] lists = new ArrayList[2];
    lists[0] = new ArrayList<>();
    lists[1] = new ArrayList<>();

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (read() == 1)
          lists[(i + j) % 2].add(new int[] { i, j });
      }
    }

    // 흑백에서의 최댓값 찾아보고 더해서 결과값 출력
    int sum = 0;
    for (int i = 0; i < 2; i++) {
      max = 0;
      list = lists[i];
      DFS(0, 0, new int[N]);
      sum += max;
    }

    System.out.println(sum);
  }

  // matrix에 이미 배치한 비숍위치입력
  static void DFS(int idx, int count, int[] matrix) {
    if (idx == list.size()) {
      max = Math.max(max, count);
      return;
    }

    int[] cur = list.get(idx);

    // 배치할 비숍의 길에 이미 배치한 비숍이 있는지 확인
    boolean isAble = true;
    check: for (int[] d : bishop) {
      int x = cur[0];
      int y = cur[1];

      while (true) {
        x += d[0];
        y += d[1];
        if (x < 0 || y < 0 || x >= N || y >= N)
          break;
        if ((matrix[x] & (1 << y)) != 0) {
          isAble = false;
          break check;
        }
      }
    }

    // 다음 비숍 배치가 가능한지 재귀반복
    DFS(idx + 1, count, matrix);
    // 이번 비숍 배치하고 다음 비숍 배치 확인
    if (isAble) {
      int[] newMatrix = matrix.clone();
      newMatrix[cur[0]] += 1 << cur[1];
      DFS(idx + 1, count + 1, newMatrix);
    }
  }

  static int read() throws IOException {
    int res = 0;
    while (true) {
      int r = br.read();
      if (r == 10 || r == 32)
        return res;
      if (r == 13)
        continue;
      res = 10 * res + (r - 48);
    }
  }
}
