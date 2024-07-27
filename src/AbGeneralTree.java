public abstract class AbGeneralTree<T> extends AbTree<T> implements TreeADT<T> {
    @Override
    public int numChildren(TreeNode<T> n) throws IllegalArgumentException {
        return n.children.size();
    }

    @Override
    public Iterable<TreeNode<T>> children(TreeNode<T> n) {
        return n.children;
    }
}
