#include <bits/stdc++.h>

using namespace std;

int solution(vector<vector<int>> routes) {
    int answer = 0;
    /**
    *   1. 진입지점 2. 나간지점을 기준으로 오름차순 정렬을 한 뒤, 첫 원소와 겹치는 원소들을 묶는다.
    *   마지막으로 묶이는 원소의 다음 원소부터 다시 겹치는 원소를 찾는다.
    *   이것을 반복하여 몇 개의 그룹으로 원소들을 묶을 수 있는지 반복문을 1회 수행한다.
    */
    
    sort(routes.begin(), routes.end(), [](const vector<int>& a, const vector<int>& b) {
        return tie(a[0], a[1]) < tie(b[0], b[1]);
    });
    
    int boundary = routes[0][1];
    answer++;
    for (auto& route: routes) {
        
        // 만일 boundary보다 이전에 고속도로를 나가는 원소가 있을 경우, boundary를 해당 지점으로 당긴다.
        if (route[1] < boundary) {
            boundary = route[1];
        }
        // 그룹의 첫 원소(자동차)가 고속도로를 빠져나간 시점보다 현재 원소(route)가 고속도로를 진입한 시점이 늦으면, 두 원소는 한 그룹으로 묶이지 않으므로 새 그룹을 시작한다.
        else if (route[0] > boundary) {
            answer++;
            boundary = route[1];
        }
    }
    
    return answer;
}