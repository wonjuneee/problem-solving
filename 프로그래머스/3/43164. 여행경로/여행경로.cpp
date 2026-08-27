#include <bits/stdc++.h>

using namespace std;

int total_tickets;

bool dfs(unordered_map<string, map<string, int>>& map, string from, vector<string>& answer, int n) {
    if (!map.count(from) && total_tickets != n) {
        return false;
    } else if (total_tickets == n) {
        return true;
    }
    
    for (auto& [to, count]: map[from]) {
        if (count > 0) {            
            answer.push_back(to);
            map[from][to] -= 1;
            if (!dfs(map, to, answer, n + 1)) {
                answer.pop_back();
                map[from][to] += 1;
            } else {
                return true;
            }
        }
    }
    
    return false;
}

vector<string> solution(vector<vector<string>> tickets) {
    vector<string> answer = {"ICN"};
    /**
    *   사전 순서대로 방문 가능한 경로를 탐색해야 하므로, DFS로 경로를 탐색할 수 있다.
    *   한 공항에서 여러 공항으로 향할 수 있을 때 사전 순으로 다음 재귀를 진행하면, 최종 결과 역시 사전 순으로 정렬된다.
    */
    total_tickets = tickets.size();
    
    // 티켓 도착지 기준으로 오름차순 정렬이 되므로, 최종결과도 사전 순으로 정렬될 수 있다.
    unordered_map<string, map<string, int>> map;
    for (auto& ticket: tickets) {
        string from = ticket[0], to = ticket[1];
        
        if (!map.count(from)) {
            map[from][to] = 1;
        } else {
            map[from][to] += 1;
        }
    }
    
    dfs(map, "ICN", answer, 0);
    
    return answer;
}