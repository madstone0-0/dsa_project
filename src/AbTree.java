import java.util.ArrayList;
import java.util.List;

public abstract class AbTree<T> implements TreeADT<T> {
    @Override
    public boolean isInternal(TreeNode<T> n) throws IllegalArgumentException {
        return numChildren(n) > 0;
    }

    @Override
    public boolean isExternal(TreeNode<T> n) throws IllegalArgumentException {
        return numChildren(n) == 0;
    }

    @Override
    public boolean isRoot(TreeNode<T> n) throws IllegalArgumentException {
        return n == root();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void preorderSubtree(TreeNode<T> n, List<TreeNode<T>> snap) {
        snap.add(n);
        for (var c : n.children) {
            preorderSubtree(c, snap);
        }
    }

    private void postorderSubtree(TreeNode<T> n, List<TreeNode<T>> snap) {
        for (var c : n.children) {
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

    public int depth(TreeNode<T> n) {
        if (isRoot(n)) return 0;
        else return depth(parent(n)) + 1;
    }

    public int height(TreeNode<T> n) {
        int h = 0;
        for (var node : children(n)) h = Math.max(h, 1 + height(node));
        return h;
    }
}
