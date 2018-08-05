package com.proekspert;


import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class Node {

    Logger logger = Logger.getLogger(Node.class.getName());

    private Gson gson = new Gson();

    @RequestMapping("/")
    public @ResponseBody
    String greeting() {
        return "Hello World";
    }

    @RequestMapping(value = "/treeLocal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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


    @RequestMapping(value = "/nodeLocal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> nodeLocal(@RequestBody String data) {
        Gson gson = new Gson();
        BinaryTreeNode tree = gson.fromJson(data, BinaryTreeNode.class);

        Long millis = System.currentTimeMillis();
        logger.log(Level.INFO, "Locally processing the Node: " + tree);
        Utils.reverseCurrentNode(tree);
        logger.log(Level.INFO, "Reverted Node: " + tree);
        logger.log(Level.INFO, "Reversing task completed in " + (System.currentTimeMillis() - millis) + "ms");
        return new ResponseEntity<String>(gson.toJson(tree), HttpStatus.OK);
    }


    @RequestMapping(value = "/treeDistributed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> treeDistributed(@RequestBody String data) throws InterruptedException {

        BinaryTreeNode tree = gson.fromJson(data, BinaryTreeNode.class);

        logger.log(Level.INFO, "Distributed processing the Tree: " + data);
        Long millis = System.currentTimeMillis();

        // Converts each node of the tree to a list of nodes.
        List<BinaryTreeNode> listTree = Utils.treeToList(tree);

        // List of threads that will be executed
        List<Thread> listOfThreads = new ArrayList<Thread>();

        // Creates the threads
        listTree.forEach(node -> {
            Thread t = new Thread(() -> {
                BinaryTreeNode revertedNode = revertRemotely(node, Utils.getRandomNodeUrl() + "/nodeLocal");
                node.copyChildren(revertedNode);
            });
            listOfThreads.add(t);
        });

        // Starts all threads.
        listOfThreads.forEach(thread -> {
            thread.start();
        });

        // Wait for all threads to finish. Necessary because if we return with a response before all threads are finished, the result will be wrong.
        listOfThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        logger.log(Level.INFO, "Reversing task completed in " + (System.currentTimeMillis() - millis) + "ms");
        return new ResponseEntity<String>(gson.toJson(Utils.listToTree(new BinaryTreeNode(0), listTree)), HttpStatus.OK);
    }

    private BinaryTreeNode revertRemotely(BinaryTreeNode node, String address) {
        BinaryTreeNode returnTree = null;

        boolean hasReverted = false;
        String subtreeJson = gson.toJson(node);
        try {
            RestTemplate restTemplate = new RestTemplate();
            logger.log(Level.INFO, "Performing request to the address: " + address);
            String recursivelyRevertSubTree = restTemplate.postForObject(address, subtreeJson, String.class);
            returnTree = gson.fromJson(recursivelyRevertSubTree, BinaryTreeNode.class);
            hasReverted = true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error trying to revert subTree " + subtreeJson + " on the address " + address + ". Reverting locally.");
        }
        if (!hasReverted) {
            Utils.reverseCurrentNode(node);
            returnTree = node;
        }

        return returnTree;
    }


}
