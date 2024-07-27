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

    public int depth(TreeNode<T> n) {
        if (isRoot(n))
            return 0;
        else
            return depth(parent(n)) + 1;
    }

    public int height(TreeNode<T> n) {
        int h = 0;
        for (var node : children(n)) h = Math.max(h, 1 + height(node));
        return h;
    }
}
