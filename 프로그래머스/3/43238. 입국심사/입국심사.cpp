#include <bits/stdc++.h>

using namespace std;

long long solution(int n, vector<int> times) {
    long long answer = 0;
    /**
    *   N이 10억이므로, O(logN)의 시간복잡도를 가진 로직 혹은 심사관을 기준으로 처리하는 로직을 활용해야 한다.
    *   이분 탐색으로 X((left + right) / 2)초에 N명의 사람을 처리할 수 있는지 여부를 판단한다.
    *       - 범위 내 Lower Bound를 구하므로 left < right 동안 반복문을 수행한다.
    *       - 처리 가능하면 right = X초에도 처리 가능한지 판단한다.
    *       - 처리 불가능하면 left = X + 1초에는 처리 가능한지 판단한다.
    */
    
    long long min = (long long) *min_element(times.begin(), times.end()) * n;
    
    long long left = 0, right = min < 0 ? LLONG_MAX : min;
    while (left < right) {
        long long x = left + (right - left) / 2;
        
        long long sum = 0;
        for (int time: times) {
            sum += x / time;
            
            if (sum >= n) break;
        }
        
        if (sum >= n) {
            right = x;
            answer = x;
        } else {
            left = x + 1;
        }
    }
    
    return answer;
}