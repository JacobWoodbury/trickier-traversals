import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {
    if(node == null) return 0;
    int sum = sumLeafNodes(node.left) + sumLeafNodes(node.right);
    if(node.left == null && node.right == null) return sum + node.value;
    return sum;
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    if(node == null) return 0;
    int sum = countInternalNodes(node.right) + countInternalNodes(node.right);
    if(node.right == null && node.left == null){
      return 0;
    }
    
    return sum++;
    
  
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {
    if(node == null) return "";
    return buildPostOrderString(node.left) + buildPostOrderString(node.right) + node.value;
  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {
    Queue<TreeNode<T>> queue = new LinkedList<>();
    List<T> list = new ArrayList<>();
    if(node == null) return list;
    queue.add(node);

    while(!queue.isEmpty()){
      TreeNode<T> newNode = queue.poll();
      if(newNode != null){
        list.add(newNode.value);
        queue.add(newNode.left);
        queue.add(newNode.right);
      }
    }
    return list;
}

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
    Queue<TreeNode<Integer>> queue = new LinkedList<>();
    List<Integer> list = new ArrayList<>();
    if(node == null) return 0;
    queue.add(node);

    while(!queue.isEmpty()){
      TreeNode<Integer> newNode = queue.poll();
      if(newNode != null){
        if(!list.contains(newNode.value)){
          list.add(newNode.value);
        }
        queue.add(newNode.left);
        queue.add(newNode.right);
      }
    }
    return list.size();
  }


  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    if(node == null) return false;
    if(node.left == null && node.right == null) return true;
    if(node.left.value < node.value && node.right.value < node.value) return false;
    boolean check = false;
    if(node.left.value > node.value){
      check = hasStrictlyIncreasingPath(node.left);
    }
    if(node.right != null && node.right.value > node.value){
      check = hasStrictlyIncreasingPath(node.right);
    } 
    return check;
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    if(nodeA == null && nodeB == null) return true;
    if(nodeA == null || nodeB == null) return false;
    if(haveSameShape(nodeA.left, nodeB.left) && haveSameShape(nodeA.right, nodeB.right)) return true;
    return false;
  }


  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    
    Queue<T> prefix = new LinkedList<>();
    List<T> temp = new ArrayList<>();
    List<List<T>> full = new ArrayList<>();
    if(node == null) return full;
    if(node.left == null && node.right == null){
      temp.add(node.value);
      full.add(temp);
      return full;
    } 
    List<List<T>> left = findAllRootToLeafPathsPre(node.left, prefix, full);//find the left
    List<List<T>> right = findAllRootToLeafPathsPre(node.right, prefix, full);//find the right
    //add right to the left
    for(List<T> l : right){
      left.add(l);
    }
    return left;



    // List<List<T>> outerList = new ArrayList<>();
    // List<T> prefixList = new ArrayList<>();
    // prefixList.add(node.value);

    // if(node.left != null){
    //   outerList = findAllRootToLeafPathsPre(node.left, prefixList);
    // }

    // //if leaf - pop from end and return complete list
    // if(node.right != null){
    //   List<List<T>> temp = findAllRootToLeafPathsPre(node.right, prefixList);
    //   for(List<T> list : temp){
    //     outerList.add(list);
    //   }
    //}

    

    //check left exists, 
      //write root
      //add to que
      //add the left list

    //check right
      //write root
      //add to que
      //add the right list
    
  }
  
  //I found the idea for a prefix list when looking more into Language models. they store words as a linked list of letters, but prefixes branch to other letters creating new words.
  public static <T> List<List<T>> findAllRootToLeafPathsPre(TreeNode<T> node, Queue<T> prefixList, List<List<T>> fullList) {
    if(node == null) return fullList;

    prefixList.add(node.value);
    //check if leaf then add queue to fullList as a list
    if(node.left == null && node.right == null){
      List<T> prePrefix = List.copyOf(prefixList);
      List<T> prefix = new ArrayList<>();
      //reverse order
      for(T e : prePrefix){
        prefix.add(e);
      }
      fullList.add(prefix);
      prefixList.poll();
      return fullList;
    }


    if(node.left != null){
      List<List<T>> left = findAllRootToLeafPathsPre(node.left, prefixList, fullList);
      if(!left.isEmpty()) {
        for(List<T> l : left){
          fullList.add(l);
        }
      }
      
    }
    if(node.right != null){
      List<List<T>> right = findAllRootToLeafPathsPre(node.right, prefixList, fullList);
      if(!right.isEmpty()) {
        for(List<T> l : right){
          fullList.add(l);
        }
      }
    }
    
    prefixList.poll();
    return fullList;


    // List<List<T>> left = findAllRootToLeafPathsPre(node.left, prefixList, fullList);
    // List<List<T>> right = findAllRootToLeafPathsPre(node.right, prefixList, fullList);
  //   List<List<T>> outerList = new ArrayList<>();
  //   List<T> innerList = prefixList;
  //   innerList.add(node.value);
  //   if(node.left == null && node.right == null){

  //     prefixList.remove(prefixList.size()-1);
  //   }
    
  //   return null;
  // }

  }
}
