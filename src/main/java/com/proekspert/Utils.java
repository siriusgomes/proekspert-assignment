package com.proekspert;

public class Utils {

    public static void reverseTree(BinaryTreeNode node) {
        if (node == null) {
            return;
        }

        BinaryTreeNode temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);

        reverseTree(node.getLeft());
        reverseTree(node.getRight());
    }

    public static int height(BinaryTreeNode node)
    {
        if (node == null)
            return 0;
        else
        {
            int leftHeight = height(node.getLeft());
            int rightHeight = height(node.getRight());

            if (leftHeight > rightHeight)
                return (leftHeight + 1);
            else
                return (rightHeight + 1);
        }
    }
}
