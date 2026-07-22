import java.util.HashMap;
import java.util.Map;

class Solution {
    public static String solution(String[] participant, String[] completion) { 

        Map<String, Integer> participantMap = new HashMap<>(); 
        
        for (int i = 0; i < participant.length ; i++) { 
            participantMap.compute(participant[i], (k, v) -> v != null ? null : 1);
            
            if (i < completion.length) {
                participantMap.compute(completion[i], (k,v) -> v != null ? null : 1);
            }
        }
        
        return participantMap.keySet().iterator().next(); 
    }
}