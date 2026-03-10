import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final int[] TRAILER = new int[50001];
    static final long[] CUMULATIVE_SUM = new long[50001];
    // 사용한 기관차 대수(행)에 대해 각 객차까지의 최대 수송량(열)
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

        // 소형기관차를 train 대수만큼 활용
        for (int train = 1; train <= 3; train++) {
            // 앞선 기관차가 겹치지 않게 객차를 선택한 경우, train * m 이후 객차부터 현재 train이 선택할 수 있다.
            for (int i = train * m; i <= n; i++) {
                // i - m + 1부터 i까지 m칸의 객차를 선택 경우(이전 기관차의 i칸까지의 수송량 + 이후 m칸 수용량), i칸 객차를 선택하지 않는 경우 중 최대
                DP[train][i] = Math.max(DP[train - 1][i - m] + (CUMULATIVE_SUM[i] - CUMULATIVE_SUM[i - m]), DP[train][i - 1]);
            }
        }

        bw.write(String.valueOf(DP[3][n]));
        bw.flush();
        bw.close();
        br.close();
    }
}
