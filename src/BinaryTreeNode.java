/**
 * Represents a node in a binary tree.
 * Each node contains data, and references to its left and right children.
 * 
 * @param <T> the type of data stored in the node, which must be comparable.
 */
public class BinaryTreeNode<T extends Comparable<T>> extends TreeNode<T> {
    // Reference to the left child node
    BinaryTreeNode<T> left;
    // Reference to the right child node
    BinaryTreeNode<T> right;

    /**
     * Constructs a BinaryTreeNode with the specified data.
     * 
     * @param data the data to be stored in the node.
     */
    public BinaryTreeNode(T data) {
        super(data); // Initialize the node with the provided data
    }

    /**
     * Constructs a BinaryTreeNode with the specified data, parent, left child, and right child.
     * 
     * @param data the data to be stored in the node.
     * @param parent the parent node of this node.
     * @param left the left child of this node.
     * @param right the right child of this node.
     */
    public BinaryTreeNode(T data, BinaryTreeNode<T> parent, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        super(data, parent); // Initialize the node with the data and parent
        this.left = left; // Set the left child
        this.right = right; // Set the right child
    }

    /**
     * Constructs a BinaryTreeNode with the specified data and parent.
     * 
     * @param data the data to be stored in the node.
     * @param parent the parent node of this node.
     */
    public BinaryTreeNode(T data, BinaryTreeNode<T> parent) {
        super(data, parent); // Initialize the node with the data and parent
    }

    /**
     * Constructs a BinaryTreeNode with the specified data and parent.
     * 
     * @param data the data to be stored in the node.
     * @param parent the parent node of this node.
     */
    public BinaryTreeNode(T data, TreeNode<T> parent) {
        super(data, parent); // Initialize the node with the data and parent
    }
}
