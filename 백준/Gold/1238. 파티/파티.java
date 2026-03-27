import java.io.*;
import java.util.*;

public class Main {

    static int n, m, x;

    static final List<int[]>[] map = new ArrayList[1001];
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
        }

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            map[start].add(new int[]{ end, weight });
        }

        for (int i = 1; i <= n; i++) {
            // 매 반복마다, 각 노드로부터의 최단거리를 저장하기 위한 배열을 정수 최대값으로 초기화
            Arrays.fill(minTime, Integer.MAX_VALUE);
            int time = dijkstra(i);

            // 시작노드가 목표노드인 경우, 목표노드에서 각 노드로의 최단거리(돌아오는 시간)을 각 노드의 종합시간에 더해준다.
            if (i == x) {
                for (int j = 1; j <= n; j++) {
                    totalTime[j] += minTime[j];
                }
            } else {
                totalTime[i] += time;
            }
        }

        int result = 0;
        for (int i = 1; i <= n; i++) {
            result = Math.max(totalTime[i], result);
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static int dijkstra(int start) {
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

        // start 노드에서 목표노드 x까지의 최단거리를 반환
        return minTime[x];
    }
}