import java.util.ArrayList;

/**
 * Represents a node in a general tree data structure.
 * Each node can have multiple children and stores a reference to its parent node.
 * 
 * @param <T> The type of data stored in the node. It must be comparable.
 */
public class GeneralTreeNode<T extends Comparable<T>> extends TreeNode<T> {
    
    // List of child nodes for this node
    ArrayList<TreeNode<T>> children = new ArrayList<>();

    // Alternative approach using a set to store children, which avoids duplicates and might offer more efficient lookups
    // but would require reinitialization for sorting attribute changes.
    // Set<TreeNode<T>> children = new TreeSet<>();

    /**
     * Constructs a GeneralTreeNode with the specified data and no parent.
     * 
     * @param data The data to be stored in this node.
     */
    GeneralTreeNode(T data) {
        super(data);
    }

    /**
     * Constructs a GeneralTreeNode with the specified data and parent.
     * 
     * @param data The data to be stored in this node.
     * @param parent The parent of this node.
     */
    GeneralTreeNode(T data, TreeNode<T> parent) {
        super(data, parent);
    }

    /**
     * Adds a child node to this node.
     * The child node's parent reference is updated to point to this node.
     * 
     * @param child The child node to be added.
     */
    public void addChild(GeneralTreeNode<T> child) {
        // Set the parent of the child node to this node
        child.parent = this;
        // Add the child node to the list of children
        children.add(child);
    }
}
