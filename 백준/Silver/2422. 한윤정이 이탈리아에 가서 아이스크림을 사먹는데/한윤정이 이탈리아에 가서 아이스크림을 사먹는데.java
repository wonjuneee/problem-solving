import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final boolean[][] disjoint = new boolean[201][201];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 2422 한윤졍이 이탈리아에 가서 아이스크림을 사먹는데
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 두 아이스크림 간 섞이면 안되는 관계를 Map으로 저장
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            disjoint[a][b] = true;
            disjoint[b][a] = true;
        }

        int result = combination(1, 0, new int[3]);
        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * nCr (r == 3)
     * 조합이므로 순서에 따른 중복이 발생하지 않도록, min(탐색을 시작하는 아이스크림 번호)부터 r개를 선택하도록 한다.
     * 이미 선택된 아이스크림(arr)에 대해, 섞이면 안되는 아이스크림인지를 판별하여 다음 재귀에 포함시킬지 여부를 판단한다.
     */
    static int combination(int min, int r, int[] arr) {
        if (r == arr.length) {
            return 1;
        }

        int result = 0;
        for (int i = min; i <= n; i++) {
            boolean flag = true;
            for (int count = 0; count < r; count++) {
                int choice = arr[count];
                if (disjoint[choice][i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                arr[r] = i;
                result += combination(i + 1, r + 1, arr);
            }
        }

        return result;
    }
}
