import java.io.*;
import java.util.*;

public class Main {

    static int n;

    static final int[] towers = new int[100001];
    static final Stack<Tower> stack = new Stack<>();
    static final int[][] results = new int[100001][3]; // 보이는 탑 개수, 가장 가까운 탑 idx, dist

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 22866 탑 보기
     */
    public static void main(String[] args) throws IOException {
        n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            int height = Integer.parseInt(st.nextToken());
            towers[i] = height;
        }

        traverse();

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(results[i][0]).append(' ');
            if (results[i][0] != 0) {
                sb.append(results[i][1]);
            }
            sb.append('\n');
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static class Tower {
        int idx, height;

        public Tower(int idx, int height) {
            this.idx = idx;
            this.height = height;
        }
    }

    static void traverse() {
        // 왼쪽으로 보이는 탑
        for (int i = 1; i <= n; i++) {
            results[i] = scanTower(i);
        }
        stack.clear();

        // 오른쪽으로 보이는 탑
        for (int i = n; i >= 1; i--) {
            int[] result = scanTower(i);

            results[i][0] += result[0];
            // 좌측에 있는 탑이 우선
            if (results[i][2] > result[2]) {
                results[i][1] = result[1];
            }
        }
    }

    // 매 타워를 스택에 넣으며 조건에 따라 처리하면, 각 순회마다 현재 위치에서 보이는 타워만 스택에 남게 된다.
    static int[] scanTower(int currIdx) {
        int currHeight = towers[currIdx];
        while (!stack.isEmpty()) {
            Tower tower = stack.peek();

            // 현재 높이보다 작거나 같으면 스택에서 제거
            if (tower.height <= currHeight) {
                stack.pop();
            }
            // 현재 높이보다 높으면 볼 수 있으므로 반복문 탈출
            else {
                break;
            }
        }
        int[] result;
        if (stack.isEmpty()) {
            result =  new int[]{ 0, 0, n + 1 };
        } else {
            result =  new int[]{ stack.size(), stack.peek().idx, Math.abs(stack.peek().idx - currIdx) };
        }

        stack.push(new Tower(currIdx, currHeight));
        return result;
    }
}