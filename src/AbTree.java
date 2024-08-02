import java.util.ArrayList;
import java.util.List;

public abstract class AbTree<T extends Comparable<T>> implements TreeADT<T> {

    @Override
    public boolean isRoot(TreeNode<T> n) throws IllegalArgumentException {
        return n == root();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    protected void preorderSubtree(TreeNode<T> n, List<TreeNode<T>> snap) {
        snap.add(n);
        for (var c : children(n)) {
            preorderSubtree(c, snap);
        }
    }

    protected void postorderSubtree(TreeNode<T> n, List<TreeNode<T>> snap) {
        for (var c : children(n)) {
            postorderSubtree(c, snap);
        }
        snap.add(n);
    }

    public Iterable<TreeNode<T>> preorder() {
        List<TreeNode<T>> snap = new ArrayList<>();
        if (!isEmpty()) preorderSubtree(root(), snap);
        return snap;
    }

    public Iterable<TreeNode<T>> postorder() {
        List<TreeNode<T>> snap = new ArrayList<>();
        if (!isEmpty()) postorderSubtree(root(), snap);
        return snap;
    }

}
