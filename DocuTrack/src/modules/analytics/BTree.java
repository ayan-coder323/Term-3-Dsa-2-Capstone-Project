package modules.analytics;

class BTreeNode {

    int key;
    BTreeNode left;
    BTreeNode right;

    BTreeNode(int key) {

        this.key = key;
        left = right = null;
    }
}

public class BTree {

    BTreeNode root;

   public void insert(int key) {

        root = insertRec(root, key);
    }

    BTreeNode insertRec(
            BTreeNode root,
            int key) {

        if(root == null)
            return new BTreeNode(key);

        if(key < root.key)
            root.left =
                    insertRec(
                            root.left,
                            key);

        else
            root.right =
                    insertRec(
                            root.right,
                            key);

        return root;
    }

    public void inorder(BTreeNode root) {

        if(root != null) {

            inorder(root.left);

            System.out.print(
                    root.key + " ");

            inorder(root.right);
        }
    }
    public void display() {

        if(root == null) {

            System.out.println("BTree is Empty");
            return;
        }

        System.out.print("BTree Contents: ");
        inorder(root);
        System.out.println();
    }
}
