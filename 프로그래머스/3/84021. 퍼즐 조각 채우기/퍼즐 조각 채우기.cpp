#include <bits/stdc++.h>

using namespace std;

vector<vector<int>> directions = {{ 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }};
int puzzleCount = -1;
vector<vector<pair<int, int>>> puzzles;

bool in_boundary(int row, int col, int n) {
    return row > -1 && row < n && col > -1 && col < n;
}

// 퍼즐 채우기
void dfs(vector<vector<int>>& map, vector<vector<bool>>& visited, int row, int col) {
    puzzles[puzzleCount].push_back({ row, col });
    
    for (auto& direction: directions) {
        int nextRow = row + direction[0], nextCol = col + direction[1];
        
        if (in_boundary(nextRow, nextCol, map.size()) && !visited[nextRow][nextCol] && map[nextRow][nextCol] == 1) {
            visited[nextRow][nextCol] = true;
            dfs(map, visited, nextRow, nextCol);
        }
    }
}

// (0, 0)으로 정규화
void normalize(vector<pair<int, int>>& puzzle) {
    int minRow = INT_MAX, minCol = INT_MAX; 
    for (auto [row, col]: puzzle) {
        minRow = min(row, minRow);
        minCol = min(col, minCol);
    }

    for (auto& cell: puzzle) {
        cell.first -= minRow;
        cell.second -= minCol;
    }

    sort(puzzle.begin(), puzzle.end());
}

// (r, c) -> (c, -r)
void rotate(vector<pair<int, int>>& puzzle) {
    // 90도 회전
    for (auto& cell: puzzle) {
        int tmp = cell.first;
        cell.first = cell.second;
        cell.second = -tmp;
    }

    normalize(puzzle);
}

// game_board의 연결된 빈칸(0)을 찾고, 각 퍼즐에 대해 일치 여부 판별
int fill(vector<vector<int>>& map, vector<vector<bool>>& visited, vector<bool>& is_used, int row, int col) {
    vector<pair<int, int>> space;
    stack<pair<int, int>> stc;
    visited[row][col] = true;
    
    stc.push({ row, col });
    while (!stc.empty()) {
        pair<int, int> curr = stc.top();
        stc.pop();
        int currRow = curr.first, currCol = curr.second;

        space.push_back({ currRow, currCol });
        for (auto& direction: directions) {
            int nextRow = currRow + direction[0], nextCol = currCol + direction[1];

            if (in_boundary(nextRow, nextCol, map.size()) && !visited[nextRow][nextCol] && map[nextRow][nextCol] == 0) {
                visited[nextRow][nextCol] = true;
                stc.push({ nextRow, nextCol });
            }
        }
    }
    
    normalize(space);
    
    for (int i = 0; i < puzzles.size(); i++) {
        if (is_used[i]) {
            continue;
        }
        
        auto& puzzle = puzzles[i];
            
        for (int j = 0; j < 4; j++) {
            rotate(puzzle);
            if (space == puzzle) {
                is_used[i] = true;
                return space.size();
            }
        }
    }
    
    return 0;
}

int solution(vector<vector<int>> game_board, vector<vector<int>> table) {
    int answer = 0;
    /**
    *   퍼즐은 회전이 가능하다. 이때, 한 퍼즐이 회전한 케이스를 (0, 0)으로 옮겨 계산을 편리하게 할 수 있다.
    *   퍼즐의 각 칸이 회전한 좌표에 대해, 행과 열 중 가장 작은 값을 전체 좌표에 각각 빼주면 된다.
    */
    
    vector<vector<bool>> visited (table.size(), vector<bool>(table.size()));
    for (int row = 0; row < table.size(); row++) {
        for (int col = 0; col < table.size(); col++) {
            if (!visited[row][col] && table[row][col] == 1) {
                puzzleCount++;
                puzzles.push_back(vector<pair<int, int>>());
                visited[row][col] = true;
                dfs(table, visited, row, col);
            }
        }
    }
    
    // 모든 퍼즐조각을 (0, 0) 좌표로 정규화한다.
    for (auto& puzzle: puzzles) {
        normalize(puzzle);
    }
    
    visited.assign(table.size(), vector<bool>(table.size()));
    vector<bool> is_used(puzzleCount + 1);
    for (int row = 0; row < game_board.size(); row++) {
        for (int col = 0; col < game_board.size(); col++) {
            if (!visited[row][col] && game_board[row][col] == 0) {
                answer += fill(game_board, visited, is_used, row, col);
            }
        }
    }
    
    return answer;
}