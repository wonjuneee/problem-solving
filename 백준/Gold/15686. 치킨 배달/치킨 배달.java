import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final List<int[]> CHICKEN_HOUSE = new ArrayList<>();
    static final List<int[]> HOUSE = new ArrayList<>();

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 15686 치킨 배달
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                int house = Integer.parseInt(st.nextToken());
                if (house == 1) {
                    HOUSE.add(new int[] { i, j });
                } else if (house == 2) {
                    CHICKEN_HOUSE.add(new int[] { i, j });
                }
            }
        }

        int result = findMinimumCombinations(0, 0, new int[13]);

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    // aCm (a == CHICKEN_HOUSE.size())

    /**
     * 치킨집으로 만들 수 있는 조합을 구성하여, 각 조합의 치킨거리 중 최소값을 반환
     * m <= 치킨집 <= 13이므로, 최대 13C6 = 1716가지의 경우의 수가 존재한다.
     * 이때 집 <= 2n 이므로, 최대 1716 * 100 * m = 171_600 * m 의 연산이 수행되므로, 1초의 시간복잡도 내에서 연산 가능하다.
     */
    static int findMinimumCombinations(int start, int count, int[] comb) {
        if (count == m) {
            return getChickenDistance(comb);
        }

        int result = Integer.MAX_VALUE;
        for (int i = start; i < CHICKEN_HOUSE.size(); i++) {
            comb[count] = i;
            result = Math.min(findMinimumCombinations(i + 1, count + 1, comb), result);
        }

        return result;
    }

    static int getChickenDistance(int[] comb) {
        int result = 0;
        for (int[] house : HOUSE) {
            int chickenDist = Integer.MAX_VALUE;
            for (int idx = 0; idx < m; idx++) {
                int[] chickenHouse = CHICKEN_HOUSE.get(comb[idx]);
                chickenDist = Math.min(Math.abs(house[0] - chickenHouse[0]) + Math.abs(house[1] - chickenHouse[1]), chickenDist);
            }
            result += chickenDist;
        }

        return result;
    }
}
