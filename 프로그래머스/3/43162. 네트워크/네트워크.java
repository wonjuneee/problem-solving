import java.io.*;
import java.util.*;

class Solution {
    
    static final int[] root = new int[200];
    static final int[] rank = new int[200];
    
    public int solution(int n, int[][] computers) {
        int answer = 0;
        
        // MakeSet(x), 모든 노드의 Rank를 1로 초기화
        for (int i = 0; i < n; i++) {
            root[i] = i;
            rank[i] = 1;
        }
        
        // (j=i) 이상인 케이스만 처리하여 (무향그래프처럼), 연산횟수 줄이기
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (computers[i][j] == 1) {
                    union(i, j);
                }
            }
        }
        
        // 집합의 개수와 연결되는 root 노드는 항상 자기자신을 가르키므로, 집합 개수 계산에 활용 가능
        for (int i = 0; i < n; i++) {
            if (root[i] == i) {
                answer += 1;
            }
        }
        
        return answer;
    }
    
    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        
        if (rootA == rootB) return;
        
        /* 
            Union-By-Rank
            높이(Rank)가 낮은 집합을 높이가 높은 집합 아래로 연결
        */
        if (rank[rootA] < rank[rootB]) {
            root[rootA] = rootB;
        } else {
            root[rootB] = rootA;
            if (rank[rootA] == rank[rootB]) {
                rank[rootA] += 1;
            }
        }
    }
    
    public int find(int a) {
        if (root[a] == a) {
            return a;
        }
        
        root[a] = find(root[a]);
        return root[a];
    }
}