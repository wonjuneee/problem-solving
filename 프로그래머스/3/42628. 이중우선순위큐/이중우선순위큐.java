import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = { 0, 0 };
        /**
        *   TreeMap을 활용해 key 기준 정렬과 동시에, 힙에 존재하는 key의 개수를 추적한다.
        *   카운트가 0이 되면 해당 노드를 TreeMap에서 제거한다.
        */
        
        TreeMap<Integer, Integer> tree = new TreeMap<>();

        for (String operation: operations) {
            String[] op = operation.split(" ");
            
            int value = Integer.parseInt(op[1]);
            switch (op[0].charAt(0)) {
                case 'I': 
                    tree.compute(value, (k, v) -> v == null ? 1 : v + 1);
                    break;    
                case 'D':
                    switch (value) {
                        case -1:
                            if (!tree.isEmpty()) {
                                tree.compute(tree.firstKey(), (k, v) -> v == 1 ? null : v - 1);
                            }
                            break;
                        case 1:
                            if (!tree.isEmpty()) {
                                tree.compute(tree.lastKey(), (k, v) -> v == 1 ? null : v - 1);
                            }
                            break;
                        default: break;
                    }
                    break;
                default: break;                    
            }
        }
        
        if (!tree.isEmpty()) {
            answer[0] = tree.lastKey();
            answer[1] = tree.firstKey();
        }
        
        return answer;
    }
}