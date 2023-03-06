package TrafficSim;

import java.util.Timer;
import java.util.TimerTask;
import TrafficSim.TrafficSimulation;

public class TrafficController {

    private static final int SIMULATION_INTERVAL = 100;
    private static final int MAX_CARS = 15;
    private static final int MAX_CAR_ID = 1000;

    private IntersectionPanel intersectionPanel;

    public TrafficController() {}
   
    public void setIntersectionPanel(IntersectionPanel intersectionPanel) {
        this.intersectionPanel = intersectionPanel;
    }

    public void startSimulation() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                addCar();
            }
        };
        timer.schedule(task, SIMULATION_INTERVAL, SIMULATION_INTERVAL);
    }

    private int getNumberOfCars() {
        return intersectionPanel.getNorthSouthCars().size() + intersectionPanel.getEastWestCars().size();
    }

    private void addCar() {
        if (getNumberOfCars() < MAX_CARS) {
            int carId = (int) (Math.random() * MAX_CAR_ID);
            intersectionPanel.addCar(carId);
        }
    }

    public static void main(String[] args) {
        TrafficSimulation trafficSimulation = new TrafficSimulation();
        trafficSimulation.startSimulation();
    }
}
