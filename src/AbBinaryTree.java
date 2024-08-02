import java.util.ArrayList;
import java.util.List;

public abstract class AbBinaryTree<T extends Comparable<T>> extends AbTree<T> implements BinaryTreeADT<T> {

    @Override
    public int numChildren(TreeNode<T> n) throws IllegalArgumentException {
        int count = 0;
        if (left(n) != null) count++;
        if (right(n) != null) count++;
        return count;
    }

    private void inorderSubtree(TreeNode<T> n, List<TreeNode<T>> snap) {
        if (left(n) != null) inorderSubtree(left(n), snap);
        snap.add(n);
        if (right(n) != null) inorderSubtree(right(n), snap);
    }

    public Iterable<TreeNode<T>> inorder() {
        var snap = new ArrayList<TreeNode<T>>();
        if (!isEmpty()) {
            inorderSubtree(root(), snap);
        }
        return snap;
    }

    @Override
    public Iterable<TreeNode<T>> children(TreeNode<T> n) {
        var buffer = new ArrayList<TreeNode<T>>(2);
        if (left(n) != null) buffer.add(left(n));
        if (right(n) != null) buffer.add(right(n));
        return buffer;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;

        return object == this;
    }

}
