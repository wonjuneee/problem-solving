import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final int[] TRAILER = new int[50001];
    static final long[] CUMULATIVE_SUM = new long[50001];
    static final long[][] DP = new long[4][50001];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 2616 소형기관차
     */
    public static void main(String[] args) throws IOException {
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            TRAILER[i] = Integer.parseInt(st.nextToken());
        }
        m = Integer.parseInt(br.readLine());

        for (int i = 1; i <= n; i++) {
            CUMULATIVE_SUM[i] = CUMULATIVE_SUM[i - 1] + TRAILER[i];
        }

        for (int train = 1; train <= 3; train++) {
            for (int i = 1; i <= n; i++) {
                if (i <= m) {
                    DP[train][i] = CUMULATIVE_SUM[i];
                } else {
                    DP[train][i] = Math.max(DP[train - 1][i - m] + (CUMULATIVE_SUM[i]- CUMULATIVE_SUM[i - m]) , DP[train][i - 1]);
                }
            }
        }

        bw.write(String.valueOf(DP[3][n]));
        bw.flush();
        bw.close();
        br.close();
    }
}
