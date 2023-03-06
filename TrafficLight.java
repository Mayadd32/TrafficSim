package TrafficSim;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TrafficLight {
    private TrafficLightColor color;
    private TrafficLightObserver observer;
    private boolean running;
    private int state = 1; // 1 - green, 2 - yellow, 3 - red

    public TrafficLight() {
        setInitialColor(TrafficLightColor.GREEN);
    }

    public TrafficLight(TrafficLightColor initialColor) {
        setInitialColor(initialColor);
    }

    public void addObserver(TrafficLightObserver observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }

    private void setInitialColor(TrafficLightColor initialColor) {
        this.color = initialColor;
        this.running = true;

        Executors.newSingleThreadExecutor().submit(() -> {
            while (running) {
                try {
                    TimeUnit.SECONDS.sleep(color.getDuration());
                    color = color.getNextTrafficLightColor();
                    notifyObserver();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void notifyObserver() {
        if (observer != null) {
            observer.update(color == TrafficLightColor.GREEN);
        }
    }

    public void turnGreen() {
        state = 1;
        color = TrafficLightColor.GREEN;
    }

    public void turnYellow() {
        state = 2;
        color = TrafficLightColor.YELLOW;
    }

    public void turnRed() {
        state = 3;
        color = TrafficLightColor.RED;
    }

    public int getState() {
        return state;
    }
}
