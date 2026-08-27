#include <bits/stdc++.h>

using namespace std;

vector<int> solution(vector<int> array, vector<vector<int>> commands) {
    vector<int> answer;
    
    vector<int> originalArr = array;
    for (auto& command: commands) {
        array = originalArr;
        
        sort(array.begin() + command[0] - 1, array.begin() + command[1]);
        answer.push_back(array[command[0] + command[2] - 2]);
    }
    
    return answer;
}