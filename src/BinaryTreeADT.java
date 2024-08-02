/**
 * This interface defines the ADT for a binary tree. It extends the TreeADT
 * interface and provides methods specific to binary trees, such as retrieving
 * the left child, right child, and sibling of a given node.
 * 
 * @param <T> the type of elements stored in the tree. It must be comparable, 
 *            as specified by the Comparable<T> constraint.
 */
public interface BinaryTreeADT<T extends Comparable<T>> extends TreeADT<T> {

    /**
     * Returns the left child of a given node.
     * 
     * @param n the node whose left child is to be retrieved. Must be a valid node
     *          in the tree.
     * @return the left child of the specified node. If the node has no left child,
     *         this method returns null.
     * @throws IllegalArgumentException if the provided node is invalid or null.
     */
    BinaryTreeNode<T> left(TreeNode<T> n) throws IllegalArgumentException;

    /**
     * Returns the right child of a given node.
     * 
     * @param n the node whose right child is to be retrieved. Must be a valid node
     *          in the tree.
     * @return the right child of the specified node. If the node has no right child,
     *         this method returns null.
     * @throws IllegalArgumentException if the provided node is invalid or null.
     */
    BinaryTreeNode<T> right(TreeNode<T> n) throws IllegalArgumentException;

}
