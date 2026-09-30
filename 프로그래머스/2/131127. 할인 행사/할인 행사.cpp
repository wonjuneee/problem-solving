#include <bits/stdc++.h>

using namespace std;

int solution(vector<string> want, vector<int> number, vector<string> discount) {
    int answer = 0;
    /**
    *   unordered_map을 사용해 원하는 물건의 개수를 기록한다.
    *   discount를 처음부터 탐색하며, 카트의 물건을 모두 채울 수 있는지 판단한다.
    *   want로 주어지는 물건은 최대 10개이고, discount의 크기는 최대 100,000 이므로
    *   매 번 10개의 물건이 모두 위시리스트에 담기는지 판단하는 횟수는 1,000,000 이다.
    */
    
    unordered_map<string, int> wishlist;
    for (int i = 0; i < want.size(); i++) {
        wishlist[want[i]] = number[i];
    }
    
    for (int i = 0; i < discount.size(); i++) {
        if (i >= 10) {
            wishlist[discount[i - 10]]++;
        }
        
        wishlist[discount[i]]--;
        
        // 선제적으로 answer + 1을 수행한 뒤, 하나라도 위시리스트를 채우지 못한 경우 다시 -1로 롤백한다.
        answer++;
        for (auto& [key, value]: wishlist) {
            if (value != 0) {
                answer--;
                break;
            }
        }
    }
    
    return answer;
}