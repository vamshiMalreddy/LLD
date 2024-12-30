/*
ring of n - numbers
 * nodes -> hash(0,n) : what to do we hash - node_id
 * request -> hash(0,n) : we can hash request_id
 * assign request to a node -> nearest hased_node clockwise 
 * adding nodes
 * removing nodes
 * virtual nodes -> use multiple hash functions
 * 
 */

 public interface HashFunction{
    public int hash(String key);
 }

 public class HashFunctionImpl{
    MessageDigest md = MessageDigest.getInstance("MD5");

    public int hash(String key){
        byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));
        return Math.abs(Arrays.hashCode(digest));
    }

 }

 public class ConsistentHashing{
    int VIRTUAL_NODES;
    TreeMap<Integer,Node> hashRing = new TreeMap<Integer,Node>();  
    HashFunction hashFunction;

    public ConsistentHashing(List<int> nodeIds, HashFunction hashFunction){
        this.hashFunction = hashFunction;
        for(int nodeId: nodeIds){
            hashRing.put(this.hashFunction.hash(nodeId),nodeId);
        }
    }

    public Node assignRequest(int requestId){
        // int hashedRequestId = hashFunction.hash(requestId);
        // int assignedNodeId = hashRing.firstEntry().getValue();
        // for (Map.Entry<int,int> hashedNodeEntry : hashedNodes.entrySet()){
        //     if (hashedRequestId <= hashedNodeEntry.getKey()) {
        //         assignedNodeId = hashedNodeEntry.getValue();
        //     }
        // }
        // return assignedNodeId;
        int hash = hashFunction.hash(requestId);
        if (!hashRing.containsKey(hashedRequestId)){
            TreeMap<Integer,Node> tailMap = hashRing.tailMap(hashedRequestId);
            hash = tailMap.isEmpty() ? hashRing.firstEntry().getKey() : tailMap.firstEntry().getKey();
        }
        return hashRing.get(hash);
    }

    void addNode(Node node){
        for(int i = 0; i < VIRTUAL_NODES; i++){
            int hashedNodeId = hashFunction.hash(node.getNodeId() + "#" + i);
            hashRing.put(hashedNodeId, node);
        }
    }

    void removeNode(int nodeId){
        for(int i = 0; i < VIRTUAL_NODES; i++){
            int hashedNodeId = hashFunction.hash(node.getNodeId() + "#" + i);
            hashRing.remove(hashedNodeId);
        }
    }
 }

 Node{
    String nodeId;
 }

//  Request{
//     int id;
//  }