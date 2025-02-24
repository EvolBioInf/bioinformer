package haubold.dp;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class DpmCellRenderer implements TableCellRenderer {
  DpmCell[][] dpmMatrix;
  DpmCellRenderer(DpmCell[][] dpmM) {
    dpmMatrix = dpmM;
  }
  public static final DefaultTableCellRenderer DEFAULT_RENDERER =
    new DefaultTableCellRenderer();
  public Component getTableCellRendererComponent(
    JTable table,
    Object value,
    boolean isSelected,
    boolean hasFocus,
    int row,
    int column) {
    Component renderer =
      DEFAULT_RENDERER.getTableCellRendererComponent(
        table,
        value,
        isSelected,
        hasFocus,
        row,
        column);
    Color background, foreground;
    ((JLabel) renderer).setHorizontalAlignment(SwingConstants.TRAILING);
    Font font = new Font("CourierNew", Font.PLAIN, 10);
    ((JLabel) renderer).setFont(font);
    if (row > 0 && column > 0) {
      if (dpmMatrix[row - 1][column - 1].optimal) {
        background = Color.green;
        foreground = Color.black;
      } else if (dpmMatrix[row - 1][column - 1].numCooptimal > 0.0) {
        background = new Color(0.5f, 1.0f, 0.5f, 0.5f);
        foreground = Color.black;
      } else {
        background = Color.blue;
        foreground = Color.yellow;
      }
    } else {
      background = Color.yellow;
      foreground = Color.blue;
    }
    renderer.setForeground(foreground);
    renderer.setBackground(background);
    return renderer;
  }
}
