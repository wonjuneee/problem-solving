#include <bits/stdc++.h>

using namespace std;

int solution(int bridge_length, int weight, vector<int> truck_weights) {
    int answer = 0;
    /**
    *   큐에 트럭의 무게를 순차적으로 넣는다.
    *   다리 길이만큼 값이 들어간 이후부터, 가장 앞 트럭은 큐에서 제거하고 새로운 트럭을 큐에 삽입한다.
    *       - {트럭 무게, 다리를 빠져나갈 시간}을 저장하여, 무게 제한으로 트럭이 더 이상 진입하지 못하면 맨 앞의 트럭의 빠져나갈 시간으로 시간을 건너뛴다.
    *   무게 제한으로 인해 트럭이 더 이상 다리에 진입하지 못하면, 시간을 건너뛴 뒤 다시 무게제한을 검토한다.
    */
    
    queue<pair<int, int>> q;
    
    int currTruck = 0, sum = 0;
    while (currTruck < truck_weights.size()) {
        // 다리를 탈출하는 시간이 도달하면, 맨 앞의 트럭을 큐에서 제거한다.
        if (!q.empty() && q.front().second == answer) {
            sum -= q.front().first;
            q.pop();
        }
        
        // 1. 다리에 새로운 트럭이 올라갈 수 있는 조건: 다리 위 트럭 무게 합산 & 큐에 { 트럭 무게, 다리 탈출 시각 } 저장
        // 2. 무게 제한 등, 다리에 트럭이 더 이상 올라갈 수 없는 조건: 맨 앞의 트럭이 빠져나간 이후 새로운 상태에서 조건을 탐색해야 하므로 시간을 맨 앞 트럭이 다리를 탈출하기 직전 시간으로 건너뛴다.
        if (q.empty() || (q.size() < bridge_length && sum + truck_weights[currTruck] <= weight)) {
            sum += truck_weights[currTruck];
            q.push({ truck_weights[currTruck++], answer + bridge_length });
            answer++; // (answer + 1)인 타이밍 때의 상태를 모두 적용한다.
        } else {
            answer = q.front().second;
        }
        
        // cout << answer << ": ";
        // for (int i = 0; i < q.size(); i++) {
        //     cout << "(" << q.front().first << ", " << q.front().second << ") ";
        //     q.push(q.front());
        //     q.pop();
        // }
        // cout << '\n';
    }
    
    // 마지막 트럭이 다리를 완전히 통과하는 초 추가
    return answer + bridge_length;
}