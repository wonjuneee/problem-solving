#include <bits/stdc++.h>

using namespace std;

int solution(vector<int> nums)
{
    int answer = 0;
    
    unordered_set<int> pokemon;
    
    for (auto& num: nums) {
        pokemon.insert(num);
    }
    
    answer = min(pokemon.size(), nums.size() / 2);
    
    return answer;
}