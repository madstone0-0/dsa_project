public class File extends FileSystem {
    private String extension;

    public File(String name, String extension, long size) {
        super(name);
        this.extension = extension;
        this.size = size;
    }

    //Getter for file extension
    public String getExtension() {
        return extension;
    }

    //Indicates that this is not a directory
    @Override
    public boolean isDirectory() {
        return false;
    }

    //Renames the file, potentially changing its extension
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

    public String getFullName() {
        return name + "." + extension;
    }

    //String representation of the file
    @Override
    public String toString() {
        return String.format("%s.%s", name, extension);
    }
}
