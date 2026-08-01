class Solution {
    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();
        
        /**
        *   핵심: Integer.toString(integer, radix) 메서드는 인자로 주어진 수와 진수를 바탕으로 숫자를 주어진 진수의 문자열로 변환한다.
        *   currCnt를 처음으로 말하는 멤버(i) 기준으로 튜브(p)의 위치에 따라, n진수의 수 중 몇째 자리를 말할지가 결정된다.
        *   이때 기본적으로 offset은 주어진 문자열 중 튜브가 말하는 가장 첫 번째 문자의 인덱스이며, 멤버의 수(m)만큼 인덱스를 늘려가며 문자를 추가한다.
        *       - i <= p인 경우, offset = p - i
        *       - i > p인 경우, offset = m - i + p
        *   수 하나를 모두 말하면, 길이(size)와 멤버 수(m)을 기준으로 다음 순번을 정한다.
        */
        
        int currCnt = 0;
        while (answer.length() < t) {
            for (int i = 1; i <= m; i++) {
                String nth = Integer.toString(currCnt++, n).toUpperCase();
                int size = nth.length();
                
                int offset = 0, repeat = 0;
                if (i <= p) {
                    offset = p - i;
                } else if (i > p){
                    offset = m - i + p;
                }
                
                while (offset + m * repeat < size && answer.length() < t) {
                    answer.append(nth.charAt(offset + m * repeat));
                    repeat++;
                }
                i += (size - 1) % m;
                if (i > m) {
                    i -= m;
                }
            }
        }
        
        return answer.toString();
    }
}