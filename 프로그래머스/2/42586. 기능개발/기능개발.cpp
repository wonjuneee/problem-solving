#include <bits/stdc++.h>

using namespace std;

vector<int> solution(vector<int> progresses, vector<int> speeds) {
    vector<int> answer;
    /**
    *   뒤에 있는 기능은 반드시 앞에 있는 작업이 모두 완료되어야 한다.
    *   따라서 앞에 완료한 작업의 수가 덱에 저장되어 있으면,
    *       1. 뒤의 작업이 앞의 작업보다 빠른 시간이 소요된다면 직전 배포 그룹의 기능 수를 +1 한다.
    *       2. 뒤의 작업이 앞의 작업보다 오랜 시간이 소요된다면 덱에 새로운 배포 그룹(cnt = 1)을 덱에 넣는다.
    */
    
    deque<int> dq;
    int prev = 0;
    for (int i = 0; i < progresses.size(); i++) {
        int progress = progresses[i], speed = speeds[i];
        
        int day = (100 - progress) / speed;
        if (day * speed + progress < 100) {
            day++;
        }
        if (day <= prev) {
            int cnt = dq.back();
            dq.pop_back();
            dq.push_back(cnt + 1);
        } else {
            prev = day;
            dq.push_back(1);
        }
    }
    while (!dq.empty()) {
        answer.push_back(dq.front());
        dq.pop_front();
    }
    
    return answer;
}