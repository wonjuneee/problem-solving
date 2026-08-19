import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        /**
        *   의상의 종류를 기준으로, 몇 개의 의상이 있는지 Map으로 계산한다.
        *   각 의상 종류마다 1개 씩 고르거나 고르지 않는 조합의 개수를 계산하면 되며, 이때 하나도 고르지 않는 경우의 수 1을 빼준 값을 반환한다.
        */
        
        Map<String, List<String>> typeNameMap = new HashMap<>();
        for (String[] cloth: clothes) {
            typeNameMap.compute(cloth[1], (k, v) -> v == null ? new ArrayList<>() : v).add(cloth[0]);
        }
        
        for (List<String> nameValue: typeNameMap.values()) {
            answer *= nameValue.size() + 1;
        }
        
        return answer - 1;
    }
}