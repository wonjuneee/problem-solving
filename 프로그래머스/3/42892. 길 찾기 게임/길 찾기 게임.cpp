#include <bits/stdc++.h>

using namespace std;

struct Node {
    int x, y, value;
    
    Node* left;
    Node* right;
    
    Node(int x, int y, int value): x(x), y(y), value(value), left(nullptr), right(nullptr) {}
};

struct Tree {
    Node* root;
    
    Tree() : root(nullptr) {}
    
    void insert(Node* node) {
        insertRecursive(root, node);
    }
    
    void insertRecursive(Node*& currNode, Node* node) {
        if (currNode == nullptr) {
            currNode = node;
            return;
        }
        
        if (currNode->x < node->x) {
            insertRecursive(currNode->right, node);
        } else {
            insertRecursive(currNode->left, node);
        }
    }
    
    void preorder(Node* node, vector<int>& result) {
        if (node == nullptr) {
            return;
        }
        
        result.push_back(node->value);
        preorder(node->left, result);
        preorder(node->right, result);
    }
    
    void postorder(Node* node, vector<int>& result) {
        if (node == nullptr) {
            return;
        }
        
        postorder(node->left, result);
        postorder(node->right, result);
        result.push_back(node->value);
    }
};

vector<vector<int>> solution(vector<vector<int>> nodeinfo) {
    vector<vector<int>> answer;
    /**
    *   트리를 구성하는 노드들은 다음과 같은 순서로 부모 - 자식 순서 및 좌우 위치가 결정된다.
    *   1. y값이 큰 노드가 작은 노드보다 부모로 위치한다.
    *   2. x값이 부모 노드보다 작은 노드는 부모 노드의 왼쪽 자식 노드로 위치한다.
    *   위 기준을 기준으로 각 노드의 적절한 위치를 찾아 매번 말단 노드를 추가한다. -> O(N logN)
    *   트리의 전위/후위 순회를 기록한다. -> O(N)
    */
    
    vector<Node> nodes;
    for (int i = 0; i < nodeinfo.size(); i++) {
        nodes.push_back(Node(nodeinfo[i][0], nodeinfo[i][1], i + 1));
    }
    
    sort(nodes.begin(), nodes.end(), [](const auto& a, const auto& b) {
        if (a.y == b.y) {
            // x값 기준 오름차순
            return a.x < b.x;
        }
        // y값 기준 내림차순
        return a.y > b.y;
    });
    Tree tree;
    
    for (auto& node: nodes) {
        tree.insert(&node);
    }
    
    vector<int> preorderResult;
    vector<int> postorderResult;
    
    tree.preorder(tree.root, preorderResult);
    tree.postorder(tree.root, postorderResult);
    
    answer.push_back(preorderResult);
    answer.push_back(postorderResult);
    
    return answer;
}