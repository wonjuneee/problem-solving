class Solution {
    
    static final int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        
        /**
        * 사각형의 테두리를 따라 캐릭터가 이동한다.
        * 이동 가능한 모든 경로를 순회한 뒤, 최소값을 반환
        * 테두리의 좌표가 주어진 사각형들 중 하나 이상의 내부에 존재하는 경우는 최종적으로 이동 불가 판정
        */
        
        boolean[][] movable = new boolean[52][52];
        boolean[][] isVisited = new boolean[52][52];
        
        for (int[] rect: rectangle) {
            int lowX = rect[0], lowY = rect[1], highX = rect[2], highY = rect[3];
            
            for (int x = lowX; x <= highX; x++) {
                movable[x][lowY] = true;
                movable[x][highY] = true;
            }
            for (int y = lowY; y <= highY; y++) {
                movable[lowX][y] = true;
                movable[highX][y] = true;
            }
        }
        answer = traverse(rectangle, itemX, itemY, movable, isVisited, characterX, characterY, 0);
        
        
        return answer;
    }
    
    int traverse(int[][] rectangle, int itemX, int itemY, boolean[][] movable, boolean[][] isVisited, int currX, int currY, int dist) {
        if (currX == itemX && currY == itemY) {
            return dist;
        }
        
        isVisited[currX][currY] = true;
        
        int result = Integer.MAX_VALUE;
        for (int[] direction: directions) {
            int nextX = currX + direction[0], nextY = currY + direction[1];
            
            if (movable[nextX][nextY] 
                && !isVisited[nextX][nextY]
                && !checkInsideRect(rectangle, currX, currY, nextX, nextY)
                && checkAdjacentRect(rectangle, currX, currY, nextX, nextY)
            ) {
                result = Math.min(result, traverse(rectangle, itemX, itemY, movable, isVisited, nextX, nextY, dist + 1));
            }
        }
        
        return result;
    }
    
    /*
        다른 사각형에 겹쳐진 테두리 좌표는 겹쳐진 사각형의 내부에 위치한다.
        1. 하나 이상의 사각형 내부에 위치한 좌표를 판별하여 이동 불가하도록 한다.
        2. 한 사각형의 내부를 관통해 지나가는 좌표를 추가로 판별하여 이동 불가하도록 한다.
    */
    boolean checkInsideRect(int[][] rectangle, int currX, int currY, int nextX, int nextY) {
        System.out.println("curr: (" + currX + ", " + currY + "), next: (" + nextX + ", " + nextY + ")");
        
        for (int[] rect: rectangle) {
            // System.out.println("rectLow: (" + rect[0] + ", " + rect[1] + "), rectHigh: (" + rect[2] + ", " + rect[3] + ")");
            
            if ((rect[0] < nextX && rect[1] < nextY && rect[2] > nextX && rect[3] > nextY) // 사각형 내부 (테두리 미포함)
                || (isBorder(currX, currY, rect) && isBorder(nextX, nextY, rect)
                    && 
                    ((((currX == rect[0] && nextX == rect[2]) || (currX == rect[2] && nextX == rect[0])) && (currY != rect[1] && currY != rect[3]))
                    || (((currY == rect[1] && nextY == rect[3]) || (currY == rect[3] && nextY == rect[1])) && (currX != rect[0] && currX != rect[2])))
                   )
            ) {
                System.out.println("rectLow: (" + rect[0] + ", " + rect[1] + "), rectHigh: (" + rect[2] + ", " + rect[3] + ")");
                System.out.print("사유: ");
                if (rect[0] < nextX && rect[1] < nextY && rect[2] > nextX && rect[3] > nextY) {
                    System.out.println("사각형 내부");
                } else if (isBorder(currX, currY, rect) && isBorder(nextX, nextY, rect)
                    && (((currX == rect[0] && nextX == rect[2]) || (currX == rect[2] && nextX == rect[0])) && (currY != rect[1] && currY != rect[3]))
                ) {
                    System.out.println("가로 한 칸짜리 사각형 관통");
                } else if (isBorder(currX, currY, rect) && isBorder(nextX, nextY, rect)
                    && (((currY == rect[1] && nextY == rect[3]) || (currY == rect[3] && nextY == rect[1])) && (currX != rect[0] && currX != rect[2]))
                ) {
                    System.out.println("세로 한 칸짜리 사각형 관통");
                }
                return true;
            }
        }
        
        return false;
    }
    
    /*
        인접하지 않은 사각형 간 이동해선 안된다.
        1. 현재좌표 - 다음좌표가 동일한 사각형 테두리에 위치하는지 판별한다.
    */
    boolean checkAdjacentRect(int[][] rectangle, int currX, int currY, int nextX, int nextY) {
        // System.out.println("curr: (" + currX + ", " + currY + "), next: (" + nextX + ", " + nextY + ")");
        for (int[] rect: rectangle) {            
            // System.out.println("rectLow: (" + rect[0] + ", " + rect[1] + "), rectHigh: (" + rect[2] + ", " + rect[3] + ")");
            // System.out.println(
            //     ((rect[0] == currX && rect[1] <= currY && rect[3] >= currY) && (rect[0] == nextX && rect[1] <= nextY && rect[3] >= nextY))
            //     || ((rect[2] == currX && rect[1] <= currY && rect[3] >= currY) && (rect[2] == nextX && rect[1] <= nextY && rect[3] >= nextY))
            //     || ((rect[1] == currY && rect[0] <= currX && rect[2] >= currX) && (rect[1] == nextY && rect[0] <= nextX && rect[2] >= nextX))
            //     || ((rect[3] == currY && rect[0] <= currX && rect[2] >= currX) && (rect[3] == nextY && rect[0] <= nextX && rect[2] >= nextX))
            // );
            
            if (((rect[0] == currX && rect[1] <= currY && rect[3] >= currY) && (rect[0] == nextX && rect[1] <= nextY && rect[3] >= nextY))
                || ((rect[2] == currX && rect[1] <= currY && rect[3] >= currY) && (rect[2] == nextX && rect[1] <= nextY && rect[3] >= nextY))
                || ((rect[1] == currY && rect[0] <= currX && rect[2] >= currX) && (rect[1] == nextY && rect[0] <= nextX && rect[2] >= nextX))
                || ((rect[3] == currY && rect[0] <= currX && rect[2] >= currX) && (rect[3] == nextY && rect[0] <= nextX && rect[2] >= nextX))
            ) {
                return true;
            }
            
        }
        return false;
    }
    
    /*
        주어진 사각형의 테두리에 위치하는 좌표인지 판별하는 함수
    */
    boolean isBorder(int x, int y, int[] rect) {
        return (x == rect[0] && y >= rect[1] && y <= rect[3])   // 좌측 테두리
            || (x == rect[2] && y >= rect[1] && y <= rect[3])   // 우측 테두리
            || (y == rect[1] && x >= rect[0] && x <= rect[2])   // 하단 테두리
            || (y == rect[3] && x >= rect[0] && x <= rect[2]);  // 상단 테두리
    }
}