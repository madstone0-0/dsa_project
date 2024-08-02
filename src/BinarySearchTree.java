import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class BinarySearchTree<T extends Comparable<T>> extends AbBinaryTree<T> {
    private BinaryTreeNode<T> root;
    private int size = 0;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(TreeNode<T> n) {
        root = insertRec(root, n);
        size++;
    }

    private BinaryTreeNode<T> insertRec(BinaryTreeNode<T> root, TreeNode<T> n) {
        if (root == null) {
            root = new BinaryTreeNode<>(n.data, n.parent);
            return root;
        }

        if (n.data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, n);
        } else if (n.data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, n);
        }
        return root;
    }

    protected void preorderSubtree(TreeNode<T> n, ArrayList<TreeNode<T>> snap) {
        if (!(n instanceof GeneralTreeNode<T> node)) throw new IllegalArgumentException("Invalid node");
        snap.add(n);
        for (var c : node.children) {
            preorderSubtree(c, snap);
        }
    }

    BinarySearchTree(TreeNode<T> node) {
        if (node == null) throw new IllegalArgumentException("Node cannot be null");

        root = new BinaryTreeNode<>(node.data);
        ArrayList<TreeNode<T>> nodes = new ArrayList<>();
        preorderSubtree(node, nodes);
        nodes.removeFirst();
        for (TreeNode<T> n : nodes) {
            insert(n);
        }

    }

    protected BinaryTreeNode<T> validate(TreeNode<T> n) {
        if (!(n instanceof BinaryTreeNode<T>)) throw new IllegalArgumentException("Invalid node");
        if (n.parent == n) throw new IllegalArgumentException("Node is no longer in the tree");
        return (BinaryTreeNode<T>) n;
    }

    @Override
    public TreeNode<T> root() {
        return root;
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

    private BinaryTreeNode<T> search(T key) {
        var temp = root;
        while (temp != null) {
            if (key.compareTo(temp.data) < 0) {
                temp = temp.left;
            } else if (key.compareTo(temp.data) > 0) {
                temp = temp.right;
            } else return temp;
        }

        return null;
    }

    String searchResult(T key, TreeNode<T> wd) {
        var res = search(key);
        if (res == null) {
            return "Item not found";
        } else {
            ArrayList<TreeNode<T>> path = new ArrayList<>();
            TreeNode<T> parent = res;
            while (parent != wd) {
                path.add(parent);
                parent = parent.parent;
            }
            path.add(wd);
            StringBuilder pathString = new StringBuilder();
            for (int i = path.size() - 1; i >= 0; i--) {
                pathString.append(path.get(i).data);
            }
            return pathString.toString();
        }
    }

    @Override
    public BinaryTreeNode<T> left(TreeNode<T> n) throws IllegalArgumentException {
        var node = validate(n);
        return node.left;
    }

    @Override
    public BinaryTreeNode<T> right(TreeNode<T> n) throws IllegalArgumentException {
        var node = validate(n);
        return node.right;
    }

}