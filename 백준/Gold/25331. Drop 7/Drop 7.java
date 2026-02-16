import java.io.*;
import java.util.*;

public class Main {

    static final int N = 7;

    /* 7*7 보드로, 바깥쪽에 값이 0인 padding을 위치시킴 */
    static final int[][] BOARD = new int[N + 2][N + 2];
    /* 각 열마다 공을 떨어뜨렸을 때 공의 위치 (각 열의 맨 위 칸) */
    static final int[] TOP = new int[N + 1];
    /* 매 상황마다 지워져야 하는 공의 위치 - 한 상황이 끝나면 초기화되어야 함 */
    static final boolean[][] NEED_TO_DELETE = new boolean[N + 1][N + 1];
    /* 각 컬럼에 공을 떨어뜨릴 때마다 원본 배열을 복사해 사용하여, 원본 배열이 망가지지 않도록 해야 함 */
    static final int[][] COPIED_BOARD = new int[N + 2][N + 2];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 25331 Drop 7
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st;

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int ball = Integer.parseInt(st.nextToken());
                BOARD[i][j] = ball;
                if (ball == 0) {
                    TOP[j] = i;
                }
            }
        }
        int ball = Integer.parseInt(br.readLine());
        int min = Integer.MAX_VALUE;

        // 각 열마다 공 떨어뜨려보기
        for (int col = 1; col <= N; col++) {
            int row = TOP[col];

            /*
                원본 보드는 항상 유지해야 하므로 Deep Copy를 진행한다.
                이때, 이차원 배열 전체를 System.arraycopy()의 인자로 넣게되면, 각 일차원 배열의 참조값만 넘어가므로 Shallow Copy가 발생한다.
                따라서 for문을 통해 각 일차원 배열마다 Deep Copy가 진행되도록 한다.
             */
            for (int i = 1; i <= N; i++) {
                System.arraycopy(BOARD[i], 0, COPIED_BOARD[i], 0, BOARD[i].length);
            }
            COPIED_BOARD[row][col] = ball;

            // 큐를 깔끔하게 유지하기 위해 값에서 row, col 값을 추출하는 방식 활용
            Queue<Integer> targetQ = new ArrayDeque<>();
            targetQ.add(row * N + col);

            while (!targetQ.isEmpty()) {
                int target = targetQ.poll();
                int targetRow = (target - 1) / N;
                int targetCol = target % N == 0 ? N : target % N;
                // System.out.println("TARGET: " + targetRow + ", " + targetCol);

                /* 좌표를 기준으로 row, col 각각의 그룹을 확인하는 과정 */
                checkRow(targetRow);
                // System.out.println(Arrays.deepToString(NEED_TO_DELETE));
                checkCol(targetCol);
                // System.out.println(Arrays.deepToString(NEED_TO_DELETE));

                /*
                    큐가 빈 것은 하나의 상황이 끝난 것을 의미하므로 이후 프로세스 진행
                    1. 지워져야 할 공 일괄 삭제
                    2. 각 열에서 빈 공안으로 공 채워넣기 (중력 적용)
                    3. 변화가 발생한 칸들을 한 번에 큐에 적재
                 */
                if (targetQ.isEmpty()) {

                    // 지울 공이 없는 경우, 이후에도 더 이상 변화가 없는 것이 자명하므로 바로 루프 종료
                    if (deleteBalls() == 0) {
                        break;
                    }

                    // 중복된 값이 큐에 적재되지 않도록 해시셋을 통해 좌표값 저장 후 일괄적으로 큐에 적재
                    Set<Integer> targetSet = applyGravity();

                    targetQ.addAll(targetSet);
                    targetSet.clear();

                    // System.out.println(Arrays.deepToString(COPIED_BOARD));
                    // System.out.println("==============================");
                }
            }

            // 공이 존재하는 위치 카운트
            int cnt = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (COPIED_BOARD[i][j] != 0) {
                        cnt++;
                    }
                }
            }
            min = Math.min(min, cnt);
            if (min == 0) { // 최소값을 구하므로 0이 나오면 바로 반환
                break;
            }
        }

        bw.write(String.valueOf(min));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     1. i+1칸(다음 칸)을 바라보고, 해당 칸에 공이 존재할 경우 groupSize+1 <p>
     2. 다음 칸에 공이 없을 경우, 현재 칸까지가 하나의 그룹이므로 그룹 내에 그룹 크기와 같은 값의 공을 확인하여 저장 <p>
     이후 groupSize를 다시 0으로 초기화
     */
    static void checkRow(int targetRow) {
        int groupSize = 0;
        for (int i = 0; i <= N; i++) {
            if (COPIED_BOARD[targetRow][i + 1] != 0) {
                groupSize++;
            } else if (COPIED_BOARD[targetRow][i + 1] == 0 && groupSize != 0) {
                for (int j = i + 1; j > i - groupSize; j--) {
                    if (COPIED_BOARD[targetRow][j] == groupSize) {
                        NEED_TO_DELETE[targetRow][j] = true;
                    }
                }
                groupSize = 0;
            }
        }
    }

    static void checkCol(int targetCol) {
        int groupSize = 0;
        for (int i = 0; i <= N; i++) {
            if (COPIED_BOARD[i + 1][targetCol] != 0) {
                groupSize++;
            } else if (COPIED_BOARD[i + 1][targetCol] == 0 && groupSize != 0){
                for (int j = i + 1; j > i - groupSize; j--) {
                    if (COPIED_BOARD[j][targetCol] == groupSize) {
                        NEED_TO_DELETE[j][targetCol] = true;
                    }
                }
                groupSize = 0;
            }
        }
    }

    static int deleteBalls() {
        int cnt = 0;
        for (int i = 1; i <= N; i++) { // row
            for (int j = 1; j <= N; j++) { // col
                if (NEED_TO_DELETE[i][j]) {
                    COPIED_BOARD[i][j] = 0;
                    cnt++;
                }
            }
            Arrays.fill(NEED_TO_DELETE[i], false);
        }

        return cnt;
    }

    static Set<Integer> applyGravity() {
        Set<Integer> nextTargetSet = new HashSet<>();

        for (int i = 1; i <= N; i++) { // col
            // 현재 row(j)의 위로, 가장 아래에 위치하는 공 (즉, 0이 아닌 값 중 가장 아래에 있는 값)
            int bottom = N;
            for (int j = N; j >= 1; j--) { // row
                if (COPIED_BOARD[j][i] != 0) {
                    bottom--;
                } else {
                    /*
                        [i, j]에 공이 없으면, 공이 나올 때까지 bottom 값을 줄이기.
                        0이 아닌 값이 나오면, 그 공을 [i, j]에 넣고, [bottom, j]는 0으로 초기화
                          - IndexOutOfBounds 에러를 방지하기 위해 bottom 값은 0 이상으로만 감소
                        이때, 공이 없어진 위치와 생긴 위치 모두 그룹에 변화가 발생한 것이므로 큐에 적재해야 함
                    */
                    while (bottom > 0 && COPIED_BOARD[--bottom][i] == 0);

                    COPIED_BOARD[j][i] = COPIED_BOARD[bottom][i];
                    COPIED_BOARD[bottom][i] = 0;
                    nextTargetSet.addAll(List.of(bottom * N + i, j * N + i));
                }
            }
        }

        return nextTargetSet;
    }
}