public abstract class AbGeneralTree<T extends Comparable<T>> extends AbTree<T> {
    @Override
    public int numChildren(TreeNode<T> n) throws IllegalArgumentException {
        return ((GeneralTreeNode<T>) n).children.size();
    }

    @Override
    public Iterable<TreeNode<T>> children(TreeNode<T> n) {
        return ((GeneralTreeNode<T>) n).children;
    }
}
