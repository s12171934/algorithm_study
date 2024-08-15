import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {
		int N = read();
		int K = read();
		long[] list = new long[N + 1];
    // 누적합
		for (int i = 1; i <= N; i++) list[i] = list[i - 1] + read();
		long[] dp = new long[N + 1];
	
		int pointer = 0;
    // 이번 먹이를 먹고 소화를 하게 된다면 가장 처음 먹은 먹이의 index를 pointer로 설정
    // 애벌레는 나아가므로 pointer도 돌아갈 일이 없으므로 계속해서 ++
		for (int i = 1; i <= N; i++) {
			dp[i] = dp[i - 1];
			while (list[i - 1] - list[pointer] >= K) pointer++;
			long foods = list[i] - list[pointer] - K;
			if(foods >= 0) dp[i] = Math.max(dp[i], dp[pointer] + foods);
		}
		
		System.out.println(dp[N]);
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
