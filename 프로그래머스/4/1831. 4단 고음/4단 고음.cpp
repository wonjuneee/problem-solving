#include <bits/stdc++.h>

using namespace std;

vector<long long> pow3 (21);

int recursive(int tune, int mul, int sum) {
    int result = 0;
    
    // 현재 사용된 + 중 *와 매칭되지 않은 +의 개수에 2를 나누면 앞으로 필요한 *의 개수가 나온다.
    // 이때 필요한 * 개수만큼 3의 거듭제곱 수를 구하면 현재 +, *로 구성된 문자열이 올바른 문자열을 구성할 수 있는 후보가 될 수 있는 최댓값이 된다.
    long long mulNeeded = (sum - (mul * 2)) / 2;
    
    if (tune == 1 && mul * 2 == sum) {
        return 1;
    } else if (tune == 1 && mul * 2 != sum) {
        return 0;
    } else if (mulNeeded > 20 || tune < pow3[mulNeeded]) {
        return 0;
    }
    
    if (tune % 3 == 0 && mulNeeded > 0) {
        result += recursive(tune / 3, mul + 1, sum);
    }
    result += recursive(tune - 1, mul, sum + 1);
    
    return result;
}

// 전역 변수를 정의할 경우 함수 내에 초기화 코드를 꼭 작성해주세요.
int solution(int n) {
    int answer = 0;
    /**
    *   재귀를 통해 현재 음높이가 3으로 나누어 떨어질 경우 n/3을, 그렇지 않으면 n-1을 넘겨준다.
    *   만일 *과 ++의 쌍이 맞지 않으면 0을 반환하고, 쌍이 맞고 음높이가 1까지 내려갈 수 있으면 가능한 문자열 한 개가 존재하므로 1을 반환한다.
    *   이때 마지막 두 기호는 반드시 ++ 이어야 함을 고려한다.
    */
    
    for (int i = 0; i <= 20; i++) {
        pow3[i] = pow(3, i);
    }
    
    answer = recursive(n - 2, 0, 2);
    
    return answer;
}