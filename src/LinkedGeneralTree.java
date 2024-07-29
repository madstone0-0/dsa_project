import java.util.Iterator;

public class LinkedGeneralTree<T> extends AbGeneralTree<T> {
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
    public Iterator<T> iterator() {
        return null;
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

    public void generateTreeDisplay(TreeNode<T> n, StringBuilder sb, String prefix, String childrenPrefix) {
        var node = validate(n);
        sb.append(prefix);
        sb.append(node.data);
        sb.append('\n');

        for (int i = 0; i < node.children.size(); i++) {
            TreeNode<T> child = node.children.get(i);
            if (i < node.children.size() - 1) {
                generateTreeDisplay(child, sb, childrenPrefix + "├── ", childrenPrefix + "│   ");

            } else {
                generateTreeDisplay(child, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

    public String generateTreeDisplay(GeneralTreeNode<T> root) {
        StringBuilder sb = new StringBuilder();
        generateTreeDisplay(root, sb, "", "");
        return sb.toString();
    }

    @Override
    public String toString() {
        return generateTreeDisplay(root);
    }
    
}
