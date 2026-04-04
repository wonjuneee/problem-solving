import java.io.*;
import java.util.*;

public class Main {

    static int n, m;
    static int safeArea = 0;

    static final int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    static final int[][] map = new int[10][10];
    static final List<int[]> newWallPosition = new ArrayList<>();
    static final List<int[]> virusPosition = new ArrayList<>();
    static final Queue<int[]> bfsQ = new ArrayDeque<>();
    static final boolean[][] isVisited = new boolean[10][10];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 14502 연구소
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= n + 1; i++) {
            if (i == 0 || i == n + 1) {
                Arrays.fill(map[i], 1);
                continue;
            }
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j <= m + 1; j++) {
                if (j == 0 || j == m + 1) {
                    map[i][j] = 1;
                    continue;
                }
                int element = Integer.parseInt(st.nextToken());
                map[i][j] = element;

                if (element == 2) {
                    virusPosition.add(new int[]{ i, j });
                } else if (element == 0) {
                    safeArea++;
                }
            }
        }

        // 3개의 벽을 세울 수 있는 모든 경우의 수를 계산한 뒤, 각 케이스마다 바이러스를 전파
        getNewWallCombination(1, 3, new int[3]);
        int result = pollution();

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    // nCr, 최초 r은 항상 3으로 고정
    static void getNewWallCombination(int startPos, int r, int[] pos) {
        // r == 0 인 경우 벽 3개를 모두 세운 케이스가 되므로 newWallPosition에 기록
        if (r == 0) {
            newWallPosition.add(Arrays.copyOf(pos, 3));
            return;
        }

        for (int i = startPos; i <= n * m; i++) {
            // 이차원 좌표를 숫자로 저장하고 싶을 때, 전체 열 개수를 기준으로 나눗셈/모듈러 연산을 수행하면 됨
            int row = (i - 1) / m + 1, col = (i - 1) % m + 1;
            if (map[row][col] != 2 && map[row][col] != 1) {
                pos[3 - r] = i;
                // 조합이므로 i+1 이후 값을 선택하면 된다. (순열인 경우 1부터 다시 반복하며 선택되지 않은 값을 선택하면 된다.)
                getNewWallCombination(i + 1, r - 1, pos);
            }
        }
    }

    // 각 조합마다 벽을 세운 뒤, 바이러스 위치에서 전파를 진행
    static int pollution() {
        int result = 0;

        for (int[] wallComb : newWallPosition) {
            for (int idx : wallComb) {
                int row = (idx - 1) / m + 1, col = (idx - 1) % m + 1;
                map[row][col] = 1;
            }
            result = Math.max(bfs(safeArea), result);
            for (int idx : wallComb) {
                int row = (idx - 1) / m + 1, col = (idx - 1) % m + 1;
                map[row][col] = 0;
            }
        }

        return result;
    }


    // 바이러스가 전파될 때마다 안전지역 수에 -1. 이때, 벽 개수만큼 차감하여 반환하도록 한다.
    static int bfs(int safeArea) {
        bfsQ.clear();
        for (int i = 1; i <= n; i++) {
            Arrays.fill(isVisited[i], false);
        }
        bfsQ.addAll(virusPosition);
        for (int[] virus : virusPosition) {
            isVisited[virus[0]][virus[1]] = true;
        }

        while (!bfsQ.isEmpty()) {
            int[] node = bfsQ.poll();
            int row = node[0], col = node[1];

            for (int[] direction : directions) {
                int nextRow = row + direction[0], nextCol = col + direction[1];

                if (!isVisited[nextRow][nextCol] && map[nextRow][nextCol] != 1) {
                    bfsQ.add(new int[]{ nextRow, nextCol });
                    isVisited[nextRow][nextCol] = true;
                    safeArea--;
                }
            }
        }

        return safeArea - 3;
    }
}