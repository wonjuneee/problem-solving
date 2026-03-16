import java.io.*;
import java.util.*;

public class Main {

    static int r, c, t;

    // 공기청정기가 위치한 열과 아랫행이 담기게 된다.
    static final int[] conditioner = new int[2];
    static final int[][] map = new int[52][52];
    // 미세먼지가 전파된 값을 저장. 전체에 대해 값을 계산하면 map에 더해진다.
    static final int[][] nextStep = new int[51][51];
    static final int[][] directions = new int[][]{{ 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }};

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 17144 미세먼지 안녕!
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        for (int row = 0; row <= r + 1; row++) {
            if (row == 0 || row == r + 1) {
                Arrays.fill(map[row], -2);
                continue;
            }
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col <= c + 1; col++) {
                if (col == 0 || col == c + 1) {
                    map[row][col] = -2;
                    continue;
                }
                int value = Integer.parseInt(st.nextToken());
                map[row][col] = value;
                if (value == -1) {
                    conditioner[0] = row;
                    conditioner[1] = col;
                }
            }
        }

        /*
            spread() - (map의 모든 칸 * 상하좌우 4칸 계산) * 시간 = (rc * 4) * t
            airflow() - 공기청정기 바람 진행 칸 * 시간 = (2r + 2c) * t
            최대: ((2500 * 4) + 200) * 1000 = 10200 * 1000 = 10,200,000
        */
        for (int i = 0; i < t; i++) {
            spread();
            airflow();
        }

        int result = 0;
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                if (map[i][j] > 0) {
                    result += map[i][j];
                }
            }
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static void spread() {
        for (int row = 1; row <= r; row++) {
            for (int col = 1; col <= c; col++) {
                if (map[row][col] > 0) {
                    checkSpreadableSpace(row, col);
                }
            }
        }

        for (int row = 1; row <= r; row++) {
            for (int col = 1; col <= c; col++) {
                if (map[row][col] >= 0 && nextStep[row][col] > 0) {
                    map[row][col] += nextStep[row][col];
                    nextStep[row][col] = 0;
                }
            }
        }
    }

    static void checkSpreadableSpace(int r, int c) {
        int movable = map[r][c] / 5;
        for (int[] d : directions) {
            int nextRow = r + d[0], nextCol = c + d[1];
            if (map[nextRow][nextCol] >= 0) {
                nextStep[nextRow][nextCol] += movable;
                map[r][c] -= movable;
            }
        }
    }

    /**
     * 공기청정기 바람 진행방향의 반대로 미세먼지를 거슬러 탐색
     */
    static void airflow() {
        int topRow = conditioner[0] - 1, bottomRow = conditioner[0], column = conditioner[1];

        while (column > 1) {
            if (column != conditioner[1]) {
                map[topRow][column] = map[topRow][column - 1];
                map[bottomRow][column] = map[bottomRow][column - 1];
            } else {
                map[topRow][column] = -1;
                map[bottomRow][column] = -1;
            }
            column--;
        }

        while (topRow > 1) {
            if (map[topRow][column] != -1) {
                map[topRow][column] = map[topRow - 1][column];
            } else {
                map[topRow][column] = -1;
            }
            topRow--;
        }
        while (bottomRow < r) {
            if (map[bottomRow][column] != -1) {
                map[bottomRow][column] = map[bottomRow + 1][column];
            } else {
                map[bottomRow][column] = -1;
            }
            bottomRow++;
        }

        while (column < c) {
            map[topRow][column] = map[topRow][column + 1];
            map[bottomRow][column] = map[bottomRow][column + 1];
            column++;
        }

        while (topRow < conditioner[0] - 1) {
            if (map[topRow + 1][column] != -1) {
                map[topRow][column] = map[topRow + 1][column];
            } else {
                map[topRow][column] = 0;
            }
            topRow++;
        }
        while (bottomRow > conditioner[0]) {
            if (map[bottomRow - 1][column] != -1) {
                map[bottomRow][column] = map[bottomRow - 1][column];
            } else {
                map[bottomRow][column] = 0;
            }
            bottomRow--;
        }

        while (column > conditioner[1]) {
            if (column - 1 != conditioner[1]) {
                map[topRow][column] = map[topRow][column - 1];
                map[bottomRow][column] = map[bottomRow][column - 1];
            } else {
                map[topRow][column] = 0;
                map[bottomRow][column] = 0;
            }
            column--;
        }
    }
}