#include <bits/stdc++.h>

using namespace std;

int solution(int bridge_length, int weight, vector<int> truck_weights) {
    int answer = 0;
    /**
    *   큐에 트럭의 무게를 순차적으로 넣는다.
    *   다리 길이만큼 값이 들어간 이후부터, 가장 앞 트럭은 큐에서 제거하고 새로운 트럭을 큐에 삽입한다.
    *   무게 제한으로 인해 트럭이 더 이상 다리에 진입하지 못하면, 0을 대신 채워넣어 해당 위치에 트럭이 위치하지 않음을 표시한다.
    */
    
    queue<int> q;
    for (int i = 0; i < bridge_length; i++) {
        q.push(0);
    }
    
    int currTruck = 0, sum = 0;
    while (currTruck < truck_weights.size()) {
        sum -= q.front();
        q.pop();
        
        if (sum + truck_weights[currTruck] <= weight) {
            sum += truck_weights[currTruck];
            q.push(truck_weights[currTruck++]);
        } else {
            q.push(0);
        }
        
        answer++;
    }
    
    // 마지막 트럭이 다리를 완전히 통과하는 초 추가
    return answer + bridge_length;
}