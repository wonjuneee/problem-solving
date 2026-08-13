import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        /**
        *   Trie 구조를 사용해 각 문자열의 prefix를 확인할 수 있다.
        *   문자열이 Trie에 모두 저장되면 한 문자열이 종료됨을 의미하는 isEnd 필드의 상태값을 true로 변경한다.
        *   이후 문자열 탐색 중, 주어진 문자열을 모두 탐색하기 전에 isEnd 필드가 true인 노드를 만나면 해당 문자열의 prefix인 어떤 문자열이 존재함을 의미한다.
        */
        
        Trie root = new Trie();
        for (String phoneNumb: phone_book) {
            root.insert(phoneNumb);
        }
        
        for (String phoneNumb: phone_book) {
            answer = root.search(phoneNumb);
            
            if (!answer) {
                break;
            }
        }
        
        return answer;
    }
    
    class Node {
        Map<Character, Node> child = new HashMap<>();
        boolean isEnd = false;
    }
    
    class Trie {
        Node root = new Node();
        
        public void insert(String str) {
            Node node = root;
            
            for (int i = 0; i < str.length(); i++) {
                // node.child.putIfAbsent(str.charAt(i), new Node());
                // node = node.child.get(str.charAt(i));
                
                // 새로운 문자면 Node 생성, 이미 존재하는 문자면 기존 Node 그대로 반환 
                node = node.child.computeIfAbsent(str.charAt(i), k -> new Node());
            }
            
            node.isEnd = true;
        }
    
        public boolean search(String str) {
            Node node = root;
            for (int i = 0; i < str.length(); i++) {
                node = node.child.get(str.charAt(i));
                
                // 현재 탐색 중인 문자열의 prefix에 해당하는 어떤 문자열이 존재
                if (node.isEnd && i < str.length() - 1) {
                    return false;
                }
            }
            // 주어진 문자열이 그대로 검색되었으므로, prefix에 해당하는 문자열이 존재하지 않음
            return true;
        }
    }
}