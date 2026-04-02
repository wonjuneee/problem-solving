import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final int[][] map = new int[1001][1001];
    static final int[][] dp = new int[1001][1001];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 1915 가장 큰 정사각형
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            String line= br.readLine();
            for (int j = 1; j <= m; j++) {
                int number = line.charAt(j - 1) - '0';
                map[i][j] = number ;
            }
        }

        dp();

        int result = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                result = Math.max(dp[i][j], result);
            }
        }

        bw.write(String.valueOf(result * result));
        bw.flush();
        bw.close();
        br.close();
    }

    static void dp() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (map[i][j] == 1) {
                    // 현재 위치에 '1'이 있을 경우, 좌측과 상단의 dp값을 확인
                    int topAndLeft = Math.min(dp[i - 1][j], dp[i][j - 1]);
                    // 최대 정사각형 크기가 될 수 있는 값만큼 거슬러 올라가 좌측최상단 값이 '0'이 아님을 확인하여
                    // 0이면 정사각형이 성립되지 않으므로 topAndLeft 그대로 유지, 0이 아니면 정사각형이 성립되므로 topAndLeft + 1을 저장
                    dp[i][j] = map[i - topAndLeft][j - topAndLeft] != 0 ? topAndLeft + 1 : topAndLeft;
                }
            }
        }
    }
}