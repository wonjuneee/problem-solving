import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public String solution(int[] numbers) {
        String answer = "";
        
        /**
        *   두 수를 이어붙였을 때, 큰 수가 나오는 케이스에 따라 정렬한다.
        *   e.g., [40, 4] : 440 / 404 이므로, 4가 먼저 나와야한다.
        *   e.g., [40400, 4] : 404004 / 440400 이므로, 4가 먼저 나와야 한다.
        *   e.g., [5, 30] : 530 / 305 이므로, 5가 먼저 나와야 한다.
        */
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((i1, i2) -> {
            int option1 = Integer.parseInt(String.valueOf(i1).concat(String.valueOf(i2)));
            int option2 = Integer.parseInt(String.valueOf(i2).concat(String.valueOf(i1)));
            
            if (option1 > option2) {
                return -1;
            } else if (option1 == option2) {
                return 0;
            } else {
                return 1;
            }
        });
        
        pq.addAll(Arrays.stream(numbers).boxed().toList());
        
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            sb.append(pq.poll());
        }
        answer = sb.toString();
        
        if (Arrays.stream(answer.split("")).allMatch("0"::equals)) {
            answer = "0";
        }
        
        return answer;
    }
}