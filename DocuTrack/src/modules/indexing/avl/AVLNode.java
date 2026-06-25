package modules.indexing.avl;

import models.Document;

public class AVLNode {

    public Document doc;
    int height;

    AVLNode left, right;

    public AVLNode(Document doc) {
        this.doc = doc;
        this.height = 1;
    }
}
