import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = 0;
        
        /**
        *   플로이드 워셜을 활용해 각 지점 간의 최단거리를 계산한다.
        *       - n의 최대값은 200이므로, O(n^3)의 알고리즘도 충분히 활용할 만 하다.
        *   S에서 특정 지점까지의 거리 + 해당 지점에서 각각 A, B까지의 거리를 합한 값의 최소값을 반환한다.
        */
        int[][] cost = new int[n + 1][n + 1];
        
        for (int i = 1; i <= n; i++) {
            Arrays.fill(cost[i], -1);
            cost[i][i] = 0;
        }
        
        for (int[] fare: fares) {
            cost[fare[0]][fare[1]] = fare[2];
            cost[fare[1]][fare[0]] = fare[2];
        }

        floydWarshall(cost, n);
        
        answer = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            // 모든 경로가 연결되어 있어야 한다.
            if (cost[s][i] > -1 && cost[i][a] > -1 && cost[i][b] > -1) {
                answer = Math.min(cost[s][i] + cost[i][a] + cost[i][b], answer);
            }
        }
        
        return answer;
    }
    
    void floydWarshall(int[][] cost, int n) {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    // k를 경유하는 i-j 경로가 연결되어 있지 않은 케이스
                    if (cost[i][k] == -1 || cost[k][j] == -1) {
                        continue;
                    }
                    // k 경유 경로가 존재하나, 아직 갱신되지 않은 경우
                    if (cost[i][j] == -1) {
                        cost[i][j] = cost[i][k] + cost[k][j];
                    }
                    // k 경유 경로가 존재하고, 최소 1회 갱신된 경우
                    else {
                        cost[i][j] = Math.min(cost[i][j], cost[i][k] + cost[k][j]);
                    }
                }
            }
        }
    }
}