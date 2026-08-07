import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[][] solution(int[][] nodeinfo) {
        int[][] answer = new int[2][nodeinfo.length];
        /**
        *   이진트리가 반드시 구성되는 인풋이 주어진다.
        *   따라서 우선순위큐로 레벨이 낮은 원소를 기준으로 정렬하고, 이 순서대로 순회하며 트리를 구성한다.
        *       1. y값 기준 내림차순
        *       2. x값 기준 오름차순
        */
        
        Node[] nodes = new Node[nodeinfo.length];
        for (int i = 0; i < nodeinfo.length; i++) {
            Node node = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
            nodes[i] = node;
        }
        Arrays.sort(nodes, (n1, n2) -> {
            if (n1.y == n2.y) {
                return n1.x - n2.x;
            }
            return n2.y - n1.y;
        });
        
        // 최우선순위 노드는 반드시 루트 노드이므로
        Node root = nodes[0];
        for (int i = 1; i < nodes.length; i++) {
            dfs(root, nodes[i]);            
        }
        
        List<Integer> pre = new ArrayList<>();
        List<Integer> post = new ArrayList<>();
        preorder(root, pre);
        postorder(root, post);
        
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
    
    void preorder(Node node, List<Integer> pre) {
        pre.add(node.value);
        if (node.left != null) {
            preorder(node.left, pre);
        }
        if (node.right != null) {
            preorder(node.right, pre);
        }
    }
    
    void postorder(Node node, List<Integer> post) {
        if (node.left != null) {
            postorder(node.left, post);
        }
        if (node.right != null) {
            postorder(node.right, post);
        }
        post.add(node.value);
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