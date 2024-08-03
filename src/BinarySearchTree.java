import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Represents a binary search tree (BST) where each node is a BinaryTreeNode.
 * Provides methods for insertion, searching, and traversing the tree.
 * 
 * @param <T> the type of data stored in the tree nodes, which must be comparable.
 */
class BinarySearchTree<T extends Comparable<T>> extends AbBinaryTree<T> {
    // Root node of the binary search tree
    private BinaryTreeNode<T> root;
    // Number of nodes in the tree
    private int size = 0;

    /**
     * Default constructor initializing an empty tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a new node into the binary search tree.
     * 
     * @param n the node to insert into the tree.
     */
    public void insert(TreeNode<T> n) {
        root = insertRec(root, n);
        size++;
    }

    /**
     * Recursively inserts a new node into the binary search tree.
     * 
     * @param root the current root of the subtree.
     * @param n the node to insert.
     * @return the new root of the subtree.
     */
    private BinaryTreeNode<T> insertRec(BinaryTreeNode<T> root, TreeNode<T> n) {
        if (root == null) {
            // If the current root is null, create a new node
            root = new BinaryTreeNode<>(n.data, n.parent);
            return root;
        }

        // Recursively insert the node into the left or right subtree
        if (n.data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, n);
        } else if (n.data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, n);
        }
        return root;
    }

    /**
     * Collects all nodes in the subtree rooted at the given node using preorder traversal.
     * 
     * @param n the root node of the subtree.
     * @param snap the list to store the nodes.
     */
    protected void preorderSubtree(TreeNode<T> n, ArrayList<TreeNode<T>> snap) {
        if (!(n instanceof GeneralTreeNode<T> node)) throw new IllegalArgumentException("Invalid node");
        snap.add(n);
        for (var c : node.children) {
            preorderSubtree(c, snap);
        }
    }

    /**
     * Constructs a BinarySearchTree from a given node using preorder traversal.
     * 
     * @param node the root node of the subtree to initialize the tree.
     * @throws IllegalArgumentException if the node is null.
     */
    BinarySearchTree(TreeNode<T> node) {
        if (node == null) throw new IllegalArgumentException("Node cannot be null");

        root = new BinaryTreeNode<>(node.data);
        ArrayList<TreeNode<T>> nodes = new ArrayList<>();
        preorderSubtree(node, nodes);
        nodes.remove(0); // Remove the root node from the list
        for (TreeNode<T> n : nodes) {
            insert(n);
        }
    }

    /**
     * Validates whether a given node is a BinaryTreeNode and is part of the tree.
     * 
     * @param n the node to validate.
     * @return the validated BinaryTreeNode.
     * @throws IllegalArgumentException if the node is invalid.
     */
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

    /**
     * Searches for a node with the specified key in the tree.
     * 
     * @param key the key to search for.
     * @return the node with the specified key, or null if not found.
     */
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

    /**
     * Searches for a node with the specified key and returns the path from the node to the specified working directory.
     * 
     * @param key the key to search for.
     * @param wd the working directory to which the path should lead.
     * @return the path from the node to the working directory as a string.
     */
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
