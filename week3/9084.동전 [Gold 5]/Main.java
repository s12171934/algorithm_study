import java.io.*;
import java.util.StringTokenizer;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static int TC, N, M, i, j, t;
  static int[] dp, list;

  public static void main(String[] args) throws IOException {
    TC = Integer.parseInt(br.readLine());
    for (t = 0; t < TC; t++) {
      N = Integer.parseInt(br.readLine());

      list = new int[N];
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (i = 0; i < N; i++) list[i] = Integer.parseInt(st.nextToken());

      M = Integer.parseInt(br.readLine());
      dp = new int[M + 1];
      //0원을 만드는 방법은 언제나 하나 - 아무것도 사용하지 않는 것
      dp[0] = 1;

      /*
       * 동전 금액을 넘어간 시점부터 dp 계산 시작함
       * j원을 만든 방법 = list[i] 원 동전 1개 + (j - list[i]) 원을 만드는 방법
       * dp에 저장되면서 (j - list[i]) 원을 만드는 방법에는 list[i] 원 동전이 포함된 방법도 존재한다.
       */
      for (i = 0; i < N; i++) {
        if (M < list[i]) break;
        for (j = list[i]; j <= M; j++) dp[j] += dp[j - list[i]];
      }

      System.out.println(dp[M]);
    }
  }
}
