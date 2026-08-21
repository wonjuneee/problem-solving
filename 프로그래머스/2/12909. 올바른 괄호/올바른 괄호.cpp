#include <bits/stdc++.h>

using namespace std;

bool solution(string s)
{
    bool answer = true;

    stack<char> stc;
    for (int i = 0; i < s.size(); i++) {
        char bracket = s[i];
        
        if (!stc.empty() && stc.top() == '(' && bracket == ')') {
            stc.pop();
        } else {
            stc.push(bracket);
        }
    }
    
    answer = stc.empty();

    return answer;
}