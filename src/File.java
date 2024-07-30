public class File extends FileSystem {
    private String extension;

    public File(String name, String extension, long size) {
        super(name);
        this.extension = extension;
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public String rename(String newName) {
        var newNameArr = newName.split("\\.");
        String oldName = name;
        if (newNameArr.length != 2) {
            name = newName;
        } else {
            name = newNameArr[0];
            var oldExtension = extension;
            extension = newNameArr[1];
            oldName = String.format("%s.%s", oldName, oldExtension);
        }
        return oldName;
    }

    @Override
    public String toString() {
        return String.format("%s.%s", name, extension);
    }
}
