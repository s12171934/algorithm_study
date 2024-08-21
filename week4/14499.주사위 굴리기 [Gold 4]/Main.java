import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		int[] dice = new int[7];
		int[][][] move = setMoveMatrix();
		int[][] deltas = {{},{0,1},{0,-1},{-1,0},{1,0}};
		//초기정보 저장
		int N = read();
		int M = read();
		int x = read();
		int y = read();
		int u = 1;
		int f = 2;
		int K = read();
		
		int[][] matrix = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				matrix[i][j] = read();
			}
		}
		
		for (int k = 0; k < K; k++) {
			int d = read();
			//이동불가 명령 무시
			if (x + deltas[d][0] < 0 || x + deltas[d][0] >= N || y + deltas[d][1] < 0 || y + deltas[d][1] >= M) continue;
			x += deltas[d][0];
			y += deltas[d][1];

			//주사위 이동
			int temp = u;
			u = move[u][f][d];
			if(d == 3) {
				f = temp;
			}
			else if (d == 4) {
				f = 7 - temp;
			}
		
			//지도 및 주사위 갱신
			if (matrix[x][y] == 0) {
				matrix[x][y] = dice[u];
			}
			else {
				dice[u] = matrix[x][y];
				matrix[x][y] = 0;
			}
			
			//출력
			sb.append(dice[7 - u]).append("\n");
		}
		System.out.println(sb);
	}
	
	//주자위의 아랫 면과 남쪽 면이 주어졌을때, 동서남북 이동 시 아랫 면이 되는 방법을 모두 저장한 3차원 배열 출력
	static int[][][] setMoveMatrix() {
		int[][][] move = new int[7][7][5];
		
		//아랫 면과 남쪽면이 주어졌을때의 동쪽면
		int[][] east = {
				{},
				{0,0,3,5,2,4,0},
				{0,4,0,1,6,0,3},
				{0,2,6,0,0,1,5},
				{0,5,1,0,0,6,2},
				{0,3,0,6,1,0,4},
				{0,0,4,2,5,3,0},
		};
		
		//서쪽과 북쪽 연산하여 저장
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= 6; j++) {
				if (i == j || i + j == 7) continue;
				move[i][j][1] = east[i][j];
				move[i][j][2] = 7 - east[i][j];
				move[i][j][3] = 7 - j;
				move[i][j][4] = j;
			}
		}
		
		return move;
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
