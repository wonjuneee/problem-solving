class Solution {
    public int solution(int n, int[] lost, int[] reserve) {
        int answer = 0;
        
        /**
        *   모든 학생들에 대해 체육복을 한 벌 가지고 있으면 0, 여벌이 있으면 1, 없으면 2로 나타낸다.
        *   이때 체육복이 없는 학생은 가급적 앞에 있는 학생에게 체육복을 빌림으로써, 뒤에 있는 학생들도 최대한 체육복을 빌릴 수 있도록 해야한다.
        */
        
        int[] total = new int[n + 2];
        for (int l: lost) {
            total[l] = 2;
        }
        // 여벌을 가지고 있었으나 도난 당한 경우, 최종적으로 한 벌만 가지고 있는 것과 동일
        for (int r: reserve) {
            if (total[r] == 2) {
                total[r] = 0;
            } else {
                total[r] = 1;
            }
        }
        
        for (int i = 1; i <= n; i++) {
            if (total[i] == 0 || total[i] == 1) {
                answer++;
            } else {
                // 앞에 있는 학생의 체육복 먼저 확인
                if (total[i - 1] == 1) {
                    total[i - 1] = 0;
                    answer++;
                } else if (total[i + 1] == 1) {
                    total[i + 1] = 0;
                    answer++;
                }
            }
        }
        
        return answer;
    }
}