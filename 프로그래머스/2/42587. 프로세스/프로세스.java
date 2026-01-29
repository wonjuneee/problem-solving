import java.io.*;
import java.util.*;

class Solution {
    
    public int solution(int[] priorities, int location) {
        
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        Queue<int[]> q = new LinkedList<>();
        
        for (int i = 0; i < priorities.length; i++) {
            pq.offer(priorities[i]);
            q.offer(new int[]{ priorities[i], i });
        }
        
        int answer = 0;
        while (!q.isEmpty()) {
            int currMax = pq.peek();
            int[] currVal = q.poll();
            
            if (currMax == currVal[0]) { // 최고 우선순위의 프로세스인지 확인
                pq.poll();
                answer += 1;
                // 찾고자 하는 위치의 프로세스인지 확인
                if (location == currVal[1]) {
                    break;
                }
            } else { // 최고 우선순위가 아니면 다시 큐 맨 뒤로
                q.offer(currVal);
            }
        }
        
        return answer;
    }
}