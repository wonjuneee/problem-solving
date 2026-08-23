#include <bits/stdc++.h>

using namespace std;

string solution(string number, int k) {
    string answer = "";
    /**
    *   숫자의 앞에서부터 탐색하며, 이전 숫자보다 큰 숫자가 나오면 해당 큰 숫자를 추가한다.
    *   1. 덱에 숫자를 하나 씩 집어넣는다.
    *   2. 현재 탐색하는 숫자보다 작은 숫자들을 제거한 뒤, 현재 숫자를 덱에 넣는다.
    *   3. 제거할 숫자가 더 이상 없으면, 즉 k == 0이 되면, number에서 탐색하지 않는 수를 그대로 덱에 넣는다.
    *   4. 덱의 앞에서부터 answer를 구성한다.
    */

    deque<long> dq;
    string remain = "";
    for (int i = 0; i < number.size(); i++) {
        if (k == 0) {
            remain += number.substr(i, number.size());
            break;
        }
        
        while (!dq.empty() && k > 0) {
            if (dq.back() < number[i] - '0') {
                dq.pop_back();
                k--;
            } else {
                break;
            }
        }
        dq.push_back(number[i] - '0');
    }
    // ([4, 3, 2, 1], 2)처럼 number의 끝에 위치한 숫자들이 제거되어야 하는 경우,
    // 끝의 숫자들보다 큰 숫자가 등장하지 않으므로 k가 0이 아닌 상태로 dq가 반환된다
    // 따라서 k가 0이 될 떄까지 pop_back()을 진행해야 한다.
    while (k-- > 0) {
        dq.pop_back();
    }
    
    while (!dq.empty()) {
        answer += to_string(dq.front());
        dq.pop_front();
    }
    answer += remain;
    
    return answer;
}