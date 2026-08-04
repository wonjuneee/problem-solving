import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = { 0, 0 };
        /**
        *   최대-최소힙 2개를 활용한다.
        *   최대/최소값 제거는 각각 최대/최소힙에서만 진행한다.
        *   이때 나머지 힙에 남아있는 유령값이 추후 명령어에서 중복으로 제거되는 것을 방지하기 위해 Map으로 카운트한다.
        *   제거된 값이 다른 명령어로 다른 힙에서 제거된 값(유령값)이라면 Map에 의해 카운트가 0이므로, 0이 아닌 값이 제거될 때까지 반복한다.
        *   카운트가 0이 아닌 값은 제거 후 -1을 카운트한다.
        *   
        *   모든 명령어를 수행한 후, 최대/최소힙에 카운트가 0인 유령값이 남아있지 않도록 반복하여 제거한다.
        */
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        Map<Integer, Integer> valueCountMap = new HashMap<>();

        for (String operation: operations) {
            String[] op = operation.split(" ");
            
            int value = Integer.parseInt(op[1]);
            switch (op[0].charAt(0)) {
                case 'I': 
                    maxHeap.add(value);
                    minHeap.add(value);
                    valueCountMap.compute(value, (k, v) -> v == null ? 1 : v + 1);
                    break;    
                case 'D':
                    switch (value) {
                        case -1:
                            if (!minHeap.isEmpty()) {
                                int min = minHeap.poll();
                                while (valueCountMap.getOrDefault(min, 0) == 0 && !minHeap.isEmpty()) {
                                    min = minHeap.poll();
                                }
                                valueCountMap.put(min, Math.max(valueCountMap.get(min) - 1, 0));
                            }
                            break;
                        case 1:
                            if (!maxHeap.isEmpty()) {
                                int max = maxHeap.poll();
                                while (valueCountMap.getOrDefault(max, 0) == 0 && !maxHeap.isEmpty()) {
                                    max = maxHeap.poll();
                                }
                                valueCountMap.put(max, Math.max(valueCountMap.get(max) - 1, 0));
                            }
                            break;
                        default: break;
                    }
                    break;
                default: break;                    
            }
        }
        
        if (!maxHeap.isEmpty()) {
            while (valueCountMap.getOrDefault(maxHeap.peek(), 0) == 0 && !maxHeap.isEmpty()) {
                maxHeap.poll();
            }
        }
        if (!minHeap.isEmpty()) {
            while (valueCountMap.getOrDefault(minHeap.peek(), 0) == 0 && !minHeap.isEmpty()) {
                minHeap.poll();
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