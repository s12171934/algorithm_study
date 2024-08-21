import java.io.*;
import java.util.*;

public class Solution {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder res = new StringBuilder();
  static StringTokenizer st;
  static int N, W, H, ans;
  static int[][] deltas = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

  public static void main(String[] args) throws IOException {
    int TC = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= TC; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      W = Integer.parseInt(st.nextToken());
      H = Integer.parseInt(st.nextToken());
      ans = W * H;

      long[] matrix = new long[H];
      for (int i = 0; i < H; i++) {
        st = new StringTokenizer(br.readLine());
        for (int j = 0; j < W; j++) {
          matrix[i] |= Long.parseLong(st.nextToken()) << (j * 4);
        }
      }
      DFS(0, matrix);
      res.append(String.format("#%d %d\n", tc, ans));
    }
    System.out.println(res.toString());
  }

  static void DFS(int depth, long[] matrix) {
    if (depth == N) {
      int cnt = 0;
      for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
          if ((matrix[i] & (15l << (j * 4))) == 0)
            continue;
          cnt++;
        }
      }
      ans = Math.min(ans, cnt);
      return;
    }

    boolean allZero = true;
    for (int i = 0; i < W; i++) {
      int j = 0;
      while (j < H && (matrix[j] & (15l << (i * 4))) == 0) j++;
      if (j == H) continue;
      
      long[] newMatrix = matrix.clone();
      bomb(newMatrix, i, j);

      for (int w = 0; w < W; w++) {
        int pointer = H - 1;
        for (int h = H - 1; h >= 0; h--) {
          if ((newMatrix[h] & (15l << (w * 4))) == 0) continue;
          long temp = newMatrix[h] & (15l << (w * 4));
          newMatrix[h] &= ~(15l << (w * 4));
          newMatrix[pointer--] |= temp;
        }
      }

      allZero = false;
      DFS(depth + 1, newMatrix);
    }

    if (allZero) {
      ans = 0;
    }
  }

  static void bomb(long[] matrix, int w, int h) {
    long length = ((matrix[h] & (15l << (w * 4))) >> (w * 4)) - 1;
    matrix[h] &= ~(15l << (w * 4));
    for (int[] d : deltas) {
      int x = w;
      int y = h;
      for (int i = 0; i < length; i++) {
        x += d[0];
        y += d[1];
        if (x < 0 || y < 0 || x >= W || y >= H || (matrix[y] & (15l << (x * 4))) == 0) continue;
        bomb(matrix, x, y);
      }
    }
  }
}