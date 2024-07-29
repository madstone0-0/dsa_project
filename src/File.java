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
}
