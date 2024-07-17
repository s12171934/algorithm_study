import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ArrayList<ArrayList<Integer>> graph;
    static int[] parents;

    public static void main(String[] args) throws Exception {
        int N = read();
        
        parents = new int[N + 1];
        parents[1] = 1;

        // 그래프 생성
        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) graph.add(new ArrayList<>());
        for (int i = 0; i < N - 1; i++) {
            int n1 = read();
            int n2 = read();

            graph.get(n1).add(n2);
            graph.get(n2).add(n1);
        }

        BFS(1);

        // 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= N; i++) sb.append(parents[i]).append("\n");
        System.out.println(sb);
    }

    public static void BFS(int s) {
        // BFS를 위한 Queue 생성 및 초기화
        Queue<Integer> q = new LinkedList<>();
        q.add(s);

        // Parent 탐색
        while (!q.isEmpty()) {
            int parent = q.poll();

            for (int child : graph.get(parent)) {
                if (parents[child] != 0) continue;
                parents[child] = parent;
                q.add(child);
            }
        }
    }

    // Int 입력을 위한 메서드
    public static int read() throws Exception {
        int res = 0;
        while (true) {
            int r = br.read();
            if (r == 10 || r == 32) return res;
            res = 10 * res + r - 48;
        }
    }
}