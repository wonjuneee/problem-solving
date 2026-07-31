class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        
        /**
        *   모든 케이스를 계산하는 경우, 최대 2^500개의 케이스를 계산해야 한다.
        *   따라서 DP 테이블을 활용해 연산 횟수를 최소화할 수 있다.
        *   - 기저 조건(삼각형 최하단)부터 값을 더해가는 바텀업 방식의 DP로 구현
        *   - 별도의 DP 테이블 대신, triangle 배열을 그대로 사용할 수 있다.
        */
        
        for (int i = triangle.length - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                triangle[i][j] = triangle[i][j] + Math.max(triangle[i + 1][j], triangle[i + 1][j + 1]);
            }
        }
        answer = triangle[0][0];
        
        return answer;
    }
}