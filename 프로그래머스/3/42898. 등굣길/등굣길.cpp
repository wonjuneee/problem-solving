#include <bits/stdc++.h>

using namespace std;

const int DIV = 1000000007;

int solution(int m, int n, vector<vector<int>> puddles) {
    int answer = 0;
    /**
    *   1. 전체 경우의 수에서 웅덩이를 지나 학교에 도착하는 경우의 수 빼기
    *   2. 한 칸에서 이전 행 / 이전 열에서 오는 경우를 더한다. 이때 웅덩이가 있는 칸은 -1에서 순회 중 0으로 갱신한 뒤, 이후 칸에서 해당 방향의 경우의 수는 0으로 더해지도록 한다.
    */
    
    vector<vector<int>> dp(n + 1, vector<int>(m + 1));
    
    for (auto& puddle: puddles) {
        if (!puddle.empty()) {
            dp[puddle[1]][puddle[0]] = -1;
        }
    }
    dp[0][1] = 1;
    for (int row = 1; row <= n; row++) {
        for (int col = 1; col <= m; col++) {
            if (dp[row][col] == -1) {
                dp[row][col] = 0;
                continue;
            }
            dp[row][col] = ((dp[row - 1][col] % DIV) + (dp[row][col - 1] % DIV)) % DIV;
        }
    }
    answer = dp[n][m] % DIV;
    
    return answer;
}