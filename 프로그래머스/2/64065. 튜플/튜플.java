import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int[] solution(String s) {
        /**
        *   중복되는 원소가 없는 튜플이므로, 한 번 등장한 숫자는 해시셋에 넣어 중복여부를 판단할 수 있다.
        *   Input으로 주어진 문자열은 "\\},\\{"로 파싱한 뒤, 길이에 대한 오름차순으로 정렬한다.
        *   파싱된 집합에 대한 배열을 순회하여, 해시셋에 대한 중복 체크를 하여 해시셋에 존재하지 않는 값을 즉시 반환한다.
        */
        
        
        String[] set = s.substring(2, s.length() - 2).split("\\},\\{");
        Arrays.sort(set, (s1, s2) -> s1.length() - s2.length());
        int[] answer = new int[set.length];
        
        Set<Integer> placedSet = new HashSet<>();
        for (int i = 0; i < set.length; i++) {
            int[] parsed = parseSetIntoArray(set[i]);
            
            int number = Arrays.stream(parsed)
                .filter(num -> !placedSet.contains(num))
                .findFirst()
                .getAsInt();
            
            answer[i] = number;
            placedSet.add(number);
        }
        
        return answer;
    }
    
    int[] parseSetIntoArray(String set) {
        return Arrays.stream(set.split(",")).mapToInt(x -> Integer.parseInt(x)).toArray();
    }
}