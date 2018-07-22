package com.proekspert;


import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class Node {

    Logger logger = Logger.getLogger(Node.class.getName());

    private Gson gson = new Gson();

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello World";
    }

    @RequestMapping(value = "/treeLocal", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> treeLocal(@RequestBody String data) {
        Gson gson = new Gson();
        BinaryTreeNode tree = gson.fromJson(data, BinaryTreeNode.class);

        logger.log(Level.INFO, "Locally processing the Tree: " + data);
        logger.log(Level.INFO, "Height of the Tree: " + Utils.height(tree));
        logger.log(Level.INFO, "Number of binary tree nodes do be reverted: " + Utils.numberOfReversableNodes(tree));

        Long millis = System.currentTimeMillis();
        Utils.reverseTreeRecursively(tree);
        logger.log(Level.INFO, "Reversing task completed in " + (System.currentTimeMillis() - millis) + "ms");

        return new ResponseEntity<String>(gson.toJson(tree), HttpStatus.OK);
    }


    @RequestMapping(value = "/treeDistributed", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> treeDistributed(@RequestBody String data) {

        BinaryTreeNode tree = gson.fromJson(data, BinaryTreeNode.class);

        logger.log(Level.INFO, "Distributed processing the Tree: " + data);

        Long millis = System.currentTimeMillis();

        // Reverts actual node locally.
        Utils.reverseCurrentNode(tree);

        // Calls other nodes to revert left and right subtrees.
        if (tree.getLeft() != null) {
            tree.setLeft(revertRemotely(tree.getLeft(), Utils.getRandomNodeUrl() + "/treeDistributed"));
        }
        if (tree.getRight() != null) {
            tree.setRight(revertRemotely(tree.getRight(), Utils.getRandomNodeUrl() + "/treeDistributed"));
        }
        logger.log(Level.INFO, "Reversing task completed in " + (System.currentTimeMillis() - millis) + "ms");
        return new ResponseEntity<String>(gson.toJson(tree), HttpStatus.OK);
    }


    private BinaryTreeNode revertRemotely(BinaryTreeNode subTree, String address) {
        BinaryTreeNode returnTree = null;

        boolean hasReverted = false;
        String subtreeJson = gson.toJson(subTree);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String recursivelyRevertSubTree = restTemplate.postForObject(address, subtreeJson, String.class);
            logger.log(Level.INFO, "Performing request to the address: " + address);
            returnTree = gson.fromJson(recursivelyRevertSubTree, BinaryTreeNode.class);
            hasReverted = true;
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Error trying to revert subTree " + subtreeJson + " on the address " + address + ". Reverting locally.");
        }
        if (!hasReverted) {
            Utils.reverseCurrentNode(subTree);
            returnTree = subTree;
        }

        return returnTree;
    }


}
