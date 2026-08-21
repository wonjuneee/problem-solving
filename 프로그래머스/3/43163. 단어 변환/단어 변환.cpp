#include <bits/stdc++.h>

using namespace std;

struct Node {
    string word;
    int count;
    
    Node(string word, int count): word(word), count(count) {}
};

int solution(string begin, string target, vector<string> words) {
    int answer = 0;
    /**
    *   최대 10개의 알파벳으로 구성된 50개의 단어가 있을 때, 26 * 10 * 50회의 연산이 수행될 수 있다. 따라서 완전탐색이 가능하다.
    *   각 인덱스 별 26개의 알파벳을 순회하며, words 배열에 존재하는 단어인지 판단한다.
    *   만일 존재하면 해당 단어를 포함한 Node를 큐에 담는다.
    *       - 한 단어를 기준으로, 한 글자를 수정했을 때 이동할 수 있는 다른 단어들로 BFS 순회 진행하는 것과 동일하다.
    */
    
    unordered_set<string> wordSet;
    for (auto& word: words) {
        wordSet.insert(word);
    }
    
    if (wordSet.count(target) == 0) {
        return answer;
    }
    
    queue<Node*> q;
    q.push(new Node(begin, 0));
    wordSet.erase(begin);
    
    while (!q.empty()) {
        Node* curr = q.front();
        q.pop();
        
        string currWord = curr->word;
        int count = curr->count;
        
        if (currWord == target) {
            answer = count;
            delete curr;
            break;
        }
        
        for (int i = 0; i < currWord.size(); i++) {
            string nextWord = currWord;
            for (int c = 'a'; c <= 'z'; c++) {    
                nextWord[i] = c;
                if (wordSet.count(nextWord)) {
                    q.push(new Node(nextWord, count + 1));
                    wordSet.erase(nextWord);
                }
            }
        }
        
        delete curr;
    }
    
    return answer;
}