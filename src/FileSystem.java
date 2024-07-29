public interface FileSystem {
  String getName();
  void rename(String newName);
  FileSystem getParent();
  void setParent(FileSystem parent);
  boolean isDirectory();
}
