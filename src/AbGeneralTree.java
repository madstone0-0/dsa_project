public abstract class AbGeneralTree<T extends Comparable<T>> extends AbTree<T> {
    @Override
    public int numChildren(TreeNode<T> n) throws IllegalArgumentException {
        return ((GeneralTreeNode<T>) n).children.size();
    }

    @Override
    public Iterable<TreeNode<T>> children(TreeNode<T> n) {
        return ((GeneralTreeNode<T>) n).children;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;

        return object == this;
    }
}
