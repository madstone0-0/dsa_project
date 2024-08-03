import java.util.ArrayList;

/**
 * Abstract class representing a node in a tree data structure.
 * Each node stores a piece of data and a reference to its parent node.
 *
 * @param <T> The type of data stored in the node. It must be comparable.
 */
public abstract class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>> {
    
    // The data stored in this node
    T data;
    
    // The parent of this node
    TreeNode<T> parent;

    /**
     * Constructs a TreeNode with the specified data and no parent.
     * 
     * @param data The data to be stored in this node.
     */
    TreeNode(T data) {
        this.data = data;
        this.parent = null; // Initialize parent to null
    }

    /**
     * Constructs a TreeNode with the specified data and parent.
     * 
     * @param data The data to be stored in this node.
     * @param parent The parent node of this node.
     */
    TreeNode(T data, TreeNode<T> parent) {
        this.data = data;
        this.parent = parent; // Set the parent of this node
    }

    /**
     * Returns a string representation of this node.
     * 
     * @return A string representing this node in the format "ClassName{data}".
     */
    @Override
    public String toString() {
        // Return a string with the class name and the data
        return String.format("%s{%s}", this.getClass().getSimpleName(), data.toString());
    }

    /**
     * Compares this node to another node based on their data.
     * 
     * @param other The node to compare this node to.
     * @return A negative integer, zero, or a positive integer as this node's data
     *         is less than, equal to, or greater than the other node's data.
     */
    @Override
    public int compareTo(TreeNode<T> other) {
        // Compare the data of this node with the data of the other node
        return this.data.compareTo(other.data);
    }
}
