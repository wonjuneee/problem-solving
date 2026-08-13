import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        /**
        *   Trie 구조를 사용해 각 문자열의 prefix를 확인할 수 있다.
        */
        
        Trie root = new Trie();
        for (String phoneNumb: phone_book) {
            buildTrie(phoneNumb.toCharArray(), root);
        }
        
        for (String phoneNumb: phone_book) {
            answer = search(phoneNumb.toCharArray(), root);
            
            if (!answer) {
                break;
            }
        }
        
        return answer;
    }
    
    class Trie {
        Map<Character, Trie> child;
        boolean isEnd = false;
        
        public Trie() {
            this.child = new HashMap<>();
        }
    }
    
    public void buildTrie(char[] charArr, Trie node) {
        if (charArr.length == 0) {            
            return;
        }
        node.child.putIfAbsent(charArr[0], new Trie());
        Trie next = node.child.get(charArr[0]);
        
        buildTrie(Arrays.copyOfRange(charArr, 1, charArr.length), next);
        
        if (!next.child.isEmpty()) {
            next.isEnd = false;
        } else {
            next.isEnd = true;
        }
    }
    
    public boolean search(char[] charArr, Trie node) {
        if (node.isEnd && charArr.length == 0) {
            return true;
        } else if (!node.isEnd && charArr.length == 0) {
            return false;
        }
        
        Trie next = node.child.get(charArr[0]);
        return search(Arrays.copyOfRange(charArr, 1, charArr.length), next);
    }
}