import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final List<Integer>[] orders = new ArrayList[32001];
    static final int[] inDegrees = new int[32001];
    static final List<Integer> result = new ArrayList<>();

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 2252 줄 세우기
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            orders[i] = new ArrayList<>();
        }

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int shorter = Integer.parseInt(st.nextToken());
            int longer = Integer.parseInt(st.nextToken());

            orders[shorter].add(longer);
            inDegrees[longer] += 1;
        }

        topology();

        StringBuilder sb = new StringBuilder();
        for (int node : result) {
            sb.append(node).append(' ');
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void topology() {
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (inDegrees[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int currNode = q.poll();
            if (inDegrees[currNode] == 0) {
                result.add(currNode);
            }

            for (int nextNode : orders[currNode]) {
                inDegrees[nextNode]--;
                if (inDegrees[nextNode] == 0) {
                    q.add(nextNode);
                }
            }
        }
    }
}