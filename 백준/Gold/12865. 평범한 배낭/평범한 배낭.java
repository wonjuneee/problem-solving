import java.io.*;
import java.util.*;

public class Main {

    static int n, k;

    static final int[] WEIGHT = new int[101];
    static final int[] VALUE = new int[101];
    static final int[] DP = new int[100001];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 12865 평범한 배낭
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int weight = Integer.parseInt(st.nextToken()), value = Integer.parseInt(st.nextToken());
            WEIGHT[i] = weight;
            VALUE[i] = value;
        }

        for (int i = 1; i <= n; i++) {
            for (int w = k; w >= WEIGHT[i]; w--) {
                DP[w] = Math.max(DP[w - WEIGHT[i]] + VALUE[i], DP[w]);
            }
        }

        bw.write(String.valueOf(DP[k]));
        bw.flush();
        bw.close();
        br.close();
    }
}