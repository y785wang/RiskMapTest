import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

class Board extends JFrame {
    private Color playerColor ;
    private Color continentColor;
    private JLabel cursorLocation;

    public Board(String title) {
        playerColor = Color.red;
        continentColor = Color.blue;
        setTitle(title);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
    }

    public void setCursorXY(int x, int y) {
        cursorLocation.setText("X: " + x + ", Y : " + y);
        cursorLocation.repaint();
    }

    public void setSouthPanel(JLabel cursorLocationLabel) { cursorLocation = cursorLocationLabel; }

    public void setContinentColor(Color newContinentColor) { continentColor = newContinentColor; }

    public void setPlayerColor(Color newPlayColor) { playerColor = newPlayColor; }

    public Color getPlayerColor() { return playerColor; }

    public Color getContinentColor() { return continentColor; }

}