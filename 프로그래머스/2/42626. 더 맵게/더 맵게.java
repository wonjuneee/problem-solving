import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        
        /**
        *   우선순위 큐를 오름차순(Min-Heap)으로 정렬하여 스코빌 지수가 낮은 음식부터 꺼낸다.
        *   이후 계산된 값을 큐에 넣어, 최소 스코빌 지수를 확인하는 과정을 반복한다.
        */
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.addAll(Arrays.stream(scoville).boxed().toList());    
        
        while (pq.peek() < K) {
            if (pq.size() < 2) {
                return -1;
            }
            
            int scov1 = pq.poll();
            int scov2 = pq.poll();
            
            int newScov = scov1 + 2 * scov2;
            answer++;
            
            pq.offer(newScov);
        }
        
        return answer;
    }
}