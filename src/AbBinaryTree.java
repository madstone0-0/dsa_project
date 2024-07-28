import java.util.ArrayList;

public abstract class AbBinaryTree<T>
    extends AbTree<T> implements BinaryTreeADT<T> {
  @Override
  public BinaryTreeNode<T> sibling(TreeNode<T> n)
      throws IllegalArgumentException {
    var parent = parent(n);
    if (parent == null)
      return null;

    if (n == left(parent))
      return right(parent);
    else
      return left(parent);
  }

  @Override
  public int numChildren(TreeNode<T> n) throws IllegalArgumentException {
    int count = 0;
    if (left(n) != null)
      count++;
    if (right(n) != null)
      count++;
    return count;
  }

  @Override
  public Iterable<TreeNode<T>> children(TreeNode<T> n) {
    var buffer = new ArrayList<TreeNode<T>>(2);
    if (left(n) != null)
      buffer.add(left(n));
    if (right(n) != null)
      buffer.add(right(n));
    return buffer;
  }
}
