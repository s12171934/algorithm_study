import java.io.*;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {
		int N = read();
		int K = read();
		int[] diff = new int[N - 1];
		int last = read();
		for (int i = 0; i < N - 1; i++) {
			int now = read();
			diff[i] = now - last;
			last = now;
		}
		Arrays.sort(diff);
		long sum = 0;
		for (int i = 0; i < N - K; i++) sum += diff[i];
		System.out.println(sum);
 	}
	
	static int read() throws IOException {
		int res = 0;
		while(true) {
			int r = br.read();
			if (r == 10 || r == 32) return res;
			if (r == 13) continue;
			res = 10 * res + (r - 48);
		}
	}
}
