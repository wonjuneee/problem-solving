import java.io.*;
import java.util.*;

public class Main {

    static int n, m;

    static final int[] SUM_OF_DEGREE = new int[501];
    static final List<Integer>[] IN_DEGREE_LINKED_LIST = new ArrayList[501];
    static final List<Integer>[] OUT_DEGREE_LINKED_LIST = new ArrayList[501];
    static final boolean[] isVisited = new boolean[501];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 2458 키 순서
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            IN_DEGREE_LINKED_LIST[i] = new ArrayList<>();
            OUT_DEGREE_LINKED_LIST[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int in = Integer.parseInt(st.nextToken());
            int out = Integer.parseInt(st.nextToken());

            // 각 노드의 in/out degree인 노드 지정
            IN_DEGREE_LINKED_LIST[out].add(in);
            OUT_DEGREE_LINKED_LIST[in].add(out);
        }

        for (int node = 1; node <= n; node++) {
            SUM_OF_DEGREE[node] = topology(node);
        }

        // 자기 자신을 제외한 모든 노드 (n-1)개를 방문하면 본인의 키순서 파악 가능
        int result = 0;
        for (int node = 1; node <= n; node++) {
            if (SUM_OF_DEGREE[node] == n - 1) {
                result++;
            }
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     *  BFS를 기반으로 정방향/역방향으로 향할 수 있는 노드 개수 합산
     *  isVisited 배열을 통해 중복 방문 방지
     */
    static int topology(int node) {
        Arrays.fill(isVisited, false);
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(node);

        int count = 0;
        while (!q.isEmpty()) {
            int currNode = q.poll();

            List<Integer> outDegreeList = OUT_DEGREE_LINKED_LIST[currNode];
            for (int outDegree : outDegreeList) {
                if (!isVisited[outDegree]) {
                    q.add(outDegree);
                    count++;
                }
                isVisited[outDegree] = true;
            }
        }

        q.offer(node);
        while (!q.isEmpty()) {
            int currNode = q.poll();

            List<Integer> inDegreeList = IN_DEGREE_LINKED_LIST[currNode];
            for (int inDegree : inDegreeList) {
                if (!isVisited[inDegree]) {
                    q.add(inDegree);
                    count++;
                }
                isVisited[inDegree] = true;
            }
        }

        return count;
    }
}