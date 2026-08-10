import java.util.Set;
import java.util.HashSet;
import java.util.stream.IntStream;

class Solution {
    public int solution(int k, int[] num, int[][] links) {
        int answer = 0;
        /**
        *   각 노드의 응시인원 최대값과 모든 노드의 응시인원의 합 사이에 정답이 존재한다.
        *   따라서 이 범위 내의 이진탐색을 진행하며, 해당 인원으로 그룹을 구성할 때 k개 이하의 그룹이 형성될 수 있으면 정답 후보가 된다.
        *   이를 반복하여 k개 이하의 그룹을 형성할 때 그룹 인원 최대값들 중 최소값을 반환한다.
        */
        
        Set<Integer> childSet = new HashSet<>();
        IntStream.range(0, num.length).forEach(childSet::add);
        
        for (int i = 0; i < links.length; i++) {
            childSet.remove(links[i][0]);
            childSet.remove(links[i][1]);
        }
        int root = childSet.iterator().next();
        
        Node tree = new Node(root, num[root]);
        int[] treeInfo = buildTree(links, num, tree);
        
        answer = binarySearch(k, num, tree, treeInfo);
        return answer;
    }
    
    int[] buildTree(int[][] links, int[] num, Node node) {
        int max = node.count, leftSum = 0, rightSum = 0;
        if (links[node.idx][0] != -1) {
            node.left = new Node(links[node.idx][0], num[links[node.idx][0]]);
            int[] leftInfo = buildTree(links, num, node.left);
            
            leftSum = leftInfo[0];
            max = Math.max(leftInfo[1], max);
        }
        if (links[node.idx][1] != -1) {
            node.right = new Node(links[node.idx][1], num[links[node.idx][1]]);
            int[] rightInfo = buildTree(links, num, node.right);
            
            rightSum = rightInfo[0];
            max = Math.max(rightInfo[1], max);
        }
        
        return new int[]{ node.count + leftSum + rightSum, max };
    }
    
    int binarySearch(int k, int[] num, Node root, int[] treeInfo) {
        int left = treeInfo[1], right = treeInfo[0], result = Integer.MAX_VALUE;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            
            int[] subResult = dfs(num, root, mid);
            
            if (subResult[1] <= k - 1) {
                result = Math.min(result, subResult[2]);
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    int[] dfs(int[] num, Node node, int targetSum) {
        
        int leftSum = 0, rightSum = 0, cutCnt = 0, max = node.count;
        
        if (node.left != null) {
            int[] leftRes = dfs(num, node.left, targetSum);
            leftSum = leftRes[0];
            cutCnt += leftRes[1];
            max = Math.max(leftRes[2], max);
        }
        if (node.right != null) {
            int[] rightRes = dfs(num, node.right, targetSum);
            rightSum = rightRes[0];
            cutCnt += rightRes[1];
            max = Math.max(rightRes[2], max);
        }
        
        int totalSum = node.count + leftSum + rightSum;
        if (totalSum <= targetSum) {
            return new int[]{ totalSum, cutCnt, Math.max(totalSum, max) };
        } else if (totalSum > targetSum && node.count + Math.min(leftSum, rightSum) <= targetSum) {
            return new int[]{ node.count + Math.min(leftSum, rightSum), cutCnt + 1, Math.max(node.count + Math.min(leftSum, rightSum), max) };
        } else { // node.count + Math.min(leftSum, rightSum) > targetSum
            return new int[]{ node.count, cutCnt + 2, Math.max(Math.max(leftSum, rightSum), max) };
        }
    }
    
    class Node {
        int idx, count;
        Node left, right;
        
        public Node(int idx, int count) {
            this.idx = idx;
            this.count = count;
        }
        
        @Override
        public String toString() {
            return idx + ": " + count + '\n' + "[l- " + left + ", r-" + right + "]";
        }
    }
}