import java.io.*;

class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String args[]) throws IOException {
    int pointer = 1;
    int N = read();
    int[] dp = new int[N + 1];

    for (int i = 0; i < N; i++) {
      int cur = read();
      // dp에 저장된 최댓값보다 큰 경우 pointer +1 하고 저장
      if (dp[pointer - 1] < cur) {
        dp[pointer++] = cur;
        continue;
      }

      /*
       * dp에 저장된 값 중에서 나보다 큰 값 중 가장 작은 값을 대체
       * 더 큰 값이 증가수열을 생성하는데 잠재력이 더 적기 때문
       */
      int temp = pointer - 1;
      while (dp[--temp] >= cur) {}
      dp[++temp] = cur;
    }

    // 마지막 값 삽입 후 pointer 상승에 따른 보정값
    System.out.println(pointer - 1);
  }

  static int read() throws IOException {
    int res = 0;
    while (true) {
      int r = br.read();
      if (r == 10 || r == 32) return res;
      if (r == 13) continue;
      res = 10 * res + (r - 48);
    }
  }
}