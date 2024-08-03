/**
 * Represents a file in a file system.
 * A file has a name, an extension, and a size. It is not a directory.
 */
public class File extends FileSystem {
    // The extension of the file (e.g., "txt", "jpg")
    private String extension;

    /**
     * Constructs a File with the specified name, extension, and size.
     * 
     * @param name The name of the file.
     * @param extension The file's extension (e.g., "txt").
     * @param size The size of the file in bytes.
     */
    public File(String name, String extension, long size) {
        super(name);
        this.extension = extension;
        this.size = size;
    }

    /**
     * Returns the extension of the file.
     * 
     * @return The file's extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Indicates that this instance is not a directory.
     * 
     * @return false, as this is a file and not a directory.
     */
    @Override
    public boolean isDirectory() {
        return false;
    }

    /**
     * Renames the file, potentially changing its extension.
     * 
     * @param newName The new name for the file, which may include an extension.
     * @return The old name of the file, including its previous extension if changed.
     */
    @Override
    public String rename(String newName) {
        var newNameArr = newName.split("\\.");
        String oldName = name;
        
        // Check if the new name includes an extension
        if (newNameArr.length != 2) {
            // No extension in the new name
            name = newName;
        } else {
            // Split name and extension
            name = newNameArr[0];
            var oldExtension = extension;
            extension = newNameArr[1];
            oldName = String.format("%s.%s", oldName, oldExtension);
        }
        
        return oldName;
    }

    /**
     * Returns a string representation of the file, including its name and extension.
     * 
     * @return A string representing the file, formatted as "name.extension".
     */
    @Override
    public String toString() {
        return String.format("%s.%s", name, extension);
    }
}
