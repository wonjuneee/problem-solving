#include <bits/stdc++.h>

using namespace std;

vector<int> solution(vector<string> operations) {
    vector<int> answer = { 0, 0 };
    /**
    *   map을 활용해 key를 기준으로 내림차순 정렬한다.
    *   이때 value로 해당 key가 큐에 존재하는 개수를 기록한다.
    */
    
    // 내림차순 정렬
    map<int, int, greater<int>> tree;
    
    for (auto& operation: operations) {
        stringstream ss(operation);
        string op, value;
        getline(ss, op, ' ');
        getline(ss, value, ' ');
        int v = stoi(value);
        
        switch (op[0]) {
            case 'I': {
                auto it = tree.find(v);
                if (it == tree.end()) {
                    tree.insert({ v, 1 });
                } else {
                    it->second++;
                }
                break;
            }
            case 'D': {
                if (tree.empty()) {
                    continue;
                }
                
                // auto는 선언자가 필요하므로, 기본적으로 tree.begin()으로 초기화
                auto it = tree.begin();
                if (v == -1) {
                    it = prev(tree.end());
                    // --it;
                }
                
                if (it->second == 1) {
                    tree.erase(it);
                } else {
                    it->second--;
                }
                break;
            }
            default:
                break;
        }
    }
    
    if (tree.empty()) {
        answer = {0, 0};
    } else {
        auto it = tree.begin();
        if (it == prev(tree.end())) {
            answer[0] = answer[1] = it->first;
        } else {
            answer[0] = tree.begin()->first;
            answer[1] = prev(tree.end())->first;
        }
    }
    
    return answer;
}