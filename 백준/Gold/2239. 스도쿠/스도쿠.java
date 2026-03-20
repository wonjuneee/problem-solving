import java.io.*;
import java.util.*;

public class Main {

    static final int NINE = 9;

    static final int[][] sudoku = new int[NINE + 2][NINE + 2];
    static final List<int[]> emptyGridList = new ArrayList<>();
    // 각 행, 열, 블럭에 각 비트에 해당하는 값이 사용되었는지 판단하는 배열
    // validation[A] = 010101110x : A 행/열/블럭에서 2, 3, 4, 6, 8 사용
    static final int[] rowValidation = new int[NINE + 1];
    static final int[] colValidation = new int[NINE + 1];
    static final int[] blockValidation = new int[NINE + 1];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 2239 스도쿠
     */
    public static void main(String[] args) throws IOException {

        for (int i = 1; i <= NINE; i++) {
            String str = br.readLine();
            for (int j = 1; j <= NINE; j++) {
                int number = str.charAt(j - 1) - '0';
                sudoku[i][j] = number;
                if (number == 0) {
                    emptyGridList.add(new int[]{ i, j });
                } else {
                    rowValidation[i] |= (1 << number);
                    colValidation[j] |= (1 << number);
                    // e.g., (5, 3) : [{(5-1)/3} * 3 + (3-1)/3] + 1 = 3 + 0 + 1 = 4
                    blockValidation[((i - 1) / 3) * 3 + (j - 1) / 3 + 1] |= (1 << number);
                }
            }
        }

        dfs(0);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= NINE; i++) {
            for (int j = 1; j <= NINE; j++) {
                sb.append(sudoku[i][j]);
            }
            sb.append('\n');
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     *  복수 정답 존재 시, 사전 순(작은 숫자가 앞으로 오도록)으로 정렬해야 하므로 dfs를 통해 작은 값부터 채워넣도록 함
     */
    static boolean dfs(int idx) {
        // 모든 빈 칸을 채우는 케이스를 발견하면 종료
        if (idx == emptyGridList.size()) {
            return true;
        }

        int[] grid = emptyGridList.get(idx);
        int row = grid[0], col = grid[1];
        int blockNum = ((row - 1) / 3) * 3 + (col - 1) / 3 + 1;

        // 사전 순으로 가능한 케이스 순회
        for (int i = 1; i <= NINE; i++) {
            // 010101110x & 000001000x == 000001000x : 4는 사용되었으므로 조건에 부합하지 않음
            if (((rowValidation[row] & (1 << i)) != (1 << i)) && ((colValidation[col] & (1 << i)) != (1 << i)) && ((blockValidation[blockNum] & (1 << i)) != (1 << i))) {
                sudoku[row][col] = i;
                rowValidation[row] |= (1 << i);
                colValidation[col] |= (1 << i);
                blockValidation[blockNum] |= (1 << i);
                // 하위 재귀에서 false가 넘어오면, i를 현재 칸에 채울 수 없는 것이므로 현재 칸을 초기화 후 다음 반복 수행
                if (!dfs(idx + 1)) {
                    sudoku[row][col] = 0;
                    rowValidation[row] &= ~(1 << i);
                    colValidation[col] &= ~(1 << i);
                    blockValidation[blockNum] &= ~(1 << i);
                }
                // 모든 빈 칸을 채운 경우에만 true를 반환하므로, 한 번 true가 반환되면 그대로 넘겨주기
                else {
                    return true;
                }
            }
        }
        // 해당 칸에 1-9까지 모든 케이스를 채우지 못하면 해당 케이스는 정답이 될 수 없으므로 false 반환
        return false;
    }
}