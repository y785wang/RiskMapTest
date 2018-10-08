import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class colorButton extends JButton {
    private Color color;
    private Board board;
    private JPanel colorPanel;

    public colorButton(int type, Color newColor, String text, Board newBoard, JPanel newColorPanel) {
        color = newColor;
        board = newBoard;
        colorPanel = newColorPanel;
        setText(text);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (1 == type) {
                    board.setPlayerColor(color);
                } else {
                    board.setContinentColor(color);
                }
                colorPanel.setBackground(color);
                colorPanel.repaint();
            }
        });

    }
}