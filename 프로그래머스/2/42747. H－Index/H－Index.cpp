#include <bits/stdc++.h>

using namespace std;

int compare(int a, int b) {
    return a > b;
}

int solution(vector<int> citations) {
    int answer = 0;
    /**
    *   인용된 횟수 기준 내림차순으로 정렬한 뒤, 각 인덱스에서 인용된 횟수를 판단하여 H-index 값이 될 수 있는지 판단한다.
    */
    
    sort(citations.begin(), citations.end(), compare);
    int size = citations.size();
    for (int i = 0; i < citations.size() - 1; i++) {        
        int index = i + 1;
        int possibleH = min(citations[i], index);
        
        if (possibleH >= citations[i + 1]) {
            answer = max(possibleH, answer);
        }
    }
    
    if (size == min(citations[size - 1], size)) {
        answer = max(min(citations[size - 1], size), answer);
    }
    
    return answer;
}