#include <bits/stdc++.h>

using namespace std;

int solution(vector<int> people, int limit) {
    int answer = 0;
    /**
    *   몸무게를 오름차순으로 정렬한 뒤, 투 포인터를 활용해 limit에 최대한 가까운 쌍의 개수를 도출한다.
    *   만일 두 몸무게의 합이 limit을 초과하면, 둘 중 큰 몸무게를 먼저 보트에 태운다.
    */
    
    sort(people.begin(), people.end());
    int left = 0, right = people.size() - 1;
    
    while (left <= right) {
        if (people[left] + people[right] <= limit) {
            left++;
            right--;
        } else {
            right--;
        }
        
        answer++;
    }
    
    return answer;
}