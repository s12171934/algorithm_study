import java.io.*;
import java.util.*;

public class Main {

  public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
  public static Node[] hackLen;
  public static boolean[] childs;
  public static boolean[] cycleVisited;
  public static int N, M;
  public static int max = 0;

  public static void main(String[] args) throws IOException {
    N = read();
    M = read();

    //Cycle 내 Start Node의 X값으로 통일하기 위해 Node 객체 생성
    hackLen = new Node[N + 1];
    for (int i = 0; i <= N; i++)
      hackLen[i] = new Node(0);

    /*
     * B Node가 A Node보다 언제나 해킹할 수 있는 컴퓨터의 갯수가 많거나 같다.
     * 같은 경우는 A 와 B가 같은 Cycle에 포함되어 있는 경우이다.
     */
    childs = new boolean[N + 1];

    //단방향 그래프 생성
    for (int i = 0; i <= N; i++)
      graph.add(new ArrayList<>());
    for (int i = 0; i < M; i++) {
      int A = read();
      int B = read();
      graph.get(B).add(A);
    }

    //childs가 True인 경우, Cycle내에서 값이 갱신되었거나 A에 해당하는 Node이므로 Skip
    for (int i = 1; i <= N; i++) {
      if (childs[i])
        continue;

      /*
       * BFS 실행 중 Cycle 확인 시 checkCycle()을 실행
       * hackLen에는 Start Node의 자식 갯수를 입력함
       */
      boolean checkCycle = false;

      boolean[] visited = new boolean[N + 1];
      visited[i] = true;

      Queue<Integer> q = new LinkedList<>();
      q.add(i);

      while (!q.isEmpty()) {
        int n = q.poll();

        for (int child : graph.get(n)) {
          //Cycle 확인
          if (child == i)
            checkCycle = true;
          if (visited[child])
            continue;
          visited[child] = true;
          //자식 노드임을 표시하여 시작 노드로 BFS를 돌지 않게 함
          childs[child] = true;
          hackLen[i].x++;
          q.add(child);
        }
      }

      //Cycle 포함 노드의 hackLen값을 갱신해줌
      if (checkCycle) {
        cycleVisited = new boolean[N + 1];
        checkCycle(i, i);
      }

      max = Math.max(max, hackLen[i].x);
    }

    //출력
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= N; i++) {
      if (hackLen[i].x == max) {
        sb.append(i).append(" ");
      }
    }
    System.out.println(sb);
  }

  //얕은 복사를 통해 hackLen 값 갱신을 편하게 하기 위해 생성한 객체
  public static class Node {
    int x;

    public Node(int x) {
      this.x = x;
    }
  }

  //DFS를 통해 Cycle 확인하고 Cycle에 포함된 노드의 hackLen값 갱신
  public static boolean checkCycle(int start, int now) {
    boolean check = false;
    cycleVisited[now] = true;

    for (int next : graph.get(now)) {
      if (cycleVisited[next]) {
        check = true;
      } else {
        check = checkCycle(start, next) | check;
      }
    }

    if (check) {
      hackLen[now] = hackLen[start];
    }

    return check;
  }

  // Int 입력을 위한 메서드
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
