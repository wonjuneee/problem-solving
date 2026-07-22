import java.util.HashMap;
import java.util.Map;

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        
        Map<String, Integer> participantCountMap = new HashMap<>();
        
        for (String part: participant) {
            participantCountMap.put(part, participantCountMap.getOrDefault(part, 0) + 1);
        }
        
        for (String comp: completion) {
            participantCountMap.put(comp, participantCountMap.getOrDefault(comp, 0) - 1);
        }
        
        for (Map.Entry<String, Integer> entry: participantCountMap.entrySet()) {
            if (entry.getValue() != 0) {
                return entry.getKey();
            }
        }
        
        return answer;
    }
}