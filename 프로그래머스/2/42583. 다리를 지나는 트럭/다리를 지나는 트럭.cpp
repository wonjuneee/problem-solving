#include <bits/stdc++.h>

using namespace std;

void print_set(unordered_map<int, int>& tbset) {
    for (int i = 0; i < tbset.size(); i++) {
        cout << i << ": " << tbset[i] << ", ";
    }
    cout << '\n';
}

int solution(int bridge_length, int weight, vector<int> truck_weights) {
    int answer = 0;
    /**
    *   각 트럭이 몇 초동안 다리 위에 있었는지, 해시맵으로 기록한다.
    *   bridge_length 동안 있었던 트럭은 다리를 모두 건넌 것으로, 다리에서 내려온 뒤 bridge_length + 1의 value를 가진다.
    *   1초마다 트럭을 다리에 올리고, 이 개수를 right - left + 1로 관리한다.
    *   마지막 트럭의 해시맵 value가 bridge_length + 1이 되기 전까지 이 과정을 반복한다.
    */
    
    unordered_map<int, int> truck_bridge_set;
    int n = truck_weights.size();
    
    for(int i = 0; i < n; i++) {
        truck_bridge_set.insert({i, 0});
    }
    
    // left, right - 1: 다리를 건너는 중인 첫/마지막 트럭
    int left = 0, right = 1, sum = truck_weights[0];
    truck_bridge_set[0]++;
    answer = 1;
    while (truck_bridge_set[n - 1] < bridge_length) {
        if (right - left <= bridge_length && sum + (right < truck_weights.size() ? truck_weights[right] : 10000) <= weight) {
            if (right < truck_weights.size()) {
                sum += truck_weights[right++];                
            }
            
            for (int i = left; i < right; i++) {
                if (++truck_bridge_set[i] == bridge_length) {
                    sum -= truck_weights[left++];
                }
            }
            answer++;
        } else {
            int gap = bridge_length - truck_bridge_set[left];
            // int gap = bridge_length - (right - left);
            for (int i = left; i < right; i++) {
                truck_bridge_set[i] += gap;
            }
            sum -= truck_weights[left++];
            answer += gap;
        }
        // cout << "(" << left << ", " << right << ") - " << sum << ", "<< answer << '\n';
        // print_set(truck_bridge_set);
    }
    
    // 마지막 트럭이 다리를 빠져나오는 1초
    return answer + 1;
}