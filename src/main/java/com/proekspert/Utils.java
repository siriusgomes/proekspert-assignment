package com.proekspert;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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

    public static List<BinaryTreeNode> treeToList(BinaryTreeNode node) {
        List<BinaryTreeNode> list = new ArrayList<BinaryTreeNode>();
        if (node == null) {
            return new ArrayList<BinaryTreeNode>();
        }

        BinaryTreeNode temp = new BinaryTreeNode(node.getId());
        if (node.getLeft() != null)
            temp.setLeft(new BinaryTreeNode(node.getLeft().getId()));

        if (node.getRight() != null)
            temp.setRight(new BinaryTreeNode(node.getRight().getId()));

        list.add(temp);
        list.addAll(treeToList(node.getLeft()));
        list.addAll(treeToList(node.getRight()));

        return list;
    }

    public static BinaryTreeNode listToTree(BinaryTreeNode node, List<BinaryTreeNode> list) {
        if (!CollectionUtils.isEmpty(list)) {
            node = list.remove(0);

            if (node.getRight() != null) {
                node.setRight(listToTree(node.getRight(), list));
            }
            if (node.getLeft() != null) {
                node.setLeft(listToTree(node.getLeft(), list));
            }
        }
        return node;
    }


    public static void reverseCurrentNode(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        BinaryTreeNode temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);
    }

    public static int height(BinaryTreeNode node) {
        if (node == null)
            return 0;
        else {
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
        } else {
            return 0;
        }
    }

    /**
     * My primitive load balance based on RANDOMNESS to pick a node url.
     */
    public static String getRandomNodeUrl() {
        if (Application.listHosts.size() > 0) {
            int host = new Random().nextInt(Application.listHosts.size());
            return "http://" + Application.listHosts.get(host) + ":8080";
        } else {
            return "http://localhost:8080";
        }
    }


}
