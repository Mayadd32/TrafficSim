package TrafficSim;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
public class CarPanel extends JPanel implements Runnable {
    private final List<Car> carList1;
    private final List<Car> carList2;
    private final List<Car> carList3;
    private static final int LANE_HEIGHT = 120;
    private static final int LANE_WIDTH = 600;
    private static final int CAR_WIDTH = 40;
    private static final int CAR_HEIGHT = 20;
    private static final int CAR_SPEED = 5;
    private volatile boolean running;
    private volatile boolean paused;
    private volatile boolean carAdded;
    public CarPanel() {
        carList1 = new ArrayList<>();
        carList2 = new ArrayList<>();
        carList3 = new ArrayList<>();
        running = false;
        paused = false;
        carAdded = false;
        setPreferredSize(new Dimension(LANE_WIDTH, LANE_HEIGHT));
    }
    public void start() {running = true;}
    public void pause() {paused = true;}
    public void stop() {running = false;}
    public void continuePanel() {running = true;}
    public void addCar() {carAdded = true;}
    public void removeCar() {
        Random random = new Random();
        int lane = random.nextInt(3) + 1;
        List<Car> carList;
        switch (lane) {
            case 1:
                carList = carList1;
                break;
            case 2:
                carList = carList2;
                break;
            case 3:
                carList = carList3;
                break;
            default:
                return;
        }
        if (carList.size() > 0) {
            carList.remove(0);
        }
    }
    @Override
    public void run() {
        int carId = 1;
        while (true) {
            synchronized (this) {
                while (!running) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (paused) {
                    continue;
                }
                if (carAdded) {
                    addCarToRandomLane(carId++);
                    carAdded = false;
                }
                for (List<Car> carList : Arrays.asList(carList1, carList2, carList3)) {
                    for (int i = 0; i < carList.size(); i++) {
                        Car car = carList.get(i);
                        if (car.getX() > LANE_WIDTH) {
                            carList.remove(i);
                        } else {
                            car.move(CAR_SPEED);
                        }
                    }
                }
            }
            repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLane(g, 1);
        drawLane(g, 2);
        drawLane(g, 3);
        drawCars(carList1, g);
        drawCars(carList2, g);
        drawCars(carList3, g);
    }
    private void drawLane(Graphics g, int lane) {
        int laneY = (lane - 1) * LANE_HEIGHT / 3;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, laneY, LANE_WIDTH, LANE_HEIGHT / 3);
        g.setColor(Color.WHITE);
    for (int i = 0; i < LANE_WIDTH; i += 40) {
      g.fillRect(i, laneY + LANE_HEIGHT / 6, 20, 10);
    }
  }
  private void drawCars(List<Car> carList, Graphics g) {
    for (Car car : carList) {
        car.draw(g);
    }
}
private void addCarToRandomLane(int carId) {
    Random random = new Random();
    int lane = random.nextInt(3) + 1;
    int x = -CAR_WIDTH;
    int y = random.nextInt(LANE_HEIGHT - CAR_HEIGHT);
    switch (lane) {
        case 1:
            carList1.add(new Car(carId, x, y, lane));
            break;
        case 2:
            carList2.add(new Car(carId, x, y, lane));
            break;
        case 3:
            carList3.add(new Car(carId, x, y, lane));
            break;
    }
}
private class Car {
    private final int id;
    private int x;
    private int y;
    private int lane;
    private final Color color;
    public Car(int id, int x, int y, int lane) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.lane = lane;
        Random random = new Random();
        color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
    public int getX() {return x;}
    public void move(int speed) {x += speed;}
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y + (lane - 1) * LANE_HEIGHT / 3, CAR_WIDTH, CAR_HEIGHT);
    }
}
}