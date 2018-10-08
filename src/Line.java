import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Line extends JPanel {
    private int id;
    private int width;
    private int height;
    private int locationX;
    private int locationY;
    private int countrySize;
    private int strokeWidth;
    private int drawStartX;
    private int drawStartY;
    private int drawEndX;
    private int drawEndY;
    private boolean selected;
    private Map map;
    private Country countryA;
    private Country countryB;
    private Color color;

    public Line(int newId, Country newCountryA, Country newCountryB, int newCountrySize, int newStrokeWidth) {
        id = newId;
        countryA = newCountryA;
        countryB = newCountryB;
        color = Color.black;
        countrySize = newCountrySize;
        strokeWidth = newStrokeWidth;
        selected = false;


        calculatePosition();
        setOpaque(false);
//        setBackground(Color.yellow);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (calculateClickPosition(e.getX(), e.getY())) {
                    select();
                } else {
                    map.findLine(e.getX()+locationX, e.getY() + locationY);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                grabFocus();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (8 == e.getKeyCode()) { // delete key
                    if (selected) map.removeLine(getItself());
                }
            }
        });
    }

    public void calculatePosition() {
        width = abs(countryA.getX() - countryB.getX());
        height = abs(countryA.getY() - countryB.getY());
        int minX = min(countryA.getX(), countryB.getX());
        int minY = min(countryA.getY(), countryB.getY());
        drawStartX = countryA.getX() - minX;
        drawStartY = countryA.getY() - minY;
        drawEndX = countryB.getX() - minX;
        drawEndY = countryB.getY() - minY;
        locationX = minX + countrySize/2;
        locationY = minY + countrySize/2;
        setSize(width, height);
        setLocation(locationX, locationY);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(strokeWidth));
        g2.draw(new Line2D.Float(drawStartX, drawStartY, drawEndX, drawEndY));
    }

    public boolean calculateClickPosition(int cursorX, int cursorY) {
        // line function: y = kx + b
        double k = (drawEndY - drawStartY) * 1.0 / (drawEndX - drawStartX);
        double b = drawStartY - k * drawStartX;
        double diff = cursorY - k * cursorX;
        return abs(b - diff) < 5;
    }

    public void select() {
        grabFocus();
        if (!selected) {
            selected = true;
            map.setSelectedLine(getItself());
            highLight();
        } else {
            selected = false;
            map.resetSelected();
            resetColor();
        }
    }

    public void updatePosition() {
        calculatePosition();
        repaint();
    }

    public void highLight() {
        color = Color.cyan;
        repaint();
    }

    public void resetColor() {
        color = Color.black;
        repaint();
    }

    public void resetSelected() {
        selected = false;
        resetColor();
    }

    public void setMap(Map newMap) { map = newMap; }

    public int getId() { return id; }

    public Line getItself() { return this; }

    public Country getCountryA() {
        return countryA;
    }

    public Country getCountryB(){
        return countryB;
    }
}
