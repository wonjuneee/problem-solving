import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = { 0, 0 };
        /**
        *   최대-최소힙 2개를 활용한다.
        *   각 케이스에 따라 최대/최소힙에서 poll()을 진행한 뒤, 나머지 힙에서 remove()로 원소를 제거한다.
        */
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        for (String operation: operations) {
            String[] op = operation.split(" ");
            
            int value = Integer.parseInt(op[1]);
            switch (op[0].charAt(0)) {
                case 'I': 
                    maxHeap.add(value);
                    minHeap.add(value);
                    break;    
                case 'D':
                    switch (value) {
                        case -1:
                            if (!minHeap.isEmpty()) {
                                int poll = minHeap.poll();
                                maxHeap.remove(poll);
                            }
                            break;
                        case 1:
                            if (!maxHeap.isEmpty()) {
                                int poll = maxHeap.poll();
                                minHeap.remove(poll);
                            }
                            break;
                        default: break;
                    }
                    break;
                default: break;
            }
        }
        
        if (!maxHeap.isEmpty()) {
            answer[0] = maxHeap.peek();
        }
        if (!minHeap.isEmpty()) {
            answer[1] = minHeap.peek();
        }
        
        return answer;
    }
}