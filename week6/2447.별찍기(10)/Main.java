import java.io.*;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) throws IOException {
    int N = Integer.parseInt(br.readLine());
    int K = (int) Math.round(Math.log(N) / Math.log(3));
    boolean[][][] matrix = new boolean[K + 1][][];
    matrix[0] = new boolean[][] { { true } };
    for (int n = 1; n <= K; n++) {
      int lastLen = matrix[n - 1].length;
      matrix[n] = new boolean[lastLen * 3][lastLen * 3];

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (i == 1 && j == 1)
            continue;
          for (int k = 0; k < lastLen; k++) {
            for (int l = 0; l < lastLen; l++) {
              matrix[n][i * lastLen + k][j * lastLen + l] = matrix[n - 1][k][l];
            }
          }
        }
      }
    }

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        sb.append(matrix[K][i][j] ? '*' : ' ');
      }
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }
}
