import java.util.Arrays;

class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";
        /**
        *   각 로그마다 해당 타임라인의 시청 인원 수를 증가시키는 방식(for문)을 사용하면 시간초과가 발생할 수 있다.
        *   따라서 0초부터 재생시간 끝까지 시청 인원의 누적합을 구한 뒤, 광고 재생시간으로 윈도우를 옮겨가며 최대 시청 인원 수를 구한다.
        *       1. timeline에 대해 log의 시작에 +1, 끝에 -1을 더한다. (타임라인은 이상, 미만으로 시간을 관리)
        *       2. timeline을 전체 순회하여 i 시간의 시청자 수를 계산한다.
        *       3. timeline을 전체 순회하여 i 시간까지의 누적 시청시간을 계산한다.
        *       4. timeline에서 adTime을 윈도우로 하여 [i, i + adTime - 1] 사이의 누적합을 계산하고, 최대값을 반환한다.
        */
        long[] timeline = new long[360000];
        
        for (String log: logs) {
            String[] times = log.split("-");
            
            int start = timeToSecond(times[0]);
            int end = timeToSecond(times[1]);
            
            timeline[start]++;
            timeline[end]--;
        }
        
        int playTime = timeToSecond(play_time);
        int adTime = timeToSecond(adv_time);
        
        // 해당 타임라인의 시청자 수 계산
        for (int i = 1; i <= playTime; i++) {
            timeline[i] += timeline[i - 1];
        }
        // 해당 타임라인의 누적된 시청자 수 계산
        for (int i = 1; i <= playTime; i++) {
            timeline[i] += timeline[i - 1];
        }
        
        // [0, adTime - 1] : 0부터 adTime 동안의 총 시청시간
        long max = timeline[adTime - 1];
        int time = 0;
        // [1, adTime] - [playTime - adTime + 1, playTime]
        for (int i = 1; i + adTime - 1 <= playTime; i++) {
            long curr = timeline[i + adTime - 1] - timeline[i - 1]; 
            
            if (curr > max) {
                time = i;
                max = curr;
            }
        }
        
        answer = secondToTime(time);
        return answer;
    }
    
    public int timeToSecond(String time) {
        String[] timeStr = time.split(":");
        
        return 3600 * Integer.parseInt(timeStr[0]) + 60 * Integer.parseInt(timeStr[1]) + Integer.parseInt(timeStr[2]);
    }
    
    public String secondToTime(int second) {
        int hour = second / (60 * 60);
        second %= 60 * 60;
        int minute = second / 60;
        second %= 60;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}