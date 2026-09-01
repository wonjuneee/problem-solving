#include <bits/stdc++.h>

using namespace std;

vector<int> parents(100);
int find(int a) {
    if (a == parents[a]) {
        return a;
    }
    
    parents[a] = find(parents[a]);
    return parents[a];
}

void union_set(int a, int b) {
    int root_a = find(a);
    int root_b = find(b);
    
    if (root_a == root_b) {
        return;
    }
    
    if (root_a < root_b) {
        parents[root_b] = root_a; 
    } else {
        parents[root_a] = root_b;
    }
}

int solution(int n, vector<vector<int>> costs) {
    int answer = 0;
    /**
    *   최소 신장 트리를 활용해, 그래프를 구성하는 가장 작은 건설 비용을 계산할 수 있다.
    *   1. 간선의 비용을 오름차순으로 정렬
    *   2. 반복문을 통해 비용이 가장 작은 간선을 선택한다.
    *       - 간선의 두 정점이 서로 다른 네트워크인 경우 해당 간선을 선택할 수 있다.
    *       - 이미 동일 네트워크에 속한 두 정점을 잇는 간선인 경우 선택하지 않는다.
    *       Union-Find를 통해 두 정점이 같은 네트워크에 속하는지 판단하고, 하나의 네트워크로 합칠 수 있다.
    *   3. N개의 정점이 사이클 없이 연결되도록 N-1개의 간선이 선택되면 반복문을 중단한다.
    */
    
    unordered_set<int> graph;
    sort(costs.begin(), costs.end(), [](const auto& a, const auto& b) {
        return a[2] < b[2]; // cost 기준 오름차순 정렬
    });
    
    for (int i = 0; i < n; i++) {
        parents[i] = i;
    }
    
    int  edge = 0;
    for (auto& curr: costs) {
        if (edge == n - 1) {
            break;
        }
        
        int a = curr[0], b = curr[1], cost = curr[2];
        // 서로 다른 그래프에 속하는 경우
        if (find(a) != find(b)) {
            answer += cost;
            edge++;
            union_set(a, b);
        }
    }
    
    return answer;
}