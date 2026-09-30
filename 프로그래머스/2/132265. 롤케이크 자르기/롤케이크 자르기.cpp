#include <bits/stdc++.h>

using namespace std;

int solution(vector<int> topping) {
    int answer = 0;
    /**
    *   좌우에서 한 번 씩, 양쪽 끝에서 해당 인덱스까지의 토핑 개수를 계산하여 저장한다.
    *   1. 각 방향에서 없는 토핑이 새로 발견된 경우, toppingSet에 토핑 번호를 추가하고 이전 케이크의 토핑 개수 + 1을 저장한다.
    *   2. 이미 존재하는 토핑인 경우, 이전 값을 그대로 전달받는다.
    *   최종적으로, (i, i + 1)의 토핑 개수가 동일한 경우를 더한다.
    */
    vector<vector<int>> sumOfTopping (topping.size(), vector<int>(2));
    unordered_set<int> toppingSet;
    toppingSet.insert(topping[0]);
    sumOfTopping[0][0] = 1;    
    for (int i = 1; i < topping.size(); i++) {
        if (!toppingSet.contains(topping[i])) {
            toppingSet.insert(topping[i]);
            sumOfTopping[i][0] = sumOfTopping[i - 1][0] + 1;
        } else {
            sumOfTopping[i][0] = sumOfTopping[i - 1][0];
        }
    }
    
    toppingSet.clear();
    toppingSet.insert(topping[topping.size() - 1]);
    sumOfTopping[topping.size() - 1][1] = 1;
    for (int i = topping.size() - 2; i >= 0; i--) {
        if (!toppingSet.contains(topping[i])) {
            toppingSet.insert(topping[i]);
            sumOfTopping[i][1] = sumOfTopping[i + 1][1] + 1;
        } else {
            sumOfTopping[i][1] = sumOfTopping[i + 1][1];
        }
    }
    for (int i = 0; i < topping.size() - 1; i++) {        
        if (sumOfTopping[i][0] == sumOfTopping[i + 1][1]) {
            answer++;
        }
    }
    
    return answer;
}