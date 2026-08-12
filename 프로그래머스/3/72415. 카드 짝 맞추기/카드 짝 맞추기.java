import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

class Solution {
    
    static int[][] directions = new int[][]{{ 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
    
    public int solution(int[][] board, int r, int c) {
        int answer = 0;
        /**
        *   6 종류의 카드를 순차적으로 방문하는 6! = 720개의 경우의 수 각각을 판단하여 최솟값을 반환할 수 있다.
        *   각 카드가 2개(A, B)씩 존재하고 이때 A -> B / B -> A 순서를 바꿀 수 있으므로, 2^6 = 64개의 케이스가 존재하므로
        *   총 720 * 32 = 46080 개의 케이스를 검증해야 한다.
        *   따라서 N개의 카드에 대한 각 순열 내에서 동일한 카드를 방문하는 순서를 바꿔가며 최소 조작 횟수를 계산한다.
        *   각 칸을 이동할 때의 비용이 1이므로, BFS를 통해 한 좌표에서 다른 좌표로의 최단거리를 구할 수 있다.
        */
        
        List<int[]>[] cardCoordinates = new ArrayList[7];
        for (int i = 1; i < 7; i++) {
            cardCoordinates[i] = new ArrayList<>();
        }
        
        // 카드 수 계산 및 각 카드의 2가지 좌표 저장
        int card = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) {
                    card++;
                    cardCoordinates[board[i][j]].add(new int[]{ i, j });
                }
            }
        }
        card /= 2;
        
        answer = permutation(card, 0, board, r, c, new HashSet<>(), cardCoordinates, 0);
        
        return answer;   
    }
    
    // nPr
    int permutation(int n, int r, int[][] board, int initR, int initC, Set<Integer> cardSet, List<int[]>[] cardCoordinates, int count) {
        if (r == n) {
            return count;
        }
        
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (cardSet.add(i)) {                
                int tmpCount = 0;
                int[] caseA = cardCoordinates[i].get(0), caseB = cardCoordinates[i].get(1);
                
                // case 1 (A -> B)
                int[] caseAtoB1 = bfs(board, initR, initC, caseA[0], caseA[1]);
                tmpCount += caseAtoB1[2];
                board[caseAtoB1[0]][caseAtoB1[1]] = 0;
                int[] caseAtoB2 = bfs(board, caseAtoB1[0], caseAtoB1[1], caseB[0], caseB[1]);
                tmpCount += caseAtoB2[2];
                board[caseAtoB2[0]][caseAtoB2[1]] = 0;
                
                min = Math.min(permutation(n, r + 1, board, caseAtoB2[0], caseAtoB2[1], cardSet, cardCoordinates, count + tmpCount), min);
                
                // 카드 복구
                board[caseAtoB1[0]][caseAtoB1[1]] = i;
                board[caseAtoB2[0]][caseAtoB2[1]] = i;
                
                // case 2 (B -> A)
                int[] caseBtoA1 = bfs(board, initR, initC, caseB[0], caseB[1]);
                tmpCount = caseBtoA1[2];
                board[caseBtoA1[0]][caseBtoA1[1]] = 0;
                int[] caseBtoA2 = bfs(board, caseBtoA1[0], caseBtoA1[1], caseA[0], caseA[1]);
                tmpCount += caseBtoA2[2];
                board[caseBtoA2[0]][caseBtoA2[1]] = 0;
                
                min = Math.min(permutation(n, r + 1, board, caseBtoA2[0], caseBtoA2[1], cardSet, cardCoordinates, count + tmpCount), min);
                
                // 카드 복구
                board[caseBtoA1[0]][caseBtoA1[1]] = i;
                board[caseBtoA2[0]][caseBtoA2[1]] = i;
                
                cardSet.remove(i);
            }
        }
        
        return min;
    }
    
    // { 도착한 R, 도착한 C, 엔터를 포함한 조작 횟수 }
    int[] bfs(int[][] board, int startR, int startC, int targetR, int targetC) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{ startR, startC, 1 });
        
        boolean[][] isVisited = new boolean[5][5];
        isVisited[startR][startC] = true;
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int currR = curr[0], currC = curr[1], currCost = curr[2];
            
            if (currR == targetR && currC == targetC) {
                return new int[]{ currR, currC, currCost };
            }
            
            for (int[] direction: directions) {
                int nextR = currR + direction[0], nextC = currC + direction[1];
                if (nextR > -1 && nextR < 4 && nextC > -1 && nextC < 4 && !isVisited[nextR][nextC]) {
                    q.add(new int[]{ nextR, nextC, currCost + 1 });
                    isVisited[nextR][nextC] = true;
                }
                
                 while (nextR > -1 && nextR < 4 && nextC > -1 && nextC < 4 && board[nextR][nextC] == 0) {
                    nextR += direction[0];
                    nextC += direction[1];
                }
                
                if (nextR == -1 || nextR == 4 || nextC == -1 || nextC == 4) {
                    nextR -= direction[0];
                    nextC -= direction[1];
                }
                
                if (!isVisited[nextR][nextC]) {
                    q.add(new int[]{ nextR, nextC, currCost + 1 });
                    isVisited[nextR][nextC] = true;
                }
            }
        }
        
        return new int[]{ startR, startC, 0 };
    }
}