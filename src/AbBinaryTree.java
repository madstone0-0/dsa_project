import java.util.ArrayList;

/**
 * Abstract class implementing the BinaryTreeADT interface for binary trees.
 * Provides implementations for methods to get a node's sibling, count its children,
 * and retrieve its children.
 *
 * @param <T> the type of elements stored in the tree. It must be comparable, 
 *            as specified by the Comparable<T> constraint.
 */
public abstract class AbBinaryTree<T extends Comparable<T>> extends AbTree<T> implements BinaryTreeADT<T> {

    /**
     * Returns the sibling of a given node.
     * 
     * @param n the node whose sibling is to be retrieved. Must be a valid node 
     *          in the tree.
     * @return the sibling of the specified node. If the node has no sibling 
     *         (i.e., it is the only child or there is no parent), this method 
     *         returns null.
     * @throws IllegalArgumentException if the provided node is invalid or 
     *                                   null.
     */
    @Override
    public BinaryTreeNode<T> sibling(TreeNode<T> n) throws IllegalArgumentException {
        var parent = parent(n); // Get the parent of the given node
        if (parent == null) return null; // No parent means no sibling

        // If the node is the left child, return the right child; otherwise, return the left child
        if (n == left(parent)) return right(parent);
        else return left(parent);
    }

    /**
     * Returns the number of children for a given node.
     * 
     * @param n the node whose number of children is to be counted. Must be a 
     *          valid node in the tree.
     * @return the number of children of the specified node. This is either 0, 1, 
     *         or 2 for binary trees.
     * @throws IllegalArgumentException if the provided node is invalid or 
     *                                   null.
     */
    @Override
    public int numChildren(TreeNode<T> n) throws IllegalArgumentException {
        int count = 0;
        // Check if the left child exists
        if (left(n) != null) count++;
        // Check if the right child exists
        if (right(n) != null) count++;
        return count;
    }

    /**
     * Returns an iterable collection of the children of a given node.
     * 
     * @param n the node whose children are to be retrieved. Must be a valid node 
     *          in the tree.
     * @return an iterable collection of the children of the specified node. 
     *         The collection may be empty if the node has no children.
     */
    @Override
    public Iterable<TreeNode<T>> children(TreeNode<T> n) {
        var buffer = new ArrayList<TreeNode<T>>(2); // Create a buffer to hold up to 2 children
        // Add the left child to the buffer if it exists
        if (left(n) != null) buffer.add(left(n));
        // Add the right child to the buffer if it exists
        if (right(n) != null) buffer.add(right(n));
        return buffer;
    }
}
