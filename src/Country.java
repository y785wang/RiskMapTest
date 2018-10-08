import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Country extends JPanel {
    private int id;
    private int correctX;
    private int correctY;
    private int borderThickness;
    private int maxHighlightSize;
    private int highlightSize;
    private boolean selected;
    private Color playerColor;
    private Color continentColor;
    private Map map;
    private ArrayList<Line> adjacencyLines = new ArrayList<>();

    public Country(int newId, int newSize, int newMaxHighlightSize, int newBorderThickness, Color pc, Color cc) {
        id = newId;
        maxHighlightSize = newMaxHighlightSize;
        highlightSize = maxHighlightSize;
        borderThickness = newBorderThickness;
        playerColor = pc;
        continentColor = cc;
        correctX = 0;
        correctY = 0;
        selected = false;
        setSize(newSize, newSize);
//        setOpaque(false);

        // TODO: try something new
        setBackground(Color.red);
        setBorder(BorderFactory.createLineBorder(Color.black));

        setSize(100, 100);
        setLayout(new GridBagLayout());
        GridBagConstraints c;

        JButton b1 = new JButton("Montreal_1");
//        b1.setPreferredSize(new Dimension(79, 19));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        add(b1, c);

        JButton b2 = new JButton("X");
//        b2.setPreferredSize(new Dimension(19, 19));
        b2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                map.removeCountry(getItself());
            }
        });
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weighty = 1.0;
        add(b2, c);

        JButton b3 = new JButton("Long-Named Button 4");
        b3.setPreferredSize(new Dimension(98, 19));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
//        add(b3, c);

        JButton b4 = new JButton("5");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
//        add(b4, c);









        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                correctX = e.getX();
                correctY = e.getY();
                map.setLineStartCountry(getItself());
                grabFocus();

                if (!selected) {
                    if (! map.checkHoldingShiftKey()) {
                        selected = true;
                        map.setSelectedCountry(getItself());
                    }
                    zoomIn();
                } else {
                    selected = false;
                    map.resetSelected();
                    zoomOut();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                correctX = e.getX();
                correctY = e.getY();
                if (map.checkHoldingShiftKey()) map.connectTwoCountries();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                grabFocus();
//                zoomIn();
                if (map.checkHoldingShiftKey()) {
                    map.setLineEndCountry(getItself());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                if (!selected) zoomOut();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (!map.checkHoldingShiftKey()) {
                    e.translatePoint(e.getComponent().getLocation().x, e.getComponent().getLocation().y);
                    setLocation(e.getX() - correctX, e.getY() - correctY);
                    for (Line line : adjacencyLines) {
                        if (null != line) line.updatePosition();
                    }
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (16 == e.getKeyCode()) { // shift key
                    map.setHoldingShiftKey(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (8 == e.getKeyCode()) { // delete key
                    if (selected) map.removeCountry(getItself());
                } else if (16 == e.getKeyCode()) { // shift key
                    map.setHoldingShiftKey(false);
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        int width = getWidth() - 2*highlightSize;
//        int height = getHeight() - 2*highlightSize;
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(continentColor);
//        g2.fillOval(highlightSize, highlightSize, width, height);
//        g2.setColor(playerColor);
//        g2.fillOval(highlightSize + borderThickness, highlightSize + borderThickness, width - 2*borderThickness, height - 2*borderThickness);
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(Color.black);
//        g2.drawString("C_" + id, getWidth()/2-13,getHeight()/2+4);
    }

    public void zoomIn() {
        highlightSize = 0;
        repaint();
    }

    public void zoomOut() {
        highlightSize = maxHighlightSize;
        repaint();
    }

    public void removeLine(Line line) {
        adjacencyLines.set(adjacencyLines.indexOf(line), null);
    }

    public void removeLines() {
        for (Line line : adjacencyLines) {
            if (null != line) map.removeLine(line);
        }
        adjacencyLines.clear();
    }

    public void resetSelected() {
        selected = false;
        zoomOut();
    }

    public void addLine(Line line) { adjacencyLines.add(line); }

    public void setMap(Map newMap) { map = newMap; }

    public int getId() { return id; }

    public Country getItself() { return this; }
}