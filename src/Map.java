import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;

public class Map extends JLayeredPane {
    private final int COUNTRY_SIZE = 60;
    private final int MAX_FLEX_SIZE = 7;
    private final int MAP_BORDER_THICKNESS = 4;
    private final int LINE_STROKE_WIDTH = 5;

    private Board board;
    private int countryId;
    private int lineId;
    private boolean holdShiftKey = false;
    private Country lineStartCountry;
    private Country lineEndCountry;
    private Line selectedLine;
    private Country selectedCountry;
    private HashMap<Integer, Country> countries = new HashMap<>();
    private HashMap<Integer, Line> lines = new HashMap<>();

    public Map() {
        countryId = 0;
        lineId = 0;
        setLayout(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createCountry(e.getX()-COUNTRY_SIZE/2, e.getY()-COUNTRY_SIZE/2);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                grabFocus();
                lineEndCountry = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                board.setCursorXY(e.getX(), e.getY());
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (16 == e.getKeyCode()) { // shift key
                    holdShiftKey = true;
                } else if (8 == e.getKeyCode()) { // delete key
                    if (null != selectedCountry) {
                        removeCountry(selectedCountry);
                    } else if (null != selectedLine) {
                        removeLine(selectedLine);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (16 == e.getKeyCode()) { // shift key
                    holdShiftKey = false;
                }
            }
        });
    }

    public void createCountry(int locationX, int locationY) {
        Country country = new Country(countryId++, COUNTRY_SIZE, MAX_FLEX_SIZE, MAP_BORDER_THICKNESS,
                board.getPlayerColor(), board.getContinentColor());
        country.setMap(getItself());
        country.setLocation(locationX, locationY);
        add(country, DRAG_LAYER);
        countries.put(country.getId(), country);
        repaint();
    }

    public void connectTwoCountries() {
        if (null != lineStartCountry && null != lineEndCountry &&
                lineStartCountry.getId() != lineEndCountry.getId()) { // avoid self loop
            //TODO check duplicate line
            Line line = new Line(++lineId, lineStartCountry, lineEndCountry, COUNTRY_SIZE, LINE_STROKE_WIDTH);
            line.setMap(getItself());
            lineStartCountry.addLine(line);
            lineEndCountry.addLine(line);
            lineStartCountry = null;
            lineEndCountry = null;
            lines.put(line.getId(), line);
            add(line, DEFAULT_LAYER);
            repaint();
        }
    }

    public void setSelectedLine(Line line) {
        if (null != selectedLine) {
            selectedLine.resetSelected();
        }
        selectedLine = line;
        if (null != selectedCountry) {
            selectedCountry.resetSelected();
            selectedCountry = null;
        }
    }

    public void setSelectedCountry(Country country) {
        if (null != selectedCountry) {
            selectedCountry.resetSelected();
        }
        selectedCountry = country;
        if (null != selectedLine) {
            selectedLine.resetSelected();
            selectedLine = null;
        }
    }

    public void removeCountry(Country country) {
        country.removeLines();
        countries.remove(country);
        remove(country);
        repaint();
    }

    public void removeLine(Line line) {
        line.getCountryA().removeLine(line);
        line.getCountryB().removeLine(line);
        remove(line);
        lines.remove(line.getId());
        repaint();
    }

    public void resetSelected() {
        selectedCountry = null;
        selectedLine = null;
    }

    public void findLine(int cursorX, int cursorY) {
        // TODO: also pass the select wrong line id here, no need to check it again
        boolean found = false;

        for (int id : lines.keySet()) {
            Line line = lines.get(id);
            if (line.calculateClickPosition(cursorX-line.getX(), cursorY-line.getY())) {
                line.select();
                found = true;
                break;
            }
        }

        if (!found) {
            createCountry(cursorX - COUNTRY_SIZE/2, cursorY - COUNTRY_SIZE/2);
        }
    }

    public void setBoard(Board newBoard) {
        board = newBoard;
    }

    public void setHoldingShiftKey(boolean hold) { holdShiftKey = hold; }

    public void setLineStartCountry(Country c) { lineStartCountry = c; }

    public void setLineEndCountry(Country c) { lineEndCountry = c; }

    public boolean checkHoldingShiftKey() {return holdShiftKey;}

    private Map getItself() { return this; }
}