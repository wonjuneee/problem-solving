class Solution {
    public int solution(int[] numbers, int target) {
        int answer = 0;
        
        /**
        * 주어진 모든 숫자를 활용해야하므로, 각 원소에 대해 +-로 분기하여 모든 경우의 수를 탐색해야 한다.
        */
        
        answer += dfs(numbers, target, 0, 0);
        
        return answer;
    }
    
    int dfs(int[] numbers, int target, int idx, int sum) {
        if (idx == numbers.length && target == sum) {
            return 1;
        }
        
        int cnt = 0;
        if (idx < numbers.length) {
            cnt += dfs(numbers, target, idx + 1, sum + numbers[idx]);
            cnt += dfs(numbers, target, idx + 1, sum - numbers[idx]);
        }
        
        return cnt;
    }
}