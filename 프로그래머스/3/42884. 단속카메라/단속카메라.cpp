#include <bits/stdc++.h>

using namespace std;

int solution(vector<vector<int>> routes) {
    int answer = 0;
    /**
    *   나간지점을 기준으로 오름차순 정렬을 한 뒤, 첫 원소와 겹치는 원소들을 묶는다.
    *   마지막으로 묶이는 원소의 다음 원소부터 다시 겹치는 원소를 찾는다.
    *   이것을 반복하여 몇 개의 그룹으로 원소들을 묶을 수 있는지 반복문을 1회 수행한다.
    */
    
    sort(routes.begin(), routes.end(), [](const vector<int>& a, const vector<int>& b) {
        return tie(a[1], a[0]) < tie(b[1], b[0]);
    });
    
    int boundary = routes[0][1];
    answer++;
    for (auto& route: routes) {
        // 그룹의 첫 원소(자동차)가 고속도로를 빠져나간 시점보다 현재 원소(route)가 고속도로를 진입한 시점이 늦으면, 두 원소는 한 그룹으로 묶이지 않으므로 새 그룹을 시작한다.
        if (route[0] > boundary) {
            answer++;
            boundary = route[1];
        }
    }
    
    return answer;
}