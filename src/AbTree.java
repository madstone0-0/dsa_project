import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a tree data structure.
 * Provides basic tree operations and traversal methods.
 *
 * @param <T> the type of data stored in the tree nodes, which must be comparable.
 */
public abstract class AbTree<T extends Comparable<T>> implements TreeADT<T> {

    /**
     * Checks if a given node is the root of the tree.
     * 
     * @param n the node to check.
     * @return true if the node is the root, false otherwise.
     * @throws IllegalArgumentException if the node is invalid.
     */
    @Override
    public boolean isRoot(TreeNode<T> n) throws IllegalArgumentException {
        return n == root();
    }

    /**
     * Checks if the tree is empty.
     * 
     * @return true if the tree is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Performs a preorder traversal of the subtree rooted at the given node.
     * 
     * @param n the root node of the subtree.
     * @param snap the list to store the nodes in preorder.
     */
    protected void preorderSubtree(TreeNode<T> n, List<TreeNode<T>> snap) {
        snap.add(n); // Add the current node to the list
        for (var c : children(n)) {
            preorderSubtree(c, snap); // Recursively add children nodes
        }
    }

    /**
     * Performs a postorder traversal of the subtree rooted at the given node.
     * 
     * @param n the root node of the subtree.
     * @param snap the list to store the nodes in postorder.
     */
    protected void postorderSubtree(TreeNode<T> n, List<TreeNode<T>> snap) {
        for (var c : children(n)) {
            postorderSubtree(c, snap); // Recursively add children nodes
        }
        snap.add(n); // Add the current node to the list after children
    }

    /**
     * Provides an iterable of nodes in preorder traversal.
     * 
     * @return an iterable list of nodes in preorder.
     */
    public Iterable<TreeNode<T>> preorder() {
        List<TreeNode<T>> snap = new ArrayList<>();
        if (!isEmpty()) preorderSubtree(root(), snap);
        return snap;
    }

    /**
     * Provides an iterable of nodes in postorder traversal.
     * 
     * @return an iterable list of nodes in postorder.
     */
    public Iterable<TreeNode<T>> postorder() {
        List<TreeNode<T>> snap = new ArrayList<>();
        if (!isEmpty()) postorderSubtree(root(), snap);
        return snap;
    }

}
