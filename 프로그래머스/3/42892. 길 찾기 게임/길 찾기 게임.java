import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

class Solution {
    public int[][] solution(int[][] nodeinfo) {
        int[][] answer = new int[2][nodeinfo.length];
        /**
        *   이진트리가 반드시 구성되는 인풋이 주어진다.
        *   따라서 우선순위큐로 레벨이 낮은 원소를 기준으로 정렬하고, 이 순서대로 순회하며 트리를 구성한다.
        *       1. y값 기준 내림차순
        *       2. x값 기준 오름차순
        */
        
        PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> {
            if (n1.y == n2.y) {
                return n1.x - n2.x;
            }
            return n2.y - n1.y;
        });
        
        for (int i = 0; i < nodeinfo.length; i++) {
            Node node = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
            pq.add(node);
        }
        
        // 최우선순위 노드는 반드시 루트 노드이므로
        Node root = pq.poll();
        while (!pq.isEmpty()) {
            dfs(root, pq.poll());            
        }
        
        List<Integer> pre = preorder(root);
        List<Integer> post = postorder(root);
        
        answer[0] = pre.stream().mapToInt(Integer::intValue).toArray();
        answer[1] = post.stream().mapToInt(Integer::intValue).toArray();
        
        return answer;
    }
    
    void dfs(Node root, Node node) {
        if (root.x < node.x) {
            if (root.right == null) {
                root.right = node;
            } else {
                dfs(root.right, node);            
            }
        } else {
            if (root.left == null) {
                root.left = node;
            } else {
                dfs(root.left, node);
            }
        }
    }
    
    List<Integer> preorder(Node node) {
        if (node.left == null && node.right == null) {
            return List.of(node.value);
        }
        
        List<Integer> result = new ArrayList<>();
        
        result.add(node.value);
        if (node.left != null) {
            result.addAll(preorder(node.left));
        }
        if (node.right != null) {
            result.addAll(preorder(node.right));
        }
        
        return result;
    }
    
    List<Integer> postorder(Node node) {
        if (node.left == null && node.right == null) {
            return List.of(node.value);
        }
    
        List<Integer> result = new ArrayList<>();

        if (node.left != null) {
            result.addAll(postorder(node.left));
        }
        if (node.right != null) {
            result.addAll(postorder(node.right));
        }
        result.add(node.value);
        
        return result;
    }
    
    class Node {
        Node left, right;
        int value, x, y;
    
        public Node(int value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString() {
            return this.value + ": (" + this.x + ", " + this.y + ")";
        }
    }
}