import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    public int[][] solution(int n) {
        
        List<int[]> ans = hanoi(n, 1, 2, 3);
        
        int[][] answer = ans.toArray(new int[0][]);
        
        return answer;
    }
    
    List<int[]> hanoi(int n, int from, int mid, int to) {
        if (n == 1) {
            System.out.println(from + " " + to);
            return List.of(new int[] { from, to });
        }
        
        List<int[]> result = new ArrayList<>();
        // 가장 큰 판의 윗 부분 전체를 mid로 옮겨놓아야, 판을 to로 옮길 수 있다.
        result.addAll(hanoi(n - 1, from, to, mid));
        
        // 판을 from - to로 이동한다.
        result.add(new int[] { from, to });
        
        // mid로 옮겼던 윗 부분을 to로 최종적으로 이동시킨다.
        result.addAll(hanoi(n - 1, mid, from, to));
        
        return result;
    }
}