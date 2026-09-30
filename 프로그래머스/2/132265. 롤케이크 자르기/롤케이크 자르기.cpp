#include <bits/stdc++.h>

using namespace std;

int solution(vector<int> topping) {
    int answer = 0;
    /**
    *   각 토핑의 개수를 unordered_map으로 계산한다.
    *   왼쪽부터 한 칸씩 토핑 종류를 map으로 추적하여, 좌우 개수가 같으면 answer + 1을 수행한다.
    */
    unordered_map<int, int> leftTopping, rightTopping;
    for (int i = 0; i < topping.size(); i++) {
        rightTopping[topping[i]]++;
    }
    
    for (int i = 0; i < topping.size(); i++) {
        int t = topping[i];
        
        leftTopping[t]++;
        rightTopping[t]--;
            
        if (rightTopping[t] == 0) {
            rightTopping.erase(t);
        }
        
        if (leftTopping.size() == rightTopping.size()) {
            answer++;
        }
    }
    
    return answer;
}