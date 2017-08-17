package genetic.algorithm.annealing;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class PlotRoute extends JPanel {

    private Tour tour;
    private List<Object> shapes = new ArrayList<>();

    public PlotRoute() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 700));
    }

    public void drawTour(Tour tour) {
        this.tour = tour;
        shapes.clear();
        for(int i = 0; i < tour.tourSize(); i++) {
            shapes.add(new Circle(tour.getCity(i).getX(), tour.getCity(i).getY()));
        }
        for (int i = 0; i < tour.tourSize(); i++) {
            City from = tour.getCity(i);
            City dest;
            if (i + 1 < tour.tourSize()) {
                dest = tour.getCity(i + 1);
            } else {
                dest = tour.getCity(0);
            }
            shapes.add(new Line(from.getX(), from.getY(), dest.getX(), dest.getY()));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (Object s : shapes) {
            if (s instanceof Circle) {
                ((Circle) s).draw(graphics);
            } else if (s instanceof Line) {
                ((Line) s).draw(graphics);
            }
        }
    }

    class Circle {
        int x, y, width, height;
        public Circle(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 10, 10);
            g2d.setColor(Color.GRAY);
            g2d.fill(circle);
        }
    }

    class Line {

        int x1, y1, x2, y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Line2D line = new Line2D.Float(this.x1 + 5, this.y1 + 5, this.x2 + 5, this.y2 + 5);
            g2d.draw(line);
        }

    }
}
