import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

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
        
        int max = bfs(map, cost);
        for (int i = 2; i <= n; i++) {
            if (max == cost[i]) {
                answer++;
            }
        }
        
        return answer;
    }
    
    int bfs(List<Integer>[] map, int[] cost) {
        Queue<Integer> q = new ArrayDeque<>();
        cost[1] = 0;
        q.add(1);
        
        int max = 0;
        while (!q.isEmpty()) {
            int currValue = q.poll();
            
            for (int nextValue: map[currValue]) {
                if (cost[nextValue] == Integer.MAX_VALUE) {
                    int nextCost = cost[currValue] + 1;
                    cost[nextValue] = nextCost;
                    q.add(nextValue);
                    
                    max = Math.max(nextCost, max);
                }
            }
        }
        
        return max;
    }
}