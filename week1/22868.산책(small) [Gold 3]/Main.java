import java.io.*;
import java.util.*;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws Exception {
    int N = read();
    int M = read();

    // 그래프 생성
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= N; i++)
      graph.add(new ArrayList<>());
    for (int i = 0; i < M; i++) {
      int n1 = read();
      int n2 = read();

      graph.get(n1).add(n2);
      graph.get(n2).add(n1);
    }
    // S -> E 사전순 정렬을 위한 정렬
    for (int i = 0; i <= N; i++)
      graph.get(i).sort(Integer::compareTo);

    int s = read();
    int e = read();
    int walk = 0;

    // S -> E 경로를 확보하기 위한 Queue
    Queue<ArrayList<Integer>> q = new LinkedList<>();
    q.add(new ArrayList<>(List.of(s)));

    boolean[] visited = new boolean[N + 1];
    visited[s] = true;

    while (!q.isEmpty()) {
      ArrayList<Integer> route = q.poll();
      /*
       * BFS 실행도중 가장 먼저 경로 마지막이 E인 경우
       * 미리 그래프를 사전순으로 정리 했으므로 사전순 가장 빠른 경로임
       */
      if (route.get(route.size() - 1) == e) {
        // 이동거리는 경로의 크기보다 1 작음
        walk += route.size() - 1;

        /*
         * S -> E 경로 내의 정점은 다시 방문하지 않을 것이므로
         * 해당 정점 visited 0으로 초기화
         * 단, S의 경우 마지막에 방문함
         * 이후에 바로 E -> S BFS 실행하기 위해 Break
         */
        visited = new boolean[N + 1];
        for (int r : route) {
          if (r == s)
            continue;
          visited[r] = true;
        }
        break;
      }

      // 미방문 정점 ArrayList에 추가
      for (int next : graph.get(route.get(route.size() - 1))) {
        if (visited[next])
          continue;
        visited[next] = true;
        ArrayList<Integer> nextArr = new ArrayList<>(route);
        nextArr.add(next);
        q.add(nextArr);
      }
    }

    /*
     * E -> S 의 경우 경로를 확인 할 필요가 없으므로
     * 현재 위치와 이동 거리만 요소로 가지는 int[] queue로 BFS 실행
     */
    Queue<int[]> q2 = new LinkedList<>();
    q2.add(new int[] { e, walk });

    while (!q2.isEmpty()) {
      int[] node = q2.poll();

      // 가장 먼저 S에 도착했을 때의 이동 거리 출력 후 종료
      if (node[0] == s) {
        System.out.println(node[1]);
        return;
      }

      for (int next : graph.get(node[0])) {
        if (visited[next])
          continue;
        visited[next] = true;
        q2.add(new int[] { next, node[1] + 1 });
      }
    }
  }

  // Int 입력을 위한 메서드
  public static int read() throws Exception {
    int res = 0;
    while (true) {
      int r = br.read();
      if (r == 10 || r == 32)
        return res;
      res = 10 * res + r - 48;
    }
  }
}