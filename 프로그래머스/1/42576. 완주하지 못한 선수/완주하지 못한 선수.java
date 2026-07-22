import java.util.HashSet;
import java.util.Set;

class Solution {
    public static String solution(String[] participant, String[] completion) { 

        /**
        *   participant와 completion에 쌍으로 존재하는 참가자는 Set에서 지워지고
        *   그렇지 않은, 이 문제에서는 참가했지만 완주하지 못한, 참가자만 Set에 남게 된다.
        *   결과적 일관성으로 participant, completion 원소 순서 상관없이 두 배열을 합쳐 짝수번 등장할 때 Set에서 원소를 제거한다.
        */
        
        Set<String> participantSet = new HashSet<>(); 
        
        for (int i = 0; i < participant.length ; i++) {
            // Set에 원소가 존재하지 않아 remove에 실패한 경우, Set에 원소를 추가한다.
            // 그렇지 않은 경우 remove만 진행한다.
            if (!participantSet.remove(participant[i])) {
                participantSet.add(participant[i]);
            }
            
            if (i < completion.length) {
                if (!participantSet.remove(completion[i])) {
                    participantSet.add(completion[i]);
                }
            }
        }
        
        return participantSet.iterator().next(); 
    }
}
