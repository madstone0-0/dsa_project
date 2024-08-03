import java.util.Iterator;

/**
 * Interface defining the operations for a tree data structure.
 * 
 * @param <T> The type of elements stored in the tree. It must be comparable.
 */
public interface TreeADT<T extends Comparable<T>> {

    /**
     * Returns the root node of the tree.
     * 
     * @return The root node of the tree.
     */
    TreeNode<T> root();

    /**
     * Returns the parent of a given node.
     * 
     * @param n The node whose parent is to be retrieved.
     * @return The parent node of the specified node.
     * @throws IllegalArgumentException if the node is not in the tree or is invalid.
     */
    TreeNode<T> parent(TreeNode<T> n) throws IllegalArgumentException;

    /**
     * Returns an iterable collection of the children of a given node.
     * 
     * @param n The node whose children are to be retrieved.
     * @return An iterable collection of the children of the specified node.
     */
    Iterable<TreeNode<T>> children(TreeNode<T> n);

    /**
     * Returns the number of children of a given node.
     * 
     * @param n The node whose number of children is to be counted.
     * @return The number of children of the specified node.
     * @throws IllegalArgumentException if the node is not in the tree or is invalid.
     */
    int numChildren(TreeNode<T> n) throws IllegalArgumentException;

    /**
     * Checks if a given node is the root of the tree.
     * 
     * @param n The node to check.
     * @return true if the specified node is the root; false otherwise.
     * @throws IllegalArgumentException if the node is not in the tree or is invalid.
     */
    boolean isRoot(TreeNode<T> n) throws IllegalArgumentException;

    /**
     * Returns the total number of nodes in the tree.
     * 
     * @return The number of nodes in the tree.
     */
    int size();

    /**
     * Checks if the tree is empty (i.e., contains no nodes).
     * 
     * @return true if the tree is empty; false otherwise.
     */
    boolean isEmpty();
}
