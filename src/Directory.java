import java.time.LocalDateTime;

/**
 * Represents a directory in a file system.
 * Inherits from the FileSystem class and provides functionality specific to directories.
 */
public class Directory extends FileSystem {

    /**
     * Constructs a Directory with the specified name.
     * 
     * @param name the name of the directory.
     */
    public Directory(String name) {
        super(name); // Initialize with the name provided
    }

    /**
     * Indicates that this instance is a directory.
     * 
     * @return true, as this instance represents a directory.
     */
    @Override
    public boolean isDirectory() {
        return true; // Directories should return true
    }

    /**
     * Updates the size of the directory and sets the last modified date.
     * 
     * @param change the amount by which the directory size should be changed. 
     *               This could be positive (for adding files) or negative (for removing files).
     */
    private void updateSize(long change) {
        size += change; // Update the size of the directory
        dateModified = LocalDateTime.now(); // Update the last modified date to the current time
    }

    /**
     * Provides a string representation of the directory.
     * 
     * @return the string representation of the directory, ending with a slash.
     */
    @Override
    public String toString() {
        return String.format("%s/", name); // Append a slash to indicate it's a directory
    }
}
