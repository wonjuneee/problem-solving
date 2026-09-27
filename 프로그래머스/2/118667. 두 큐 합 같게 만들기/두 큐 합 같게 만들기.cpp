#include <bits/stdc++.h>

using namespace std;

int solution(vector<int> queue1, vector<int> queue2) {
    int answer = INT_MAX;
    /**
    *   큐 1, 2를 원으로 이어서 합이 절반으로 나누어지는 때의 인덱스를 파악해야 한다.
    *   투 포인터를 활용하여 원을 한 바퀴 돌 때까지(left == 2 * size) 가능한 모든 경우의 수를 파악하고, 그 중 최솟값을 반환한다.
    */
    
    long total = 0;
    for (auto& v: queue1) {
        total += v;
    }
    for (auto& v: queue2) {
        total += v;
    }
    
    // 두 큐의 합이 짝수가 아니면, 그 합을 같게 만들 수 없으므로 -1 반환
    if (total % 2 == 1) {
        return -1;
    }
    
    int left = 0, right = 0, size = queue1.size();
    long half = total / 2, sum = 0;
    
    do {
        if (sum < half) {
            if (right >= size) {
                sum += queue2[right - size];
            } else {
                sum += queue1[right];
            }
            right++;
            
            if (right == 2 * size) {
                right = 0;
            }            
        } else if (sum > half) {
            if (left >= size) {
                sum -= queue2[left - size];
            } else {
                sum -= queue1[left];
            }
            left++;
        } else if (sum == half) {
            int tmp = 0;
            if (left >= size) {
                tmp += size + left - size;
                sum -= queue2[left - size];
            } else {
                tmp += left;
                sum -= queue1[left];
            }
            left++;
            
            if (right >= size) {
                tmp += right - size;
            } else {
                tmp += size + right - size;
            }
            
            answer = min(tmp, answer);
        }
    } while (left < right);
    
    if (answer == INT_MAX) {
        return -1;
    }
    
    return answer;
}