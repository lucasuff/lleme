/*

    Gabriel Duarte
    Cintia Costa

*/

#include <bits/stdc++.h>
using namespace std;
#define pb push_back

class Avl{
private:
struct Node{
    double key, lazy;
    Atendimento at;
    struct Node *left;
    struct Node *right;
    int height;
};


void propagate(Node *no){
    if(no == NULL)
        return; 
    if(no->lazy != 0){
        no->key += no->lazy;
        if(no->left != NULL)
            no->left->lazy += no->lazy;
        if(no->right != NULL)
            no->right->lazy += no->lazy;
        no->lazy = 0;
    }
}

int height(Node *N){
    if (N == NULL)
        return 0;
    return N->height;
}

Node* newNode(double key, Atendimento at){
    Node* node   = new Node();
    node->key    = key;
    node->left   = NULL;
    node->right  = NULL;
    node->height = 1;
    node->lazy   = 0;
    node->at     = at;
    return node;
}
 
Node *rightRotate(Node *y){
    propagate(y);
    Node *x = y->left;
    Node *T2 = x->right;
 
    x->right = y;
    y->left = T2;

    y->height = max(height(y->left), height(y->right))+1;
    x->height = max(height(x->left), height(x->right))+1;
    return x;
}

Node *leftRotate(Node *x){
    propagate(x);
    Node *y = x->right;
    Node *T2 = y->left;
 
    y->left = x;
    x->right = T2;
 
    x->height = max(height(x->left), height(x->right))+1;
    y->height = max(height(y->left), height(y->right))+1;
 
    return y;
}
 
int getBalance(Node *N){
    propagate(N);
    if (N == NULL)
        return 0;
    return height(N->left) - height(N->right);
}

Node* balance(Node *root){
    int balance = getBalance(root);
 
    if(balance > 1 && getBalance(root->left) >= 0)
        return rightRotate(root);
 
    if(balance > 1 && getBalance(root->left) < 0){
        root->left =  leftRotate(root->left);
        return rightRotate(root);
    }
 
    if(balance < -1 && getBalance(root->right) <= 0)
        return leftRotate(root);
    if(balance < -1 && getBalance(root->right) > 0){
        root->right = rightRotate(root->right);
        return leftRotate(root);
    }
 
    return root;
} 

Node* insert(Node* node, double key, Atendimento at){
    propagate(node);
    if(node == NULL)
        return(newNode(key, at));
 
    if(key < node->key)
        node->left  = insert(node->left, key, at);
    else if (key > node->key)
        node->right = insert(node->right, key, at);
    else
        return node;
 
    node->height = 1 + max(height(node->left), height(node->right));
 
    return balance(node);
}



Node* minValueNode(Node* node){
    Node* current = node;
    while(current->left != NULL){
        propagate(current);
        current = current->left;
    }
    propagate(current);
    return current;
}

 
Node* maxValueNode(Node* node){
    Node* current = node;
    while(current->right != NULL){
        propagate(current);
        current = current->right;
    }
    propagate(current);
    return current;
}

Node* deleteNode(Node* root, double key){
    propagate(root);
    if(root == NULL)
        return root;
    if(key < root->key)
        root->left = deleteNode(root->left, key);
 
    else if(key > root->key)
        root->right = deleteNode(root->right, key);
 
    else{
        if((root->left == NULL) || (root->right == NULL)){
            Node *temp = root->left ? root->left : root->right;
 
            if(temp == NULL){
                temp = root;
                root = NULL;
            }
            else
             *root = *temp;
                           
            delete(temp);
        }
        else{
            Node* temp = minValueNode(root->right);
            root->key = temp->key;
            root->right = deleteNode(root->right, temp->key);
        }
    }

    if(root == NULL)
      return root;
 
    root->height = 1 + max(height(root->left), height(root->right));
 
    return balance(root);
}

void updateTree(Node *raiz, int val){
    if(raiz != NULL){
        raiz->lazy += val;
        propagate(raiz);
    }
}

Node *raiz;
int total;
public:
	void init(){
		raiz = NULL;
		total = 0;
	}
	void insert(double x, Atendimento at){
		raiz = insert(raiz, x, at);
		total++;
	}
	void deleteNode(){  
		raiz = deleteNode(raiz, maxValueNode(raiz)->key);
		total--;
	}
	void updateTree(double x){
		updateTree(raiz, x);
	}
	void updateTree(){
		updateTree(raiz, 1./45);
	}
	double bigger(){
		return maxValueNode(raiz)->key;
	}
	Atendimento getNext(){
		return maxValueNode(raiz)->at;
	}
	int getTotal(){
		return total;
	}
};
