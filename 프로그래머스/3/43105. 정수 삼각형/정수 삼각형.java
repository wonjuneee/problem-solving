class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        
        /**
        *   모든 케이스를 계산하는 경우, 최대 2^500개의 케이스를 계산해야 한다.
        *   따라서 DP 테이블을 활용해 연산 횟수를 최소화할 수 있다.
        */
        
        int[][] dp = new int[triangle.length][triangle.length];
        
        System.arraycopy(triangle[triangle.length - 1], 0, dp[triangle.length - 1], 0, triangle.length);
        
        for (int i = triangle.length - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = triangle[i][j] + Math.max(dp[i + 1][j], dp[i + 1][j + 1]);
            }
        }
        answer = dp[0][0];
        
        return answer;
    }
}