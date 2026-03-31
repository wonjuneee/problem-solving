import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    static final int[][] map = new int[1002][1002];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 1238 파티
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
            String line = br.readLine();
            for (int j = 0; j <= m + 1; j++) {
                if (j == 0 || j == m + 1) {
                    map[i][j] = -1;
                } else {
                    int location = line.charAt(j - 1) - '0';
                    map[i][j] = location;
                }
            }
        }

        int result = bfs();

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static int bfs() {
        Queue<int[]> bfsQ = new ArrayDeque<>();
        // [3]: 벽을 부순 케이스인지 판별 (0: unbroken, 1: broken)
        bfsQ.add(new int[]{1, 1, 1, 0});
        // isVisited[a][b][0]: 벽을 뚫지 않고 방문, isVisited[a][b][1]: 벽을 뚫고 방문
        boolean[][][] isVisited = new boolean[n + 1][m + 1][2];
        isVisited[1][1][0] = true;
        isVisited[1][1][1] = true;


        while (!bfsQ.isEmpty()) {
            int[] currNode = bfsQ.poll();
            int currRow = currNode[0], currCol = currNode[1], dist = currNode[2], isBroken = currNode[3];

            if (currRow == n && currCol == m) {
                return dist;
            }
            for (int[] direction : directions) {
                int nextRow = currRow + direction[0], nextCol = currCol + direction[1];

                // 1. 벽이 없으므로 그대로 방문
                if (map[nextRow][nextCol] != -1 && map[nextRow][nextCol] != 1 && !isVisited[nextRow][nextCol][isBroken]) {
                    isVisited[nextRow][nextCol][isBroken] = true;
                    bfsQ.add(new int[]{ nextRow, nextCol, dist + 1, isBroken});
                }
                // 2. 아직 벽을 뚫은 적이 없을 때, 다음 벽을 뚫고 방문
                if (map[nextRow][nextCol] != -1 && map[nextRow][nextCol] == 1 && isBroken == 0 && !isVisited[nextRow][nextCol][1]) {
                    isVisited[nextRow][nextCol][1] = true;
                    bfsQ.add(new int[]{ nextRow, nextCol, dist + 1, 1});
                }
                // 3. 벽을 뚫은 적이 있고, 다음 벽이 있으면 끝

            }
        }

        return -1;
    }
}
