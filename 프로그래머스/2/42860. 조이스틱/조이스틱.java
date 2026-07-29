import java.util.Arrays;

class Solution {
    
    static final int ALPHABET = 26;
    
    public int solution(String name) {
        int answer = 0;
        
        /**
        *   방향 전환을 여러 번 진행할 경우, 이미 지나간 위치를 다시 지나가게 되므로 최단거리 이동이 불가하다.
        *   따라서, 최단거리로 커서를 이동시키기 위해선 방향 전환이 최대 1번만 가능하다.
        *   그렇다면 어느 지점에서 방향을 전환할지 결정해야 하는데, 0-마지막 인덱스에서 'A'가 아닌 문자의 위치가 그 후보가 된다.
        *   각 후보군 i에서 다음(처음 한 방향을 정한 뒤) 'A'가 아닌 문자의 위치 next를 기준으로 방향전환 시의 거리를 계산한다.
        *       - i에서 오른쪽-왼쪽으로 방향 전환한 경우 : i + i + (name.length() - next)
        *       - next에서 왼쪽-오른쪽으로 방향 전환한 경우 : (name.length() - next) + (name.length() - next) + i
        *       - 방향전환 없는 최단거리는은 0부터 i까지 한 방향으로만 진행하는 name.length() - 1 이다.
        *   위 케이스에 대해 모든 i에 대해 최소값을 찾으면 그것이 최단거리가 되며, 해당 케이스에서 (i 또는 next에서 방향전환 or 방향전환 x)이 이루어진다.
        */
        
        int[] nonAIndex = new int[name.length()];
        int cnt = 0;
        for (int i = 0; i < name.length(); i++) {
            int tmp = Math.min(name.charAt(i) - 'A', ALPHABET - (name.charAt(i) - 'A'));
            
            answer += tmp;
            
            // 맨 앞은 무조건 커서가 위치하므로, 반드시 포함한다.
            if (tmp != 0 || cnt == 0) {
                nonAIndex[cnt++] = i;
            }
        }
        
        if (cnt == 0) {
            return 0;
        }
        
        int nonAIdx = 0;
        int min = nonAIndex[cnt - 1];
        for (int i = 0; i < name.length(); i++) {
            // 다음 'A'가 아닌 문자가 존재하지 않으면 반복문을 종료한다.
            if (cnt <= nonAIdx + 1) {
                break;
            }
            // 'A'가 아닌 문자가 있는 위치나 name의 맨 앞은 방향전환이 가능하다.
            if (name.charAt(i) != 'A' || i == 0) {
                int next = nonAIndex[++nonAIdx];
                
                min = Math.min(min, Math.min(2 * i + (name.length() - next), 2 * (name.length() - next) + i));
            }
        }
        answer += min;
        
        return answer;
    }
}