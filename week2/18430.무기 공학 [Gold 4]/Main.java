import java.io.*;
import java.util.*;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  
  /*
   *   3
   * 2 X 0
   *   1
   * 
   * 부메랑을 제작 할 때
   * X01
   * X12
   * X23
   * X30
   * 으로 제작할 수 있음
   */
  static int[][] deltas = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
  static ArrayList<Weapon> weapons = new ArrayList<>();
  static int N, M, max;

  public static void main(String[] args) throws IOException {
    N = read();
    M = read();

    // 부메랑을 만들 수 없는 경우
    if (N == 1 || M == 1) {
      System.out.println(0);
      return;
    }

    int[][] matrix = new int[N][M];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        matrix[i][j] = read();
      }
    }

    /*
     * 무기를 전부 만들어서 배열에 저장함
     * weight : 강도
     * check : 사용한 블럭 비트연산을 통한 int 저장
     * 0 1 2 3 4
     * 5 6 7 8 9
     * 10 11 12 13 14
     * 15 16 17 18 19
     * 20 21 22 23 24
     * 블럭 사용 최대 정수값이 2^25 - 1 으로 int 허용범위 내이므로 int로 저장
     */
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        for (int k = 1; k < 5; k++) {
          int[] d1 = deltas[k - 1];
          int[] d2 = deltas[k % 4];
          if (i + d1[0] < 0 || i + d1[0] >= N || j + d1[1] < 0 || j + d1[1] >= M)
            continue;
          if (i + d2[0] < 0 || i + d2[0] >= N || j + d2[1] < 0 || j + d2[1] >= M)
            continue;
          weapons.add(
              new Weapon(
                  matrix[i][j] * 2 + matrix[i + d1[0]][j + d1[1]] + matrix[i + d2[0]][j + d2[1]],
                  (1 << (M * i + j)) + (1 << (M * (i + d1[0]) + j + d1[1])) + +(1 << (M * (i + d2[0]) + j + d2[1]))));
        }
      }
    }

    DFS(0, 0, 0);

    System.out.println(max);
  }

  static void DFS(int sum, int n, int check) {
    // 무기 배열 마지막에 도착하면 최댓값 갱신 후 종료
    if (n == weapons.size()) {
      max = Math.max(max, sum);
      return;
    }

    // n + 1번째 무기를 제작하지 않거나
    DFS(sum, n + 1, check);
    // n + 1번째 무기에 사용된 블럭이 사용되지 않은 경우 해당 무기를 제작하거나
    // 11 12 17 블럭을 사용한 무기의 경우, check 값은 2^11 + 2^12 + 2^17 => 100001100000000000(2)
    // or 비트 연산을 통해서 사용된 블럭 갱신함
    if ((check & weapons.get(n).check) == 0)
      DFS(sum + weapons.get(n).weight, n + 1, check | weapons.get(n).check);
  }

  // check는 비트연산을 통한 이진수로 사용된 블럭 확인
  static class Weapon {
    int weight, check;

    Weapon(int weight, int check) {
      this.weight = weight;
      this.check = check;
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
