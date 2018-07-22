# Technical choice
I started the task using the following technologies:
##### - Springboot framework: 
- I decided to use Springboot, which is a light framework that I am used to work with. It would be simple to develop an endpoint that could receive a HTTP request to receive the initial binary tree that should be reversed.
##### - JUnit
- A standard project always includes Unit testing, so I've decided to use JUnit. 
##### - Docker 
- Docker is a very nice way to solve the requirement of having various nodes that would do the same task. I basically specify these nodes on the `docker-compose.yml` file and tell all these containers to run the same application. Docker already sets up a network between the nodes, so they can communicate with each other as well. 
##### - Maven
- Maven would handle all the dependencies and the packaging and deploying of the Springboot application very easily. Everithing is inside the `pom.xml` file.



# How to run the project
The requirements to run the project are:
- Java 8
- Maven
- Docker (To run multiple nodes)

I've included a shell script `run.sh` that will do everything. It will start running:
```sh
mvn package spring-boot:repackage
```
This command will check the `pom.xml` file, download all the dependencies described in it (this might take a while), build the Spring-boot application, execute the unit testing and pack it in a jar file that the Docker containers will later run. Then the script will keep going and start docker with the command:
```sh
docker-compose up
```
This command will check the `docker-compose.yml` file, and build the containers described in it. I used the image `openjdk:8-jre-alpine` which is one of the lightest Java 8 images you can get on docker.


Once the application starts, the following endpoints will be available to receive the requests:
| Node | Port | Method | Description
| ------ | ------ | ------ | ------ |
| Node 1 | http://localhost:8080/ | GET | You will get back just a HelloWorld message. |
| Node 2 | http://localhost:8082/ | GET | You will get back just a HelloWorld message. |
| Node 3 | http://localhost:8083/ | GET | You will get back just a HelloWorld message. |
| Node 1 | http://localhost:8080/treeLocal | POST | Accepts a POST with the tree that will be reversed locally in that node.
| Node 2 | http://localhost:8082/treeLocal | POST | Accepts a POST with the tree that will be reversed locally in that node.
| Node 3 | http://localhost:8083/treeLocal | POST | Accepts a POST with the tree that will be reversed locally in that node.
| Node 1 | http://localhost:8080/treeDistributed | POST | Accepts a POST with the tree that will be reversed in all known nodes.
| Node 2 | http://localhost:8082/treeDistributed | POST | Accepts a POST with the tree that will be reversed in all known nodes.
| Node 3 | http://localhost:8083/treeDistributed | POST | Accepts a POST with the tree that will be reversed in all known nodes.

It is also possible to add more nodes to the solution. Basically just copy and paste the node on the `docker-compose.yml` file changing the name of the node (4, 5 and so on), the port that will be mapped to it's 8080 (8084, 8085 and so on) and add the new node as a initialization parameter on the entrypoint of all the nodes to let them know this new node exists:

````sh
node4:
    image: openjdk:8-jre-alpine
    restart: always
    ports:
      - 8084:8080
    volumes:
      - ./:/home
    entrypoint: ["java", "-jar", "/home/target/proekspert.assignment-1.0-SNAPSHOT.jar", "node1", "node2", "node3", "node4]
````

### Running without docker (1 node only)
It's also possible to run the application without docker. 

This command will run the application locally on the port 8080.
```
mvn spring-boot:run -Dspring-boot.run.arguments=localhost
```
If this port is already used, you can change it by editing the property `server.port` on the `src/main/resources/application.properties` file.
 
### Testing locally
I've included two unit tests on my solution. To run the tests, run the command:
```
mvn test -Dtest=ApplicationTest
```
They will test the endpoint that performs the reversion of the binary tree localy, given that there is no multiple nodes on the test. 
 

### Format of the Tree

This is the format of a acceptable tree that can be posted to the endpoints of my solution:
```
{ 
    "id" : 2, 
    "left" : { 
        "id" : 1
    },
    "right" : {
        "id" : 3
    }
}
```
Here are some curl requests that will run on the distributed endpoint. The ports can be changed to `8082` or `8083` as well.
```sh
curl -X POST \
  http://localhost:8080/treeDistributed \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 3064a184-ff72-98db-9037-782d9926a857' \
  -d '{"id":"50","left":{"id":"46","left":{"id":"22","left":{"id":"12","left":{"id":"1"},"right":{"id":"15"}},"right":{"id":"25","right":{"id":"30","left":{"id":"28","left":{"id":"26"}},"right":{"id":"40"}}}},"right":{"id":"48","right":{"id":"49"}}},"right":{"id":"59","left":{"id":"55"},"right":{"id":"100","left":{"id":"78","left":{"id":"67"},"right":{"id":"86","right":{"id":"92"}}}}}}'
```
```sh                 
curl -X POST \
  http://localhost:8080/treeDistributed \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6df5c936-8023-e1dc-a3eb-aee0c9888207' \
  -d '{"id":"2","left":{"id":"1","left":{"id":"6"}},"right":{"id":"3","left":{"id":"5","left":{"id":"5","right":{"id":"20"}}},"right":{"id":"10"}}}'
```
-- --
Below is the description of the task sent to me.
-- --
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
