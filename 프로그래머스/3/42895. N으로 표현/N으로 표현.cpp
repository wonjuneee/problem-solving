#include <bits/stdc++.h>

using namespace std;

int solution(int N, int number) {
    int answer = -1;
    /**
    *   N개가 x개 사용된 숫자를 기록한다.
    *   즉, dp[x] = { a, b }와 같이 기록하여 number가 최종적으로 몇 개의 N으로 이루어지는지 최솟값을 판단한다.
    */
    
    vector<unordered_set<int>> dp (9, unordered_set<int>());
    
    for (int i = 1; i <= 8; i++) {
        string str(i, N + '0');
        int Ns = stoi(str);
        
        dp[i].insert(Ns);
    }
    
    for (int i = 1; i <= 8; i++) {
        for (auto& num1: dp[i]) {
            if (num1 == number) {
                return i;
            }
            
            for (int j = 1; j <= 8 - i; j++) {
                for (auto& num2: dp[j]) {
                    dp[i + j].insert(num1 + num2);
                    dp[i + j].insert(num1 * num2);
                    // N을 뺀/나눈 수가 음수일 경우 다시 양수가 되기 위해선 다음 단계에서 N이 더해/곱해져야 하는데, 이는 N이 가장 적게 사용된 것이 아님이 자명하므로 제외한다.
                    if (num1 - num2 > 0) {
                        dp[i + j].insert(num1 - num2);
                    }
                    if (num2 - num1 > 0) {
                        dp[i + j].insert(num2 - num1);
                    }
                    if (num1 / num2 > 0) {
                        dp[i + j].insert(num1 / num2);
                    }
                    if (num2 / num1 > 0) {
                        dp[i + j].insert(num2 / num1);
                    }
                }
            }
        }
    }
    
    return answer;
}