#include <bits/stdc++.h>

using namespace std;

int solution(int n, vector<vector<int>> edge) {
    int answer = 0;
    /**
    *   간선의 비용이 모두 1이므로, BFS를 통해 탐색 가능하다.
    *   큐에서 1에서부터 같은 비용을 가지는 노드들을 세트로 꺼내 BFS 탐색을 진행한다.
    */
    
    // (i, j)를 연결하는 간선을 표시하는 인접 배열
    vector<vector<bool>> map (n + 1, vector<bool> (n + 1));
    vector<bool> visited (n + 1);
    for (auto& e: edge) {
        int x = e[0], y = e[1];
        
        map[x][y] = true;
        map[y][x] = true;
    }

    queue<int> q;
    q.push(1);
    visited[1] = true;
    
    int count = 1;
    while (!q.empty()) {
        count = q.size();
        answer = count;
        
        // 1로부터의 특정 거리인 노드들만 한 그룹으로 큐에서 꺼낸다.
        while (count-- > 0) {
            int curr = q.front();
            q.pop();
            
            for (int i = 1; i <= n; i++) {
                if (!visited[i] && map[curr][i]) {
                    visited[i] = true;
                    q.push(i);
                }
            }
        }
    }
    
    return answer;
}