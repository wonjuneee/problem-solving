import java.util.List;
import java.util.Set;
import java.util.HashSet;

class Solution {
    public int solution(int N, int number) {
        int answer = -1;
        
        /**
        *   x개의 N으로 만들 수 있는 수를 저장한다.
        *   이때 y개의 N으로 만들 수 있는 수는 모든 (x, y-x)개의 N으로 만들 수 있는 수에 대해 사칙연산을 수행하여 얻을 수 있다.
        *   확실히 N으로 이루어진 수로부터 다른 수를 얻을 수 있으며, 작은 개수부터 이를 판별한다.
        *   DP를 통해 문제 해결 가능하며, 이때 i개의 N으로 만들 수 있는 모든 수를 HashSet으로 중복없이 저장한다.
        *   이후 각 Set의 수를 조합하여 사칙연산으로 얻을 수 있는 모든 수를 다음 DP 테이블의 Set에 저장한다.
        */
        
        Set<Integer>[] dp = new HashSet[9];
        for (int i = 1; i <= 8; i++) {
            dp[i] = new HashSet<>();
        }
        
        for (int i = 1; i <= 8; i++) {
            int numb = Integer.parseInt(String.valueOf(N).repeat(i));
            dp[i].add(numb);
        }
        
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= i - 1; j++) {
                
                for (int a1: dp[j]) {
                    for (int a2: dp[i-j]) {
                        // 0으로 나눗셈을 진행하는 것을 방지함과 동시에, N이 최소 개수로 사용되어 만들어진 수가 아님이 자명하므로 제외
                        if (a2 != 0) {
                            dp[i].addAll(List.of(a1 + a2, a1 - a2, a1 * a2, a1 / a2));
                        }
                    }
                }
            }
            
            if (dp[i].contains(number)) {
                return i;
            }
        }
        
        return answer;
    }
}