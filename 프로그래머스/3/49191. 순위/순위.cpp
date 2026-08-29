#include <bits/stdc++.h>

using namespace std;

vector<vector<bool>> graph(101, vector<bool>(101));
vector<int> bounds(101, 0);

int solution(int n, vector<vector<int>> results) {
    int answer = 0;
    /**
    *   N이 100이므로, O(N^3)의 플로이드 워셜을 사용해 한 노드에서 다른 노드까지의 연결성을 파악할 수 있다.
    *   다른 노드들과 연결된 개수가 N-1개인 한 노드는 순위를 특정할 수 있다.
    *   최종적으로 다른 노드에서 한 노드로 들어오는 인바운드 / 한 노드에서 다른 노드로 나가는 아웃바운드 노드의 개수를 합한 것이 n-1인 노드의 개수를 반환한다.
    */
    
    for (auto& result: results) {
        graph[result[0]][result[1]] = true;
    }
    
    for (int k = 1; k <= n; k++) {
        for (int i = 1; i <= n; i++) {
            // 트리이므로, 후보 경로의 i-k 부분이 연결되어 있지 않으면, 이후 모든 k-j 경로는 고려하지 않아도 된다.
            if (!graph[i][k]) {
                continue;
            }
            for (int j = 1; j <= n; j++) {
                if (graph[i][k] && graph[k][j]) {
                    graph[i][j] = true;   
                }
            }
        }
    }
    
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (graph[i][j]) {
                bounds[i]++;
                bounds[j]++;
            }
        }
    }
    
    for (int i = 1; i <= n; i++) {
        if (bounds[i] == n - 1) {
            answer++;
        }
    }
    
    return answer;
}