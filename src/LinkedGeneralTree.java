import java.util.Iterator;

public class LinkedGeneralTree<T extends Comparable<T>> extends AbGeneralTree<T> {
    private GeneralTreeNode<T> root = null;
    private int size = 0;

    protected GeneralTreeNode<T> createNode(T val, TreeNode<T> parent) {
        return new GeneralTreeNode<>(val, parent);
    }

    LinkedGeneralTree() {
    }

    protected GeneralTreeNode<T> validate(TreeNode<T> n) {
        if (!(n instanceof GeneralTreeNode)) throw new IllegalArgumentException("Invalid node");
        if (n.parent == n) throw new IllegalArgumentException("Node is no longer in the tree");
        return (GeneralTreeNode<T>) n;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public TreeNode<T> parent(TreeNode<T> n) throws IllegalArgumentException {
        var node = validate(n);
        return node.parent;
    }

    public GeneralTreeNode<T> addRoot(T val) {
        if (!isEmpty()) throw new IllegalArgumentException("Tree is not empty");
        root = createNode(val, null);
        size = 1;
        return root;
    }

    public GeneralTreeNode<T> addChild(TreeNode<T> n, T val) {
        var parent = (GeneralTreeNode<T>) validate(n);
        var child = createNode(val, parent);
        parent.addChild(child);
        size++;
        return child;
    }

    public T set(TreeNode<T> n, T val) {
        var node = validate(n);
        T t = node.data;
        node.data = val;
        return t;
    }

    public void remove(TreeNode<T> n) {
        var node = (GeneralTreeNode<T>) validate(n);
        var numberOfChildren = numChildren(n);
        var children = children(n);
        if (node == root) {
            throw new UnsupportedOperationException("Cannot remove the root");
        } else {
            for (var child : children) {
                child.parent = child;
            }
            node.children.clear();
            ((GeneralTreeNode<T>) node.parent).children.remove(node);
            node.parent = node;
            size -= numberOfChildren + 1;
        }
    }

    @Override
    public TreeNode<T> root() {
        return root;
    }

}
