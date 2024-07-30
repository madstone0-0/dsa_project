import java.util.Iterator;

public class LinkedBinaryTree<T extends Comparable<T>> extends AbBinaryTree<T> {
    private BinaryTreeNode<T> root = null;
    private int size = 0;

    protected BinaryTreeNode<T> createNode(T t, BinaryTreeNode<T> parent, BinaryTreeNode<T> left,
                                           BinaryTreeNode<T> right) {
        return new BinaryTreeNode<T>(t, parent, left, right);
    }

    LinkedBinaryTree() {
    }

    protected BinaryTreeNode<T> validate(TreeNode<T> n) throws IllegalArgumentException {
        if (!(n instanceof BinaryTreeNode)) throw new IllegalArgumentException("Not a valid node type");
        var node = (BinaryTreeNode<T>) n;
        if (node.parent == node) throw new IllegalArgumentException("n is no longer in the tree");
        return (BinaryTreeNode<T>) node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public BinaryTreeNode<T> parent(TreeNode<T> n) {
        var node = validate(n);
        return (BinaryTreeNode<T>) node.parent;
    }

    @Override
    public BinaryTreeNode<T> left(TreeNode<T> n) throws IllegalArgumentException {
        var node = validate(n);
        return (BinaryTreeNode<T>) node.left;
    }

    @Override
    public BinaryTreeNode<T> right(TreeNode<T> n) throws IllegalArgumentException {
        var node = validate(n);
        return (BinaryTreeNode<T>) node.right;
    }

    public BinaryTreeNode<T> addRoot(T val) {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = new BinaryTreeNode<T>(val);
        size = 1;
        return root;
    }

    public BinaryTreeNode<T> addLeft(BinaryTreeNode<T> n, T val) {
        var parent = validate(n);
        if (left(parent) != null) throw new IllegalArgumentException("n already has a left child");
        var child = createNode(val, parent, null, null);
        parent.left = child;
        size++;
        return child;
    }

    public BinaryTreeNode<T> addRight(BinaryTreeNode<T> n, T val) {
        var parent = validate(n);
        if (right(parent) != null) throw new IllegalArgumentException("n already has a right child");
        var child = createNode(val, parent, null, null);
        parent.right = child;
        size++;
        return child;
    }

    public T set(BinaryTreeNode<T> n, T val) {
        var node = validate(n);
        T t = node.data;
        node.data = val;
        return t;
    }

    public void attach(BinaryTreeNode<T> n, LinkedBinaryTree<T> left, LinkedBinaryTree<T> right) {
        var node = validate(n);
        if (isInternal(n)) throw new IllegalArgumentException("n must be a leaf");
        size += left.size() + right.size();

        if (!left.isEmpty()) {
            left.root.parent = node;
            node.left = left.root;
            left.root = null;
            left.size = 0;
        }

        if (!right.isEmpty()) {
            right.root.parent = node;
            node.right = right.root;
            right.root = null;
            right.size = 0;
        }
    }

    public T remove(BinaryTreeNode<T> n) {
        var node = validate(n);
        if (numChildren(node) == 2) throw new IllegalArgumentException("n has two children");

        var child = (node.left != null ? node.left : node.right);
        if (child != null) child.parent = node.parent;
        if (node == root) root = (BinaryTreeNode<T>) child;
        else {
            var parent = (BinaryTreeNode<T>) node.parent;
            if (node == parent.left) parent.left = child;
            else parent.right = child;
        }
        size--;

        var val = node.data;
        node.data = null;
        node.left = null;
        node.right = null;
        node.parent = node;
        return val;
    }

    @Override
    public TreeNode<T> root() {
        return root;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

}
