import java.util.Iterator;

/**
 * Implementation of a general tree data structure using a linked representation.
 * This tree allows nodes to have any number of children and supports basic tree operations.
 * 
 * @param <T> The type of elements stored in the tree. It must be comparable.
 */
public class LinkedGeneralTree<T extends Comparable<T>> extends AbGeneralTree<T> {
    
    // The root node of the tree
    private GeneralTreeNode<T> root = null;
    
    // The number of nodes in the tree
    private int size = 0;

    /**
     * Creates a new tree node with the specified value and parent.
     * 
     * @param val The value to be stored in the new node.
     * @param parent The parent of the new node.
     * @return A newly created GeneralTreeNode with the given value and parent.
     */
    protected GeneralTreeNode<T> createNode(T val, TreeNode<T> parent) {
        return new GeneralTreeNode<>(val, parent);
    }

    /**
     * Constructs an empty LinkedGeneralTree.
     */
    LinkedGeneralTree() {
    }

    /**
     * Validates if a given node is a valid GeneralTreeNode and is still in the tree.
     * 
     * @param n The node to validate.
     * @return The validated GeneralTreeNode.
     * @throws IllegalArgumentException if the node is not a GeneralTreeNode or if it is no longer in the tree.
     */
    protected GeneralTreeNode<T> validate(TreeNode<T> n) {
        // Check if the node is an instance of GeneralTreeNode
        if (!(n instanceof GeneralTreeNode)) throw new IllegalArgumentException("Invalid node");
        // Check if the node's parent reference is pointing to itself (indicating it is no longer in the tree)
        if (n.parent == n) throw new IllegalArgumentException("Node is no longer in the tree");
        return (GeneralTreeNode<T>) n;
    }

    /**
     * Returns the number of nodes in the tree.
     * 
     * @return The size of the tree.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the parent of a given node.
     * 
     * @param n The node whose parent is to be retrieved.
     * @return The parent node of the specified node.
     * @throws IllegalArgumentException if the node is invalid.
     */
    @Override
    public TreeNode<T> parent(TreeNode<T> n) throws IllegalArgumentException {
        var node = validate(n);
        return node.parent;
    }

    /**
     * Adds a new root to the tree with the specified value.
     * 
     * @param val The value to be stored in the root node.
     * @return The newly created root node.
     * @throws IllegalArgumentException if the tree is not empty.
     */
    public GeneralTreeNode<T> addRoot(T val) {
        if (!isEmpty()) throw new IllegalArgumentException("Tree is not empty");
        root = createNode(val, null);
        size = 1;
        return root;
    }

    /**
     * Adds a child node with the specified value to a given parent node.
     * 
     * @param n The parent node to which the child will be added.
     * @param val The value to be stored in the child node.
     * @return The newly created child node.
     * @throws IllegalArgumentException if the parent node is invalid.
     */
    public GeneralTreeNode<T> addChild(TreeNode<T> n, T val) {
        var parent = (GeneralTreeNode<T>) validate(n);
        var child = createNode(val, parent);
        parent.addChild(child);
        size++;
        return child;
    }

    /**
     * Sets the value of a given node and returns the old value.
     * 
     * @param n The node whose value is to be changed.
     * @param val The new value to set in the node.
     * @return The old value of the node.
     * @throws IllegalArgumentException if the node is invalid.
     */
    public T set(TreeNode<T> n, T val) {
        var node = validate(n);
        T oldValue = node.data;
        node.data = val;
        return oldValue;
    }

    /**
     * Removes a given node from the tree.
     * 
     * @param n The node to be removed.
     * @throws UnsupportedOperationException if attempting to remove the root node.
     */
    public void remove(TreeNode<T> n) {
        var node = (GeneralTreeNode<T>) validate(n);
        var numberOfChildren = numChildren(n);
        var children = children(n);
        if (node == root) {
            throw new UnsupportedOperationException("Cannot remove the root");
        } else {
            // Set each child node's parent reference to itself (disconnected from the tree)
            for (var child : children) {
                child.parent = child;
            }
            // Clear the children list of the node
            node.children.clear();
            // Remove this node from its parent's children list
            ((GeneralTreeNode<T>) node.parent).children.remove(node);
            // Disconnect the node from its parent
            node.parent = node;
            size -= numberOfChildren + 1; // Update the size of the tree
        }
    }

    /**
     * Returns the root node of the tree.
     * 
     * @return The root node of the tree.
     */
    @Override
    public TreeNode<T> root() {
        return root;
    }
}
