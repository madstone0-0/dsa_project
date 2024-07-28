import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedGeneralTree<T> extends AbGeneralTree<T> {
  private TreeNode<T> root = null;
  private int size = 0;

  protected GeneralTreeNode<T> createNode(T val, TreeNode<T> parent) {
    return new GeneralTreeNode<>(val, parent);
  }

  LinkedGeneralTree() {}

  protected TreeNode<T> validate(TreeNode<T> n) {
    if (!(n instanceof GeneralTreeNode))
      throw new IllegalArgumentException("Invalid node");
    if (n.parent == n)
      throw new IllegalArgumentException("Node is no longer in the tree");
    return n;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return null;
  }

  @Override
  public TreeNode<T> parent(TreeNode<T> n) throws IllegalArgumentException {
    var node = validate(n);
    return node.parent;
  }

  public TreeNode<T> addRoot(T val) {
    if (!isEmpty())
      throw new IllegalArgumentException("Tree is not empty");
    root = createNode(val, null);
    size = 1;
    return root;
  }

  public TreeNode<T> addChild(TreeNode<T> n, T val) {
    var parent = validate(n);
    var child = createNode(val, parent);
    parent.addChild(child);
    size++;
    return child;
  }

  public T set(TreeNode<T> n, T val) {
    var node = validate(n);
    T t = node.data;
    node.data = val;
    return t;
  }

  public void remove(TreeNode<T> n) {
    var node = validate(n);
    var numberOfChildren = numChildren(n);
    var children = children(n);
    if (node == root) {
      throw new UnsupportedOperationException("Cannot remove the root");
    } else {
      for (var child : children) {
        child.parent = child;
      }
      node.children.clear();
      node.parent.children.remove(node);
      node.parent = node;
      size -= numberOfChildren + 1;
    }
  }

  @Override
  public TreeNode<T> root() {
    return root;
  }

  public static <T> void generateTreeDisplay(TreeNode<T> node, StringBuilder sb,
                                             String prefix,
                                             String childrenPrefix) {
    sb.append(prefix);
    sb.append(node.data);
    sb.append('\n');

    for (int i = 0; i < node.children.size(); i++) {
      TreeNode<T> child = node.children.get(i);
      if (i < node.children.size() - 1) {
        generateTreeDisplay(child, sb, childrenPrefix + "├── ",
                            childrenPrefix + "│   ");
      } else {
        generateTreeDisplay(child, sb, childrenPrefix + "└── ",
                            childrenPrefix + "    ");
      }
    }
  }

  public static <T> String generateTreeDisplay(TreeNode<T> root) {
    StringBuilder sb = new StringBuilder();
    generateTreeDisplay(root, sb, "", "");
    return sb.toString();
  }

  @Override
  public String toString() {
    return generateTreeDisplay(root);
  }

  public static void main(String[] args) {
    LinkedGeneralTree<Integer> tree = new LinkedGeneralTree<>();
    var root = tree.addRoot(10);
    for (int i = 1; i < 10; i++) {
      var child = tree.addChild(root, i);
      for (int j = 1; j < 3; j++) {
        tree.addChild(child, j * 2);
      }
    }
    tree.remove(root.children.get(1));
    System.out.println(tree.height(root));
    //        System.out.println(tree);
    System.out.println(tree);

    var dirTree = new LinkedGeneralTree<String>();
    var rootDir = dirTree.addRoot("root");
    var homeDir = dirTree.addChild(rootDir, "home");
    var userDir = dirTree.addChild(homeDir, "user");
    var file1 = dirTree.addChild(userDir, "file1");
    var file2 = dirTree.addChild(userDir, "file2");
    var file3 = dirTree.addChild(userDir, "file3");
    var file4 = dirTree.addChild(userDir, "file4");
    var file5 = dirTree.addChild(userDir, "file5");
    for (int i = 0; i < 5; i++) {
      dirTree.addChild(file1, "file1-" + i);
    }

    System.out.println(dirTree);
  }
}
