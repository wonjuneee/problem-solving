import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.List;

class Solution {
    public int solution(int n, int[][] edge) {
        int answer = 0; 
        /**
        *   1번 노드에 대해 각 노드까지의 최단거리를 다익스트라를 활용해 구할 수 있다.
        */
        
        int[] cost = new int[n + 1];
        List<Integer>[] map = new ArrayList[n + 1];
        
        Arrays.fill(cost, Integer.MAX_VALUE);
        for (int i = 1; i <= n; i++) {
            map[i] = new ArrayList<>();
        }
        
        for (int[] e: edge) {
            map[e[0]].add(e[1]);
            map[e[1]].add(e[0]);
        }
        
        dijkstra(map, cost);
        
        int max = 0;
        for (int i = 2; i <= n; i++) {
            if (max < cost[i]) {
                answer = 1;
                max = cost[i];
            } else if (max == cost[i]) {
                answer++;
            }
        }
        
        return answer;
    }
    
    void dijkstra(List<Integer>[] map, int[] cost) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((i1, i2) -> i1[1] - i2[1]);
        pq.add(new int[]{ 1, 0 });
        
        while (!pq.isEmpty()) {
            int[] currNode = pq.poll();
            int currValue = currNode[0], currCost = currNode[1];
            
            if (currCost < cost[currValue]) {
                cost[currValue] = currCost;
                
                for (int nextValue: map[currValue]) {
                    int nextCost = cost[nextValue];
                    if (nextCost > currCost + 1) {
                        pq.add(new int[]{ nextValue, currCost + 1 });
                    }
                }
            }
        }
    }
}