import java.io.*;
import java.util.*;

class Solution {
    
    static int[] dp = new int[200002];
    
    public int solution(int n, int[] tops) {
        int answer = 0;
        
        dp[0] = dp[1] = 1;
        for (int i = 2; i <= 2 * n + 1; i++) {
            if (i % 2 == 0) {
                if (tops[i/2 - 1] == 1) {
                    dp[i] = (dp[i - 2] % 10007) + (dp[i - 1] * 2 % 10007);    
                } else {
                    dp[i] = (dp[i - 2] % 10007) + (dp[i - 1] % 10007);
                }
            }
            else {
                dp[i] = (dp[i - 1] % 10007) + (dp[i - 2] % 10007);
            }
        }
        
        answer = dp[2*n + 1] % 10007;
        return answer;
    }
}