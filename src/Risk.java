import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Risk {

    public static void main(String[] args) {
        Risk r = new Risk();
        r.start();
    }

    private void start() {
        Board board = new Board("Risk");

        Container container= board.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        northPanel.add(new JButton("USELESS_1"));
        northPanel.add(new JButton("USELESS_2"));
        container.add(BorderLayout.NORTH, northPanel);

        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel cursorLocation = new JLabel();
        southPanel.add(cursorLocation);
        southPanel.add(new JButton("USELESS_3"));
        southPanel.add(new JButton("USELESS_4"));
        container.add(BorderLayout.SOUTH, southPanel);

        JPanel playColorPanel = new JPanel();
        playColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        playColorPanel.setLayout(new BoxLayout(playColorPanel, BoxLayout.Y_AXIS));
        JPanel showPlayerColorPanel = new JPanel();
        showPlayerColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        showPlayerColorPanel.setBackground(board.getPlayerColor());
        playColorPanel.add(new JLabel("  Player Color"));
        playColorPanel.add(new colorButton(1, Color.red, "red", board, showPlayerColorPanel));
        playColorPanel.add(new colorButton(1, Color.orange, "orange", board, showPlayerColorPanel));
        playColorPanel.add(new colorButton(1, Color.yellow, "yellow", board, showPlayerColorPanel));
        playColorPanel.add(new colorButton(1, Color.green, "green", board, showPlayerColorPanel));
        playColorPanel.add(new colorButton(1, Color.blue, "blue", board, showPlayerColorPanel));
        playColorPanel.add(new colorButton(1, Color.cyan, "cyan", board, showPlayerColorPanel));
        playColorPanel.add(new colorButton(1, Color.magenta, "magenta", board, showPlayerColorPanel));
        playColorPanel.add(new JLabel("    DIY RGB ?"));
        playColorPanel.add(showPlayerColorPanel);

        JPanel continentColorPanel = new JPanel();
        continentColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        continentColorPanel.setLayout(new BoxLayout(continentColorPanel, BoxLayout.Y_AXIS));
        JPanel showContinentColorPanel = new JPanel();
        showContinentColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        showContinentColorPanel.setBackground(board.getContinentColor());
        continentColorPanel.add(new JLabel("Continent Color "));
        continentColorPanel.add(new colorButton(2, Color.red, "red", board, showContinentColorPanel));
        continentColorPanel.add(new colorButton(2, Color.orange, "orange", board, showContinentColorPanel));
        continentColorPanel.add(new colorButton(2, Color.yellow, "yellow", board, showContinentColorPanel));
        continentColorPanel.add(new colorButton(2, Color.green, "green", board, showContinentColorPanel));
        continentColorPanel.add(new colorButton(2, Color.blue, "blue", board, showContinentColorPanel));
        continentColorPanel.add(new colorButton(2, Color.cyan, "cyan", board, showContinentColorPanel));
        continentColorPanel.add(new colorButton(2, Color.magenta, "magenta", board, showContinentColorPanel));
        continentColorPanel.add(new JLabel("    DIY RGB ?"));
        continentColorPanel.add(showContinentColorPanel);

        JPanel eastPanel = new JPanel();
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
        eastPanel.add(playColorPanel);
        eastPanel.add(continentColorPanel);
        container.add(BorderLayout.EAST, eastPanel);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        westPanel.add(new JLabel("Try it out :D"));
        container.add(BorderLayout.WEST, westPanel);

        Map map = new Map();
        map.setBoard(board);

        container.add(BorderLayout.CENTER, map);
        board.setSouthPanel(cursorLocation);
        board.setVisible(true);
    }
}



//                setFocusable(true);
//                setRequestFocusEnabled(true);
//                requestFocusInWindow();
//                requestFocus();
//                grabFocus();
