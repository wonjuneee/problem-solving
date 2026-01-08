class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        
        for (int i = 0; i < prices.length; i++) {
            int result = traverse(prices, prices[i], i + 1, prices.length - 1) - i;
            answer[i] = result;
        }
        
        return answer;
    }
    
    private int traverse(int[] prices, int price, int idx, int lastIdx) {
        if (idx >= lastIdx) {
            return lastIdx;
        }
        
        if (prices[idx] < price) {
            return idx;
        } else {
            return traverse(prices, price, idx + 1, lastIdx);
        }
    }
}