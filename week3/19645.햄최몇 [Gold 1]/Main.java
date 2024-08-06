import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, sum;
	static int[] list;
	static int[][] dp = new int[2501][2501];
	
	public static void main(String[] args) throws IOException {
		N = read();
		
		if (N < 3) {
			System.out.println(0);
			return;
		}
		
		list = new int[N];
		for (int i = 0; i < N; i++) {
			list[i] = read();
			sum += list[i];
		}
		
		for (int i = 0; i < 2501; i++) {
			for (int j = 0; j < 2501; j++) {
				dp[i][j] = -1;
			}
		}
		System.out.println(DFS(0,0,0));
	}
	
	static int DFS(int depth, int x, int y) {
		// 마지막 햄버거 도착했을 때, sum - x - y 가 최소라면 반환
		if(depth == N) {
			if (sum - x - y > x || sum - x - y > y) return -1;
			return sum - x - y;
		}
		
		// 이미 depth가 최심에 도달하여 값을 반환한 이후에 마주치게 되는 조건
		if(dp[x][y] != -1) return dp[x][y];
		
		// x 선배, y 선배, 내가 햄버거를 먹는 경우를 각각 계산해서 나의 최대를 반환
		int res = 0;
		res = Math.max(res, DFS(depth + 1, x + list[depth], y));
		res = Math.max(res, DFS(depth + 1, x, y + list[depth]));
		res = Math.max(res, DFS(depth + 1, x, y));
		
		// 선배의 경우 누가 더 선배인지 모르니까 x, y 둘다 저장해버림
		dp[x][y] = res;
		dp[y][x] = res;
	
		return res;
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
