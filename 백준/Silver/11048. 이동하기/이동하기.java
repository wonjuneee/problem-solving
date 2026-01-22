import java.io.*;
import java.util.*;

public class Main {

    static int n, m;
    static final int[] dp = new int[1001];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 11048 이동하기
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++) {
		            // 불필요한 메모리 할당없이, 매 행마다 각 열의 값을 덮어씌운다.
                dp[j] = Integer.parseInt(st.nextToken())
                    + Math.max(dp[j], dp[j - 1]);

            }
        }

        bw.write(String.valueOf(dp[m]));
        bw.flush();
        bw.close();
    }
}