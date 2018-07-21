# Task
Make a botnet that can reverse a binary tree
# Requirements
 - Reversing a binary tree-You can initiate the reversal by providing a binary tree via HTTP request to any of the nodes. The response to that request has to be the reversed version of the provided binary tree.
 - Each  node  needs  to  distribute  the  reversal  task  between  its  known  nodes  (the  next node(s)  get(s)  a  part  of  the  binary  tree  to  reverse).  If  there  are  no  available  nodes  (or nodes are not responding) the node needs to reverse the treeitself.
 - Each binary tree node should contain an ID field.
 - You can provide each node with a list of addresses to the other nodes.
# Technical
- Language: Java 8+
- Frameworks: Any but no bot/botnet frameworks. One of the purposes of  this task is to see how You design it yourself.
## Optional for extra points:
- Binary tree-A binary node can be processed by the nodes only once -if a node has already processed a  part  of  a  binary  tree  it  cannot  process  another  part  of  the  same  tree  again.  Separate binary trees can be processed simultaneously.

Please  provide  us  with  source  code,  we  will  compile  it  ourselves.  The  building  and executing of the project should be easily doable and shouldn't require any extra work
