import java.util.ArrayList;

public class GeneralTreeNode<T> extends TreeNode<T> {
  ArrayList<TreeNode<T>> children = new ArrayList<>();
  GeneralTreeNode(T data) { super(data); }

  GeneralTreeNode(T data, TreeNode<T> parent) { super(data, parent); }

  public void addChild(GeneralTreeNode<T> child) {
    child.parent = this;
    children.add(child);
  }
}
