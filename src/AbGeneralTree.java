/**
 * Abstract class representing a general tree data structure.
 * 
 * @param <T> The type of elements stored in the tree. It must be comparable.
 */
public abstract class AbGeneralTree<T extends Comparable<T>> extends AbTree<T> {
    
    /**
     * Returns the number of children of a given node.
     * 
     * @param n The node whose children count is to be retrieved.
     * @return The number of children of the specified node.
     * @throws IllegalArgumentException if the node is not of the correct type.
     */
    @Override
    public int numChildren(TreeNode<T> n) throws IllegalArgumentException {
        // Cast the node to GeneralTreeNode to access its children
        return ((GeneralTreeNode<T>) n).children.size();
    }

    /**
     * Returns an iterable collection of the children of a given node.
     * 
     * @param n The node whose children are to be retrieved.
     * @return An iterable collection of the children of the specified node.
     */
    @Override
    public Iterable<TreeNode<T>> children(TreeNode<T> n) {
        // Cast the node to GeneralTreeNode to access its children
        return ((GeneralTreeNode<T>) n).children;
    }

    /**
     * Checks if this object is equal to another object.
     * 
     * @param object The object to compare this instance to.
     * @return true if the specified object is the same as this instance; false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // If the object is null, return false
        if (object == null) return false;
        
        // If the object is not of the same class, return false
        if (object.getClass() != this.getClass()) return false;

        // Return true if both objects are the same instance
        return object == this;
    }
}
