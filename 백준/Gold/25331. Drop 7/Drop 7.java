import java.io.*;
import java.util.*;

public class Main {

    static final int[][] BOARD = new int[9][9];
    static final int[] TOP = new int[8];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 25331 Drop 7
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st;

        for (int i = 1; i <= 7; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 7; j++) {
                int ball = Integer.parseInt(st.nextToken());
                BOARD[i][j] = ball;
                if (ball == 0) {
                    TOP[j] = i;
                }
            }
        }
        int ball = Integer.parseInt(br.readLine());
        int min = Integer.MAX_VALUE;

        for (int col = 1; col <= 7; col++) {
            int row = TOP[col];

            int[][] copiedBoard = new int[9][9];
            for (int i = 1; i <= 7; i++) {
                System.arraycopy(BOARD[i], 0, copiedBoard[i], 0, BOARD[i].length);
            }
            copiedBoard[row][col] = ball;
            Queue<Integer> targetQ = new ArrayDeque<>();
            targetQ.add(row * 7 + col);

//            System.out.println("INITIAL: " + Arrays.deepToString(copiedBoard));

            boolean[][] needToDelete = new boolean[8][8];
            Set<Integer> targetSet = new HashSet<>();
            while (!targetQ.isEmpty()) {
                int target = targetQ.poll();
                int targetRow = (target - 1) / 7;
                int targetCol = target % 7 == 0 ? 7 : target % 7;
//                System.out.println("TARGET: " + targetRow + ", " + targetCol);

                int groupSize = 0;
                for (int i = 0; i < 8; i++) {
                    if (copiedBoard[targetRow][i + 1] != 0) {
                        groupSize++;
                    } else if (copiedBoard[targetRow][i + 1] == 0 && groupSize != 0) {
                        for (int j = i + 1; j > i - groupSize; j--) {
                            if (copiedBoard[targetRow][j] == groupSize) {
                                needToDelete[targetRow][j] = true;
                            }
                        }
                        groupSize = 0;
                    }
                }
//                System.out.println(Arrays.deepToString(needToDelete));

                groupSize = 0;
                for (int i = 0; i < 8; i++) {
                    if (copiedBoard[i + 1][targetCol] != 0) {
                        groupSize++;
                    } else if (copiedBoard[i + 1][targetCol] == 0 && groupSize != 0){
                        for (int j = i + 1; j > i - groupSize; j--) {
                            if (copiedBoard[j][targetCol] == groupSize) {
                                needToDelete[j][targetCol] = true;
                            }
                        }
                        groupSize = 0;
                    }
                }
//                System.out.println(Arrays.deepToString(needToDelete));

                if (targetQ.isEmpty()) {

                    int cnt = 0;
                    for (int i = 1; i <= 7; i++) { // row
                        for (int j = 1; j <= 7; j++) { // col
                            if (needToDelete[i][j]) {
                                copiedBoard[i][j] = 0;
                                cnt++;
                            }
                        }
                        Arrays.fill(needToDelete[i], false);
                    }
                    if (cnt == 0) {
                        break;
                    }

                    for (int i = 1; i <= 7; i++) { // col
                        int bottom = 7;
                        for (int j = 7; j >= 1; j--) { // row
                            if (copiedBoard[j][i] != 0) {
                                bottom--;
                            } else {
                                while (bottom > 0 && copiedBoard[--bottom][i] == 0);

                                copiedBoard[j][i] = copiedBoard[bottom][i];
                                copiedBoard[bottom][i] = 0;
                                targetSet.addAll(List.of(bottom * 7 + i, j * 7 + 1));
                            }
                        }
                    }

                    targetQ.addAll(targetSet);
                    targetSet.clear();

//                    System.out.println(Arrays.deepToString(copiedBoard));
//                    System.out.println("==============================");

                }
            }
            int cnt = 0;
            for (int i = 1; i <= 7; i++) {
                for (int j = 1; j <= 7; j++) {
                    if (copiedBoard[i][j] != 0) {
                        cnt++;
                    }
                }
            }

            min = Math.min(min, cnt);
            if (min == 0) {
                break;
            }
        }

        bw.write(String.valueOf(min));
        bw.flush();
        bw.close();
        br.close();
    }
}