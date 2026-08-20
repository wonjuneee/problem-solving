#include <string>
#include <vector>
#include <iostream>
#include <bits/stdc++.h>

using namespace std;

int solution(int k, vector<vector<int>> dungeons) {
    int answer = -1;
    /**
    *   던전 방문 순서의 모든 순열에 대해 순회를 돌며, 최대 방문 가능한 경우의 수를 찾는다.
    */
    
    sort(dungeons.begin(), dungeons.end());
    do {
        int curr = k;
        for (int i = 0; i <= dungeons.size(); i++) {
            if (i == dungeons.size()) {
                answer = i;
                break;
            }
            
            if (curr >= dungeons[i][0]) {
                curr -= dungeons[i][1];
            } else {
                answer = max(i, answer);
                break;
            }
        }
    } while (next_permutation(dungeons.begin(), dungeons.end()));
    
    return answer;
}
