package TrafficSim;

import TrafficSim.Car;
import TrafficLight.TrafficLight;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;


public class IntersectionPanel extends JPanel {
public static final int MIN_NUM_CARS = 3;

    private static final int INTERSECTION_X = 200;
    private static final int INTERSECTION_Y = 200;
    private static final int INTERSECTION_WIDTH = 400;
    private static final int INTERSECTION_HEIGHT = 400;

    private static final int CAR_WIDTH = 20;
    private static final int CAR_HEIGHT = 40;
    private static final int CAR_SPEED = 2;
    
    private final List<Car> northSouthCars;
    private final List<Car> eastWestCars;
    List<Car> northSouthCars = new ArrayList<>();
    private int carCount = 0;

    public IntersectionPanel() {
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.WHITE);

        northSouthCars = new ArrayList<>();
        eastWestCars = new ArrayList<>();

        Timer timer = new Timer(10, e -> {
            addCar();
            moveCars(northSouthCars);
            moveCars(eastWestCars);
            repaint();
        });
        timer.start();
    }

    public void addCar(int carId) {
        Random random = new Random();
        int direction = random.nextInt(4) + 1;
        switch (direction) {
            case 1:
                northSouthCars.add(new Car(carCount, INTERSECTION_WIDTH / 2 - CAR_WIDTH / 2, -CAR_HEIGHT, TrafficSim.CarDirection.NORTH));
                break;
            case 2:
                northSouthCars.add(new Car(carCount, INTERSECTION_WIDTH / 2 - CAR_WIDTH / 2, INTERSECTION_HEIGHT, TrafficSim.CarDirection.SOUTH));
                break;
            case 3:
                eastWestCars.add(new Car(carCount, -CAR_WIDTH, INTERSECTION_HEIGHT / 2 - CAR_HEIGHT / 2, TrafficSim.CarDirection.WEST));
                break;
            case 4:
                eastWestCars.add(new Car(carCount, INTERSECTION_WIDTH, INTERSECTION_HEIGHT / 2 - CAR_HEIGHT / 2, CarDirection.EAST));
                break;
        }
        carCount++;
    }
    
    public int getNumberOfCars() {
        return northSouthCars.size() + eastWestCars.size();
    }
    
    public List<Car> getNorthSouthCars() {
        return northSouthCars;
    }
    
    public List<Car> getEastWestCars() {
        return eastWestCars;
    }

    public void removeCar() {
        if (northSouthCars.size() > 0) {
            Car carToRemove = northSouthCars.get(northSouthCars.size() - 1);
            carToRemove.stop();
            northSouthCars.remove(carToRemove);
        } else if (eastWestCars.size() > 0) {
            Car carToRemove = eastWestCars.get(eastWestCars.size() - 1);
            carToRemove.stop();
            eastWestCars.remove(carToRemove);
        }
    }

  
    private void moveCars(List<Car> carList) {
        for (Car car : carList) {
            switch (car.getDirection()) {
                case NORTH:
                    car.setY(car.getY() + CAR_SPEED);
                    break;
                case SOUTH:
                    car.setY(car.getY() - CAR_SPEED);
                    break;
                case EAST:
                    car.setX(car.getX() + CAR_SPEED);
                    break;
                case WEST:
                    car.setX(car.getX() - CAR_SPEED);
                    break;
            }
        }
        carList.removeIf(car -> !car.isIntheintersection());
          int numNorthSouthCars = getNorthSouthCars().size();
    int numEastWestCars = getEastWestCars().size();

    // check for collisions between cars
    for (Car car : carList) {
        for (Car otherCar : carList) {
            if (car != otherCar && car.getBounds().intersects(otherCar.getBounds())) {
                car.stop();
                otherCar.stop();
                return;
            }
        }
    }

    // remove cars that have left the intersection
    carList.removeIf(car -> !car.isInIntersection());

    // add new cars to maintain a minimum number of cars
    int numCarsToAdd = MIN_NUM_CARS - (numNorthSouthCars + numEastWestCars);
    for (int i = 0; i < numCarsToAdd; i++) {
        addCar(getNumberOfCars());
    }
}

} 