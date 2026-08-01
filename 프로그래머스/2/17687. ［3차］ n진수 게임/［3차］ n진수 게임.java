class Solution {
    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();
        
        int currCnt = 0;
        while (answer.length() < t) {
            for (int i = 1; i <= m; i++) {
                String nth = Integer.toString(currCnt++, n).toUpperCase();
                int size = nth.length();
                
                // System.out.print(nth + " " + i + "번째 차례" + ">> ");
                
                if (i <= p) {
                    int offset = p - i, repeat = 0;
                    while (offset + m * repeat < size && answer.length() < t) {
                        answer.append(nth.charAt(offset + m * repeat));
                        repeat++;
                    }
                    // System.out.println(offset);
                } else if (i > p){
                    int offset = m - i + p, repeat = 0;
                    while (offset + m * repeat < size && answer.length() < t) {
                        answer.append(nth.charAt(offset + m * repeat));
                        repeat++;
                    }
                    // System.out.println(offset);
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