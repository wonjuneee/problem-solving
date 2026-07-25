import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public String solution(int[] numbers) {
        String answer = "";
        
        /**
        *   +) 최초설계
        *   1. 두 수의 자리 수가 같으면, 단순비교하여 내림차순으로 정렬한다.
        *   2. 두 수의 자리 수가 같으면, 자리 수가 작은 수의 자리 수 크기만큼 앞에서부터 작은 자리 수와 큰 자리 수를 비교한다.
        *       - 앞에서부터 비교한 것이 다르면, 이 값을 기준으로 내림차순 정렬한다.
        *       - 앞에서부터 비교한 것이 같으면, 두 수의 자리 수의 차이만큼 자리 수가 큰 수의 앞뒤를 나누어 비교한 뒤 이를 기준으로 정렬한다.
        *           - 앞이 큰 경우 작은 자리 수의 수를 앞으로 정렬하고, 뒤가 큰 경우 큰 자리 수의 수를 앞으로 정렬한다.
        *           e.g., [40400, 40]에 대해 작은 자리 수인 40을 기준으로 40400과 앞에서부터 비교한다.
        *                 이때 (40 == 40)이므로 40400과 40의 자리 수의 차 3으로 40400의 앞뒤 각각으로부터 세 자리 수를 추출한다.
        *                 추출한 수는 각각 404, 400으로 (404 > 400)이고 이는 앞의 수가 더 큰 것이므로, 최종적으로 작은 자리 수인 40이 40400보다 앞에 위치한다.
        *   
        *   ==========================================================================================================================
        *   
        *   두 수를 이어붙였을 때, 큰 수가 나오는 케이스에 따라 정렬한다.
        *   e.g., [40, 4] : 440 / 404 이므로, 4가 먼저 나와야한다.
        *   e.g., [40400, 4] : 404004 / 440400 이므로, 4가 먼저 나와야 한다.
        *   e.g., [5, 30] : 530 / 305 이므로, 5가 먼저 나와야 한다.
        */
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((i1, i2) -> {
            int option1 = Integer.parseInt(String.valueOf(i1).concat(String.valueOf(i2)));
            int option2 = Integer.parseInt(String.valueOf(i2).concat(String.valueOf(i1)));
            
            if (option1 >= option2) {
                return -1;
            } else {
                return 1;
            }
        });
        
        pq.addAll(Arrays.stream(numbers).boxed().toList());
        
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            sb.append(pq.poll());
        }
        answer = sb.charAt(0) == '0' ? "0" : sb.toString();
        
        return answer;
    }
}