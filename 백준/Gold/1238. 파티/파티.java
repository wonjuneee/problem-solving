import java.io.*;
import java.util.*;

public class Main {

    static int n, m, x;

    static final List<int[]>[] map = new ArrayList[1001];
    static final List<int[]>[] reverseMap = new ArrayList[1001];
    static final int[] minTime = new int[1001];
    static final int[] totalTime = new int[1001];

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * 1238 파티
     */
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            map[i] = new ArrayList<>();
            reverseMap[i] = new ArrayList<>();
        }

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            map[start].add(new int[]{ end, weight });
            reverseMap[end].add(new int[]{ start, weight });
        }

        // 각 노드에서 목표노드 x로의 최단거리
        dijkstra(x, map);
        // 목표노드 x에서 각 노드로의 최단거리
        dijkstra(x, reverseMap);

        int result = 0;
        for (int i = 1; i <= n; i++) {
            result = Math.max(totalTime[i], result);
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    // 정방향, 역방향 그래프 중 하나를 사용해 다익스트라 순회
    static void dijkstra(int start, List<int[]>[] map) {
        Arrays.fill(minTime, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(i1 -> i1[1]));
        pq.add(new int[]{ start, 0 });
        minTime[start] = 0;

        while (!pq.isEmpty()) {
            int[] node = pq.poll();
            int idx = node[0], weight = node[1];

            if (minTime[idx] < weight) continue;

            List<int[]> adjNodes = map[idx];
            for (int[] adjNode : adjNodes) {
                int adjIdx = adjNode[0], adjWeight = adjNode[1];
                int weightSum = weight + adjWeight;
                if (weightSum < minTime[adjIdx]) {
                    minTime[adjIdx] = weightSum;
                    pq.add(new int[]{ adjIdx, weightSum });
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            totalTime[i] += minTime[i];
        }
    }
}