#include <bits/stdc++.h>

using namespace std;

int directions[4][2] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
vector<vector<bool>> visited (102, vector<bool>(102));
vector<vector<bool>> rects (102, vector<bool>(102));

int item_x, item_y;

int dfs(int x, int y) {
    if (x == item_x && y == item_y) {
        visited[x][y] = false;
        return 1;
    }
    
    int result = INT_MAX;
    for (const auto& direction: directions) {
        int next_x = x + direction[0], next_y = y + direction[1];
        
        if (rects[next_x][next_y] && !visited[next_x][next_y]) {
            visited[next_x][next_y] = true;
            result = min(dfs(next_x, next_y) + 1, result);
        }
    }
    
    return result;
}

// 그려진 사각형에서, 겹치는 내부를 지운다.
void empty_inside(const vector<int>& rect) {
    int init_x = rect[0] * 2, init_y = rect[1] * 2, fin_x = rect[2] * 2, fin_y = rect[3] * 2;
    
    for (int x = init_x + 1; x < fin_x; x++) {
        for (int y = init_y + 1; y < fin_y; y++) {
            rects[x][y] = false;
        }
    }
}

void build_rect(const vector<int>& rect) {
    int init_x = rect[0] * 2, init_y = rect[1] * 2, fin_x = rect[2] * 2, fin_y = rect[3] * 2;
    
    for (int x = init_x; x <= fin_x; x++) {
        rects[x][init_y] = true;
        rects[x][fin_y] = true;
    }
    for (int y = init_y; y <= fin_y; y++) {
        rects[init_x][y] = true;
        rects[fin_x][y] = true;
    }
}

int solution(vector<vector<int>> rectangle, int characterX, int characterY, int itemX, int itemY) {
    int answer = 0;
    /**
    *   한 칸 짜리 사각형은 DFS/BFS 시 테두리를 따르지 않고 관통할 가능성이 있다.
    *   따라서 좌표를 2배하여 이러한 케이스를 제거한 뒤, 이후 이동 거리의 절반을 반환한다.
    */
    item_x = itemX * 2;
    item_y = itemY * 2;
    rects = vector<vector<bool>> (102, vector<bool>(102));
    for (const vector<int>& rect: rectangle) {
        build_rect(rect);
    }
    for (const vector<int>& rect: rectangle) {
        empty_inside(rect);
    }
    
    visited = vector<vector<bool>> (102, vector<bool>(102));
    visited[characterX * 2][characterY * 2] = true;
    answer = dfs(characterX * 2, characterY * 2) / 2;
    
    return answer;
}