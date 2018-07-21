package com.proekspert;


import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

@RestController
public class Node {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello World";
    }

    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public @ResponseBody String treePost(@RequestBody String data) {

        Gson gson = new Gson();
        BinaryTreeNode tree = gson.fromJson(data, BinaryTreeNode.class);


        System.out.println("Height: " + Utils.height(tree));

        Utils.reverseTree(tree);

        return tree.toString();
    }



}
