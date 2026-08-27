#include <bits/stdc++.h>

using namespace std;

int solution(vector<vector<int>> sizes) {
    int answer = 0;
    
    int max_larger = INT_MIN, max_smaller = INT_MIN;
    for(auto& size: sizes) {
        int larger = max(size[0], size[1]), smaller = min(size[0], size[1]);
        
        if (max_larger < larger) {
            max_larger = larger;
        }
        if (max_smaller < smaller) {
            max_smaller = smaller;
        }
    }
    
    answer = max_larger * max_smaller;
    return answer;
}