package modules.indexing.bst;

import models.Document;

public class BST {

    public BSTNode root;

    // INSERT
    public void insert(Document doc) {

        BSTNode newNode = new BSTNode(doc);

        if (root == null) {
            root = newNode;
            return;
        }

        BSTNode ptr = root;
        BSTNode pre = null;

        while (ptr != null) {

            pre = ptr;

            if (doc.documentId < ptr.doc.documentId)
                ptr = ptr.left;
            else
                ptr = ptr.right;
        }

        if (doc.documentId < pre.doc.documentId)
            pre.left = newNode;
        else
            pre.right = newNode;
    }

    // SEARCH
    public Document search(int id) {

        BSTNode ptr = root;

        while (ptr != null) {

            if (ptr.doc.documentId == id)
                return ptr.doc;

            else if (id < ptr.doc.documentId)
                ptr = ptr.left;

            else
                ptr = ptr.right;
        }

        return null;
    }

    // MINIMUM
    public void minimum() {

        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        BSTNode ptr = root;

        while (ptr.left != null)
            ptr = ptr.left;

        System.out.println("Minimum Document:");
        System.out.println(ptr.doc);
    }

    // MAXIMUM
    public void maximum() {

        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        BSTNode ptr = root;

        while (ptr.right != null)
            ptr = ptr.right;

        System.out.println("Maximum Document:");
        System.out.println(ptr.doc);
    }

    // INORDER
    public void inorder(BSTNode ptr) {

        if (ptr != null) {

            inorder(ptr.left);
            System.out.println(ptr.doc);
            inorder(ptr.right);
        }
    }

    // PREORDER
    public void preorder(BSTNode ptr) {

        if (ptr != null) {

            System.out.println(ptr.doc);
            preorder(ptr.left);
            preorder(ptr.right);
        }
    }

    // POSTORDER
    public void postorder(BSTNode ptr) {

        if (ptr != null) {

            postorder(ptr.left);
            postorder(ptr.right);
            System.out.println(ptr.doc);
        }
    }

    // DELETE
    public void delete(int id) {

        BSTNode ptr = root;
        BSTNode pre = null;

        while (ptr != null && ptr.doc.documentId != id) {

            pre = ptr;

            if (id < ptr.doc.documentId)
                ptr = ptr.left;
            else
                ptr = ptr.right;
        }

        if (ptr == null) {
            System.out.println("Document not found");
            return;
        }

        // LEAF NODE
        if (ptr.left == null && ptr.right == null) {

            if (pre == null)
                root = null;

            else if (pre.left == ptr)
                pre.left = null;

            else
                pre.right = null;
        }

        // TWO CHILDREN
        else if (ptr.left != null && ptr.right != null) {

            BSTNode ptr1 = ptr.left;
            BSTNode pre1 = ptr;

            while (ptr1.right != null) {
                pre1 = ptr1;
                ptr1 = ptr1.right;
            }

            ptr.doc = ptr1.doc;

            if (pre1.right == ptr1)
                pre1.right = ptr1.left;
            else
                pre1.left = ptr1.left;
        }

        // ONE CHILD
        else {

            BSTNode child;

            if (ptr.left != null)
                child = ptr.left;
            else
                child = ptr.right;

            if (pre == null)
                root = child;

            else if (pre.left == ptr)
                pre.left = child;

            else
                pre.right = child;
        }

        System.out.println("Document deleted successfully");
    }
}