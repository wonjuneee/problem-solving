#include <bits/stdc++.h>

using namespace std;

unordered_set<int> checkedSet;

bool isPrime(int number) {
    checkedSet.insert(number);
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

int permutation(string strNumber, int n, int r, string numbers, vector<bool> isChosen) {
    if (r == 0) {
        int numb = stoi(strNumber);
        return !checkedSet.count(numb) && isPrime(numb) ? 1 : 0;
    }
    
    int result = 0;
    for (int i = 0; i < numbers.size(); i++) {
        if (!isChosen[i]) {
            strNumber.push_back(numbers[i]);
            isChosen[i] = true;
            result += permutation(strNumber, n, r - 1, numbers, isChosen);
            
            strNumber.pop_back();
            isChosen[i] = false;
        }
    }
    
    return result;
}

int solution(string numbers) {
    int answer = 0;
    /**
    *   크기가 작은 인풋이 들어오기 때문에, 모든 가능한 경우의 수를 탐색하여 그 개수를 반환하면 된다.
    *   이때 같은 숫자가 중복되어 있을 경우, 중복된 순열이 생성될 수 있으므로 해시셋으로 이미 검증된 수인지 추가로 판별한다.
    */
    
    for (int i = 1; i <= numbers.size(); i++) {
        answer += permutation("", numbers.size(), i, numbers, vector<bool>(numbers.size()));
    }
    
    return answer;
}