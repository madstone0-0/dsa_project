import java.util.ArrayList;

public abstract class TreeNode<T> {
  T data;
  TreeNode<T> parent;

  TreeNode(T data) { this.data = data; }

  TreeNode(T data, TreeNode<T> parent) {
    this.data = data;
    this.parent = parent;
  }

  @Override
  public String toString() {
    return String.format("%s{%s}", this.getClass().getSimpleName(),
                         data.toString());
  }
}
