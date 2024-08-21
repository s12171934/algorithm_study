import java.io.*;
import java.util.*;

public class Solution {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder res = new StringBuilder();
  static int N, max, min, arr[];

  public static void main(String[] args) throws IOException {
    int TC = Integer.parseInt(br.readLine());
    for (int tc = 1; tc <= TC; tc++) {
      N = Integer.parseInt(br.readLine());
      max = Integer.MIN_VALUE;
      min = Integer.MAX_VALUE;
      arr = new int[N];

      StringTokenizer orders = new StringTokenizer(br.readLine());
      StringTokenizer numbers = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
        arr[i] = Integer.parseInt(numbers.nextToken());

      DFS(arr[0], 1, Integer.parseInt(orders.nextToken()), Integer.parseInt(orders.nextToken()),
          Integer.parseInt(orders.nextToken()), Integer.parseInt(orders.nextToken()));
      res.append(String.format("#%d %d\n", tc, max - min));
    }
    System.out.println(res.toString());
  }

  static void DFS(int sum, int n, int plus, int minus, int multiple, int mod) {
    if (n == N) {
      max = Math.max(max, sum);
      min = Math.min(min, sum);
      return;
    }

    for (int i = 0; i < 4; i++) {
      switch (i) {
        case 0:
          if (plus != 0)
            DFS(sum + arr[n], n + 1, plus - 1, minus, multiple, mod);
          break;
        case 1:
          if (minus != 0)
            DFS(sum - arr[n], n + 1, plus, minus - 1, multiple, mod);
          break;
        case 2:
          if (multiple != 0)
            DFS(sum * arr[n], n + 1, plus, minus, multiple - 1, mod);
          break;
        case 3:
          if (mod != 0)
            DFS(sum / arr[n], n + 1, plus, minus, multiple, mod - 1);
          break;
      }
    }
  }
}