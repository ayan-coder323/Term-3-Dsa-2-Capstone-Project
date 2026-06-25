package modules.indexing.bst;

import models.Document;

public class BSTNode {

    public Document doc;
    public BSTNode left, right;

   

	public BSTNode(Document doc) {
		// TODO Auto-generated constructor stub
		this.doc = doc;
        left = right = null;
	}

	
}