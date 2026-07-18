import java.util.*;

class Solution {
    public int[] solution(int[] answers) {
        int[] answer = {0, 0, 0};
        
        final int[] sheet1 = new int[] { 1, 2, 3, 4, 5 };
        final int[] sheet2 = new int[] { 1, 3, 4, 5 };
        final int[] sheet3 = new int[] { 3, 3, 1, 1, 2, 2, 4, 4, 5, 5 };
        
        int[] ans = new int[]{ 0, 0, 0 };   // 각 수포자의 정답 개수
        int idx1 = 0, idx2 = 0, idx3 = 0;   // 각 시트 별 idx
        
        for (int i = 0; i < answers.length; i++) {
            
            ans[0] = answers[i] == sheet1[idx1 % 5] ? ans[0] + 1 : ans[0];
            idx1++;
            
            if (i % 2 == 0) {
                ans[1] = answers[i] == 2 ? ans[1] + 1 : ans[1];
            } else {
                ans[1] = answers[i] == sheet2[idx2 % 4] ? ans[1] + 1 : ans[1];
                idx2++;
            }
            
            ans[2] = answers[i] == sheet3[idx3 % 10] ? ans[2] + 1 : ans[2];
            idx3++;
        }
        
        int max = 0, cnt = 0;
        for (int a: ans) {
            max = Math.max(max, a);
        }
        for (int i = 0; i < 3; i++) {
            if (ans[i] == max) {
                answer[cnt++] = i + 1;
            }
        }
        int[] finalAnswer = new int[cnt];
        System.arraycopy(answer, 0, finalAnswer, 0, cnt);
        
        return finalAnswer;
    }
}