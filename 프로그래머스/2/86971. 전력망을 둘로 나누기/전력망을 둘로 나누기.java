import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    public int solution(int n, int[][] wires) {
        int answer = -1;
        
        /**
        *   양방향 간선에 대해 DFS로 순회하여 각 노드가 가지는 본인 포함 자식 노드 개수를 판단한다.
        *   각 DFS 순회에서 전체 노드 개수 total에서 한 자식노드를 루트로 한 네트워크의 노드 개수 childCnt를 뺀 값이 현재 node의 송전탑 개수이고,
        *   childCnt가 해당 자식노드가 루트인 네트워크의 송전탑 개수이다.
        *   이로부터 두 네트워크의 송전탑 개수 차이 diff는 Math.abs(total - (childCnt * 2))가 된다.
        *   이것을 모든 노드에서, 각 노드의 자식노드를 분리하여 2개의 네트워크로 만들었을 때의 diff 최소값을 최종적으로 반환한다.
        */
        
        Map<Integer, Set<Integer>> tree = new HashMap<>();
        int total = 0;
        
        for (int[] wire: wires) {
            tree.putIfAbsent(wire[0], new HashSet<>());
            tree.putIfAbsent(wire[1], new HashSet<>());
            tree.get(wire[0]).add(wire[1]);
            tree.get(wire[1]).add(wire[0]);
            
            total = Math.max(total, Math.max(wire[0], wire[1]));
        }
        // System.out.println(tree);
        
        boolean[] isVisited = new boolean[total + 1];
        Arrays.fill(isVisited, false);
        
        answer = dfs(tree, isVisited, 1, total)[1];
        
        return answer;
    }
    
    int[] dfs(Map<Integer, Set<Integer>> tree, boolean[] isVisited, int node, int total) {        
        
        // 부모 노드에 반환할 자식 노드 개수에 자신도 포함
        int cnt = 1, diff = total - 1;
        isVisited[node] = true;
        
        for (Integer child: tree.get(node)) {
            // 양방향 간선으로 구성되어 있으므로, 말단 노드는 이미 방문한 노드들과만 연결되어 있다고 볼 수 있다. 따라서 { cnt=1, diff=total }을 반환한다.
            if (!isVisited[child]) {
                
                int[] childInfo = dfs(tree, isVisited, child, total);
                int childCnt = childInfo[0], childDiff = childInfo[1];

                // 현재 node에서 한 자식노드에 대한 diff는 total에서 childCnt를 뺀 값과 childCnt를 뺀 값이, 두 그래프의 노드 개수 차이이다.
                // 이때 현재 node의 최종 diff는 모든 자식노드에 대한 diff 중 최소값이다.
                diff = Math.min(diff, Math.min(childDiff, Math.abs(total - (childCnt * 2))));
                cnt += childCnt;
                
                // System.out.println("NODE: " + node + ", DIFF: " +  Math.abs(total - (childCnt * 2)));
            }
        }
        
        //System.out.println("NODE: " + node + "(" + cnt + ")" + ", DIFF: " + diff);
        
        return new int[]{ cnt, diff };
    }
}