import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int[] solution(String s) {
        String[] set = s.substring(2, s.length() - 2).split("\\},\\{");
        Arrays.sort(set, (s1, s2) -> s1.length() - s2.length());
        int[] answer = new int[set.length];
        
        // System.out.println(Arrays.toString(set));
        // if (set.length > 2) {
        //     System.out.println(set[1]);
        // }
        
        Set<Integer> placedSet = new HashSet<>();
        for (int i = 0; i < set.length; i++) {
            int[] parsed = parseSetIntoArray(set[i]);
            // System.out.println(Arrays.toString(parsed));
            
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