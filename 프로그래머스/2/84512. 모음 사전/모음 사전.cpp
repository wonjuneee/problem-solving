#include <bits/stdc++.h>

using namespace std;

int solution(string word) {
    int answer = 0;
    /**
    *   길이 5 이하의 모든 단어에 대해 완전탐색을 돌릴 수 있다.
    */
    
    vector<char> aeiou = { 'U', 'O', 'I', 'E', 'A' };
    
    stack<string> stc;
    for (char& ch: aeiou) {
        stc.push(string(1, ch));
    }
    
    while (!stc.empty()) {
        string w = stc.top();
        stc.pop();
        answer++;
        
        if (w == word) {
            break;
        }
        
        int size = w.size();
        if (size < 5) {
            for (char& ch: aeiou) {
                w.push_back(ch);
                stc.push(w);
                w.pop_back();
            }
        }
    }
    
    return answer;
}