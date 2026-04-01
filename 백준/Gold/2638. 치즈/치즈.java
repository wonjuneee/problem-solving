import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final int[][] directions = new int[][]{{ 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }};
    static final int[][] map = new int[102][102];
    static final List<int[]> cheeses = new ArrayList<>();
    static final boolean[][] isVisited = new boolean[101][101];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 2638 치즈
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= n + 1; i++) {
            if (i == 0 || i == n + 1) {
                Arrays.fill(map[i], -1);
                continue;
            }

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j <= m + 1; j++) {
                if (j == 0 || j == m + 1) {
                    map[i][j] = -1;
                    continue;
                }

                int cheese = Integer.parseInt(st.nextToken());
                map[i][j] = cheese;
                if (cheese == 1) {
                    cheeses.add(new int[]{ i, j });
                } else {
                    // 최외곽은 반드시 바깥공기이므로 2로 초기화
                    if ((i == 1 || i == n) || (j == 1 || j == m)) {
                        map[i][j] = 2;
                    }
                }
            }
        }

        // 1. 바깥공기와 연결되어 있으면 0에서 2로 전환
        checkOutsider();

        int result = 0;
        while (!cheeses.isEmpty()) {
            // 2. 치즈에 대해, 바깥공기(2)에 대해서만 공기접촉 여부 판단
            checkCheese();
            // 3. 안 공기가 바깥 공기와 연결되는지 판단
            checkOutsider();
            result++;
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    // DFS로 바깥공기와 연결된 안공기를 판별
    static void checkOutsider() {
        for (int i = 1; i <= n; i++) {
            Arrays.fill(isVisited[i], false);
        }

        // (1, 1)은 반드시 바깥공기이므로, 이곳에서부터 DFS 순회하여 모든 연결된 공기 찾기
        // (1, 1)에서부터 1회만 수행하면 되므로 매 회 O(NM)의 시간복잡도
        dfs(1, 1);
    }

    static void dfs(int row, int col) {
        map[row][col] = 2;

        for (int[] direction : directions) {
            int nextRow = row + direction[0], nextCol = col + direction[1];

            if (map[nextRow][nextCol] != -1 && map[nextRow][nextCol] != 1 && !isVisited[nextRow][nextCol]) {
                isVisited[nextRow][nextCol] = true;
                dfs(nextRow, nextCol);
            }
        }
    }

    static void checkCheese() {
        Queue<int[]> removedCheese = new ArrayDeque<>();
        for (int[] cheese : cheeses) {
            int cheeseRow = cheese[0], cheeseCol = cheese[1];

            int count = 0;
            for (int[] direction : directions) {
                count = map[cheeseRow + direction[0]][cheeseCol + direction[1]] == 2 ? count + 1 : count;
            }
            if (count >= 2) {
                removedCheese.add(cheese);
            }
        }

        cheeses.removeAll(removedCheese);
        while (!removedCheese.isEmpty()) {
            int[] cheese = removedCheese.poll();
            int row = cheese[0], col = cheese[1];
            map[row][col] = 2;
        }
    }
}
