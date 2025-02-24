package haubold.resources.util;
import javax.swing.filechooser.*;
import java.io.File;

/**
 * Description: File filter. Adapted from John Zukowski (2000). <it>Definitive Guide
 * to Swing for Java 2. Apress, p. 358f.</it>
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 15, 2004; time: 8:34:21 AM.
 */
public class ExtensionFileFilter extends FileFilter {
  String description;
  String extensions[];

  public ExtensionFileFilter(String description, String extension) {
    this(description, new String[] { extension });
  }

  public ExtensionFileFilter(String description, String extensions[]) {
    if (description == null) {
      // Since there is no description, use the first extension and # of extensions as description
      this.description = extensions[0] + "{" + extensions.length + "}";
    } else {
      this.description = description;
    }
    // Convert array to lower case but do not alter original entries
    this.extensions = (String[]) extensions.clone();
    toLower(this.extensions);
  }

  private void toLower(String array[]) {
    for (int i = 0, n = array.length; i < n; i++) {
      array[i] = array[i].toLowerCase();
    }
  }

  public String getDescription() {
    return description;
  }

  // Ignore case and always accept directories. The character before the extension
  // must me a period.
  public boolean accept(File file) {
    if (file.isDirectory()) {
      return true;
    } else {
      String path = file.getAbsolutePath().toLowerCase();
      for (int i = 0, n = extensions.length; i < n; i++) {
        String extension = extensions[i];
        if (path.endsWith(extension)
            && (path.charAt(path.length() - extension.length() - 1))
            == '.') {
          return true;
        }
      }
    }
    return false;
  }
}