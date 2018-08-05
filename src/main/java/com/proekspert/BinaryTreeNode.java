package com.proekspert;

import java.util.logging.Level;

public class BinaryTreeNode {

    private Integer id;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode(Integer id, BinaryTreeNode left, BinaryTreeNode right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    public BinaryTreeNode(Integer id) {
        this.id = id;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void copyChildren(BinaryTreeNode node) {
        this.setLeft(node.getLeft());
        this.setRight(node.getRight());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Node: " + id);
        if (left != null) {
            sb.append(" - Left: " + left.id);
        }
        if (right != null) {
            sb.append(" - Right: " + right.id);
        }

        return sb.toString();
    }

}
