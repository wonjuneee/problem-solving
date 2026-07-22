import java.util.HashMap;
import java.util.Map;

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        
        Map<String, Integer> participantCountMap = new HashMap<>();
        
        for (int i = 0; i < participant.length; i++) {
            participantCountMap.compute(
                participant[i],
                (k, v) -> v == null ? 1 : v + 1
            );
        }
        
        for (int i = 0; i < completion.length; i++) {
            participantCountMap.compute(
                completion[i],
                (k, v) -> v - 1
            );
        }
        
        for (int i = 0; i < participant.length; i++) {
            if (participantCountMap.get(participant[i]) != 0) {
                return participant[i];
            }
        }
        
        return answer;
    }
}