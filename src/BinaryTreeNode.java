public class BinaryTreeNode<T extends Comparable<T>> extends TreeNode<T> {
    BinaryTreeNode<T> left;
    BinaryTreeNode<T> right;

    public BinaryTreeNode(T data) {
        super(data);
    }

    public BinaryTreeNode(T data, BinaryTreeNode<T> parent, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        super(data, parent);
        this.left = left;
        this.right = right;
    }

    public BinaryTreeNode(T data, BinaryTreeNode<T> parent) {
        super(data, parent);
    }

    public BinaryTreeNode(T data, TreeNode<T> parent) {
        super(data, parent);
    }
}
