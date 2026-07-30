import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

class Solution {
    public int solution(int n, int[][] wires) {
        int answer = n;
        
        /**
        *   Union-Find로 주어진 wires 중 하나를 연결하지 않아 2개의 네트워크로 구분한다.
        *   각각의 연결되지 않은 wire 케이스에 대해, 송전탑 개수 차이의 최소값을 반환한다.
        */
        
        int[] root = new int[n + 1];
        int[] rank = new int[n + 1];

        for (int i = 0; i < wires.length; i++) {
            
            for (int j = 1; j <= n; j++) {
                root[j] = j;
                rank[j] = 1;
            }
            
            for (int j = 0; j < wires.length; j++) {
                if (i == j) {
                    continue;
                }   
                union(root, rank, wires[j][0], wires[j][1]);   
            }
            answer = Math.min(answer, Math.abs(n - rank[find(root, 1)] * 2));            
        }
        
        return answer;
    }
    
    void union(int[] root, int[] rank, int a, int b) {
        int rootA = find(root, a);
        int rootB = find(root, b);
        
        if (rootA == rootB) {
            return;
        }
        
        if (rank[rootA] <= rank[rootB]) {
            root[rootA] = rootB;
            rank[rootB] += rank[rootA];
        } else {
            root[rootB] = rootA;
            rank[rootA] += rank[rootB];
        }
    }
    
    int find(int[] root, int a) {
        if (root[a] == a) {
            return a;
        }
        
        root[a] = find(root, root[a]);
        return root[a];
    }
}