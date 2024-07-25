import java.io.*;

public class Main {

	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int[] list;
	public static int N, S, count;

	public static void main(String[] args) throws IOException {
		N = read();
		S = read();
		list = new int[N];
		for (int i = 0; i < N; i++)
			list[i] = read();

		DFS(0, 0);
		// 아무것도 포함되지 않는 경우 합이 0 이므로 count에 포함이 됨
		count -= S == 0 ? 1 : 0;
		System.out.println(count);
	}

	public static void DFS(int sum, int idx) {
		if (idx == N) {
			if (sum == S)
				count++;
		} else {
			DFS(sum + list[idx++], idx);
			DFS(sum, idx);
		}
	}

	public static int read() throws IOException {
		int res = 0;
		int mode = 1;
		while (true) {
			;
			int r = br.read();
			if (r == 10 || r == 32)
				return res;
			if (r == 13)
				continue;
			if (r == 45) {
				mode = -1;
				continue;
			}
			res = 10 * res + mode * (r - 48);
		}
	}
}