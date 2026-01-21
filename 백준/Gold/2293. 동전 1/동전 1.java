import java.io.*;
import java.util.*;

public class Main {

    static int n, k;
    static final int[] dp = new int[10001];
    static final Set<Integer> coinSet = new TreeSet<>();

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 2293 동전 1
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            int coin = Integer.parseInt(br.readLine());
            coinSet.add(coin);
        }

        dp[0] = 1;
        for (int coin : coinSet) {
            for (int i = coin; i <= k; i++) {
                dp[i] += dp[i - coin];
            }
        }

        bw.write(String.valueOf(dp[k]));
        bw.flush();
        bw.close();
    }
}