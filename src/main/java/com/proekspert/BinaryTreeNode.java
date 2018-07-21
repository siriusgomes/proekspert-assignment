package com.proekspert;

public class BinaryTreeNode {

    private BinaryTreeNode left;
    private BinaryTreeNode right;
    private Integer id;

    public BinaryTreeNode(BinaryTreeNode left, BinaryTreeNode right, Integer id) {
        this.left = left;
        this.right = right;
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


    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();

        sb.append("Node: " + id);
        if (left != null) {
            sb.append(" - Left: " + left.id);
        }
        if (right != null) {
            sb.append(" - Right: " + right.id);
        }
        sb.append("\n");
        sb.append(left != null ? left.toString() : "");
        sb.append(right != null? right.toString() : "");

        return sb.toString();
    }



}
