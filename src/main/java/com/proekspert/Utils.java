package com.proekspert;

import java.util.Random;

public class Utils {

    public static void reverseTreeRecursively(BinaryTreeNode node) {
        if (node == null) {
            return;
        }

        BinaryTreeNode temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);

        reverseTreeRecursively(node.getLeft());
        reverseTreeRecursively(node.getRight());
    }

    public static void reverseNode(BinaryTreeNode node) {
        if (node == null) {
            return;
        }

        BinaryTreeNode temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);
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

    public static int numberOfReversableNodes(BinaryTreeNode node) {
        if (node != null && (node.getLeft() != null || node.getRight() != null)) {
            return 1 + numberOfReversableNodes(node.getLeft()) + numberOfReversableNodes(node.getRight());
        }
        else {
            return 0;
        }
    }

    public static String getRandomNodeUrl() {
        int host = new Random().nextInt(Application.listHosts.size());
        return "http://" + Application.listHosts.get(host) + ":8080";
    }



}
