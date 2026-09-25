#include <bits/stdc++.h>

using namespace std;

int solution(int n, int s, int a, int b, vector<vector<int>> fares) {
    int answer = INT_MAX;
    /**
    *   S에서 각각 A, B로 향할 때, 최소 비용으로 연결하는 것은
    *   A(혹은 B)에서 S를 지나 B(혹은 A)에 도착하는 최단경로를 계산하는 것과 동일하다.
    *   따라서 플로이드 워셜을 통해, 각 지점에서 다른 모든 지점까지의 최단거리를 계산한다.
    *   이후 모든 지점 X에 대해 A-X, S-X, B-X 를 더한 값의 최솟값을 반환한다.
    */
    
    // i에서 j로 c의 비용의 간선을 가지는 인접배열
    vector<vector<int>> map (n + 1, vector<int>(n + 1, INT_MAX));
    
    for (int i = 1; i <= n; i++) {
        map[i][i] = 0;
    }
    
    for (auto& fare: fares) {
        int x = fare[0], y = fare[1], cost = fare[2];
        
        map[x][y] = cost;
        map[y][x] = cost;
    }
    
    for (int k = 1; k <= n; k++) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (map[i][k] != INT_MAX && map[k][j] != INT_MAX && map[i][k] + map[k][j] < map[i][j]) {
                    map[i][j] = map[i][k] + map[k][j];
                }
            }
        }
    }
    
    for (int i = 1; i <= n; i++) {
        answer = min(map[a][i] + map[s][i] + map[b][i], answer);
    }
    return answer;
}