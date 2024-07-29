public class File implements FileSystem {
    private String name;
    private FileSystem parent;

    public File(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public FileSystem getParent() {
        return parent;
    }

    @Override
    public void setParent(FileSystem parent) {
        this.parent = parent;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
