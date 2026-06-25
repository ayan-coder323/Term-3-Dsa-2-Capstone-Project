package modules.indexing.avl;

import models.Document;

public class AVLTree {

    public AVLNode root;

    int height(AVLNode node) {

        if (node == null)
            return 0;

        return node.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    int getBalance(AVLNode node) {

        if (node == null)
            return 0;

        return height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode node) {

        AVLNode leftChild = node.left;
        AVLNode temp = leftChild.right;

        leftChild.right = node;
        node.left = temp;

        node.height = 1 + max(height(node.left), height(node.right));
        leftChild.height = 1 + max(height(leftChild.left), height(leftChild.right));

        return leftChild;
    }

    AVLNode leftRotate(AVLNode node) {

        AVLNode rightChild = node.right;
        AVLNode temp = rightChild.left;

        rightChild.left = node;
        node.right = temp;

        node.height = 1 + max(height(node.left), height(node.right));
        rightChild.height = 1 + max(height(rightChild.left), height(rightChild.right));

        return rightChild;
    }

    public AVLNode insert(AVLNode root, Document doc) {

        if (root == null)
            return new AVLNode(doc);

        if (doc.documentId < root.doc.documentId)
            root.left = insert(root.left, doc);

        else if (doc.documentId > root.doc.documentId)
            root.right = insert(root.right, doc);

        else
            return root;

        root.height = 1 + max(height(root.left), height(root.right));

        int bf = getBalance(root);

        // LL
        if (bf > 1 && doc.documentId < root.left.doc.documentId)
            return rightRotate(root);

        // RR
        if (bf < -1 && doc.documentId > root.right.doc.documentId)
            return leftRotate(root);

        // LR
        if (bf > 1 && doc.documentId > root.left.doc.documentId) {

            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RL
        if (bf < -1 && doc.documentId < root.right.doc.documentId) {

            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // SEARCH
    public Document search(AVLNode root, int id) {

        if (root == null)
            return null;

        if (id == root.doc.documentId)
            return root.doc;

        if (id < root.doc.documentId)
            return search(root.left, id);

        return search(root.right, id);
    }

    // INORDER
    public void inorder(AVLNode node) {

        if (node != null) {

            inorder(node.left);
            System.out.println(node.doc);
            inorder(node.right);
        }
    }

    // PREORDER
    public void preorder(AVLNode node) {

        if (node != null) {

            System.out.println(node.doc);
            preorder(node.left);
            preorder(node.right);
        }
    }

    // POSTORDER
    public void postorder(AVLNode node) {

        if (node != null) {

            postorder(node.left);
            postorder(node.right);
            System.out.println(node.doc);
        }
    }
}