import java.io.*;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

  static int[][] matrix = new int[11][11];
  static int max;

  public static void main(String[] args) throws IOException {
    int TC = read();
    StringBuilder sb = new StringBuilder();

    for (int t = 0; t < TC; t++) {
      max = 0;
      for (int i = 0; i < 11; i++) {
        for (int j = 0; j < 11; j++) {
          matrix[i][j] = read();
        }
      }
      // 비트 연산을 통한 배치 확인
      // 아무도 배치가 안된 경우 0 => 00000000000(2)
      DFS(0, 0, 0);
      sb.append(max).append("\n");
    }

    bw.write(sb.toString());
    bw.flush();
  }

  static void DFS(int sum, int n, int check) {
    // 모든 선수에 대해서 배치가 종료되었다면 최대값을 갱신 시도하고 종료
    // (1 << 11) - 1 => 11111111111(2)
    if (check == (1 << 11) - 1) {
      max = Math.max(max, sum);
      return;
    }

    // 전부 배치되지 않았지만 마지막 선수까지 배치를 검토한 경우 종료
    if (n == 11)
      return;

    for (int i = 0; i < 11; i++) {
      // 적합하지 않은 포지션
      if (matrix[n][i] == 0)
        continue;
      // 이미 배치가 진행된 포지션
      if ((check & (1 << i)) != 0)
        continue;
      // 선수를 배치하고 능력치의 합을 연산하여 다음 선수 배치를 진행
      DFS(sum + matrix[n][i], n + 1, check | (1 << i));
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
