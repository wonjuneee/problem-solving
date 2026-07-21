class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        
        /**
        *   brown이 카펫의 외곽 테두리를 감싸고 있으므로, (a + b) * 2 개로 구성되어 있다.
        *   이때 가로 세로는 각각 (a > b)일 때, [a+1, b+1] 개이다.
        *   가로 a+1, 세로 b+1일 때, 내부 노란색 개수는 (a-1, b-1)이다.
        */
        
        int border = brown / 2;
        
        for (int a = border; a > 0; a--) {
            int b = border - a;
            
            if (yellow == (a-1) * (b-1)) {
                answer[0] = a + 1;
                answer[1] = b + 1;
                break;
            }
        }
        
        return answer;
    }
}