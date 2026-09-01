#include <bits/stdc++.h>

using namespace std;

vector<int> solution(vector<string> genres, vector<int> plays) {
    vector<int> answer;
    /**
    *   장르명을 키로 하고, 각각 { 총 재생횟수 }, { 개별 재생횟수, 인덱스 }를 값으로 가지는 해시맵을 구성할 수 있다.
    *   1. 장르명 - 총 재생횟수를 가지는 map을 vector로 옮겨, 재생횟수의 내림차순으로 벡터를 정렬한다.
    *   2. 각 장르명에 대해, { 개별 재생횟수, 인덱스 } vector를 개별 재생횟수의 내림차순으로 정렬한다.
    *   각 장르별 정렬 순서, 각 노래 별 개별 재생횟수 정렬에 따라 각 장르별 최대 2개까지 answer에 추가하여 반환한다.
    */
    
    unordered_map<string, int> genre_playtime_map;
    unordered_map<string, vector<pair<int, int>>> genre_playtime_index_map;
    for (int i = 0; i < genres.size(); i++) {
        genre_playtime_map[genres[i]] += plays[i];
        genre_playtime_index_map[genres[i]].push_back({ plays[i], i });
    }
    vector<pair<string, int>> genre_playtime_v(genre_playtime_map.begin(), genre_playtime_map.end());
    // 재생횟수 내림차순 정렬
    sort(genre_playtime_v.begin(), genre_playtime_v.end(), [](const auto& a, const auto& b) {
        return a.second > b.second;
    });
    
    for (auto& [genre, playtime_index_v]: genre_playtime_index_map) {
        // 재생횟수 내림차순, 인덱스 오름차순 정렬
        sort(playtime_index_v.begin(), playtime_index_v.end(), [](const auto& a, const auto& b) {
            if (a.first == b.first) {
                return a.second < b.second;
            }
            return a.first > b.first;
        });
    }
    
    for(auto& [genre, playtime]: genre_playtime_v) {
        auto& playtime_index_v = genre_playtime_index_map[genre];
        for (int i = 0; i < 2 && i < playtime_index_v.size(); i++) {
            answer.push_back(playtime_index_v[i].second);
        }
    }
    
    return answer;
}