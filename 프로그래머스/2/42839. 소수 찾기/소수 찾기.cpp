#include <bits/stdc++.h>

using namespace std;

bool isPrime(int number) {
    if (number <= 1) {
        return false;
    }
    
    for (int i = 2; i <= sqrt(number); i++) {
        if (number % i == 0) {
            return false;
        }
    }
    return true;
}
int solution(string numbers) {
    int answer = 0;
    /**
    *   크기가 작은 인풋이 들어오기 때문에, 모든 가능한 경우의 수를 탐색하여 그 개수를 반환하면 된다.
    *   이때 같은 숫자가 중복되어 있을 경우, 중복된 순열이 생성될 수 있으므로 해시셋으로 이미 검증된 수인지 추가로 판별한다.
    */
    unordered_set<int> checkedSet;
    // next_permutation()은 오름차순 기준으로 다음 순열을 반환하므로, 사전 정렬이 필요하다.
    sort(numbers.begin(), numbers.end(), less<>());
    
    do {
        for (int i = 1; i <= numbers.size(); i++) {
            // 각 순열의 부분문자열은 한자리 수를 제외한 나머지에 대해 중복이 발생하지 않는다.
            int number = stoi(numbers.substr(0, i));
            answer += (checkedSet.insert(number).second && isPrime(number) ? 1 : 0);
        }
    } while (next_permutation(numbers.begin(), numbers.end()));

    return answer;
}