import java.util.Iterator;

public interface TreeADT<T> extends Iterable<T> {
    TreeNode<T> root();

    TreeNode<T> parent(TreeNode<T> n) throws IllegalArgumentException;

    Iterable<TreeNode<T>> children(TreeNode<T> n);

    int numChildren(TreeNode<T> n) throws IllegalArgumentException;

    boolean isInternal(TreeNode<T> n) throws IllegalArgumentException;

    boolean isExternal(TreeNode<T> n) throws IllegalArgumentException;

    boolean isRoot(TreeNode<T> n) throws IllegalArgumentException;

    int size();

    boolean isEmpty();

    Iterator<T> iterator();
//    Iterable<TreeNode<T>> nodes();
}
