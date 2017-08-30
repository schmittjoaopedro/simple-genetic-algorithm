package natural.algorithm.tsp;

public class City {

    private int x;
    private int y;

    // Constructs a randomly placed city
    public City() {
        this.x = (int) (Math.random() * 200);
        this.y = (int) (Math.random() * 200);
    }

    // Constructs a city at chosen x, y location
    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Gets city's x coordinate
    public int getX() {
        return this.x;
    }

    // Gets city's y coordinate
    public int getY() {
        return this.y;
    }

    // Gets the distance to given city
    public double distanceTo(City city) {
        return Math.sqrt(Math.pow(city.getX() - this.getX(), 2.0) + Math.pow(city.getY() - this.getY(), 2.0));
    }

    @Override
    public String toString() {
        return getX() + ", " + getY();
    }

}
