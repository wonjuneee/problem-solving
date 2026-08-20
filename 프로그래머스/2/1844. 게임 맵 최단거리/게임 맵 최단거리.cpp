#include <bits/stdc++.h>

using namespace std;

int bfs(vector<vector<int>>& maps, int rSize, int cSize);
bool inBoundary(int row, int col, int rSize, int cSize);
int directions[4][2] = {{ 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }};

int solution(vector<vector<int> > maps)
{
    int answer = 0;
    /**
    *   BFS 순회를 돌며, 최단거리를 찾는다.
    */
    
    answer = bfs(maps, maps.size(), maps[0].size());
    
    return answer;
}

int bfs(vector<vector<int>>& maps, int rSize, int cSize) {
    queue<array<int, 3>> q;
    q.push({ 0, 0, 1 });
    array<array<int, 100>, 100> visited;
    for (auto& v: visited) {
        fill(v.begin(), v.end(), false);
    }    
    visited[0][0] = true;
    
    while (!q.empty()) {
        array<int, 3> curr = q.front();
        q.pop();
        int row = curr[0], col = curr[1], cost = curr[2];
        
        if (row == rSize - 1 && col == cSize - 1) {
            return cost;
        }
        
        for (auto& direction: directions) {
            int nextRow = row + direction[0], nextCol = col + direction[1];
            
            if (inBoundary(nextRow, nextCol, rSize, cSize) && !visited[nextRow][nextCol] && maps[nextRow][nextCol] == 1) {
                visited[nextRow][nextCol] = true;
                q.push({ nextRow, nextCol, cost + 1 });
            }
        }
    }
    
    return -1;
}

bool inBoundary(int row, int col, int rSize, int cSize) {
    return row > -1 && row < rSize && col > -1 && col < cSize;
}