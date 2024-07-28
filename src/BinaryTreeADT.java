public interface BinaryTreeADT<T> extends TreeADT<T> {
  BinaryTreeNode<T> left(TreeNode<T> n) throws IllegalArgumentException;
  BinaryTreeNode<T> right(TreeNode<T> n) throws IllegalArgumentException;
  BinaryTreeNode<T> sibling(TreeNode<T> n) throws IllegalArgumentException;
}
