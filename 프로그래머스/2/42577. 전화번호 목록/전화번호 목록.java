import java.util.Arrays;

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        /**
        *   문자열을 사전 순으로 정렬하면, 바로 직전의 문자열이 현재 문자열의 접두사인지만 판단하면 된다.
        *   e.g., ["123", "78", "127", "1", "12"] -> ["1", "12", "123", "127", "78"]
        *       phone_book[i].startsWith(phone_book[i - 1])
        */
        
        Arrays.sort(phone_book);
        for (int i = 1; i < phone_book.length; i++) {
            if (phone_book[i].startsWith(phone_book[i - 1])) {
                answer = false;
                break;
            }
        }
        
        return answer;
    }
}