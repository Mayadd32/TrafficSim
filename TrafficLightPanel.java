package TrafficSim;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
interface TrafficLightObserver {public void update(boolean isGreen);}
enum TrafficLightColor {
    RED, YELLOW, GREEN;
}
public class TrafficLightPanel extends JPanel implements TrafficLightObserver {
    private final TrafficLight trafficLight;
    private final ScheduledExecutorService executorService;
    public TrafficLightPanel() {
        trafficLight = new TrafficLight(TrafficLightColor.RED);
        trafficLight.addObserver(this);
        executorService = Executors.newSingleThreadScheduledExecutor();
executorService.scheduleAtFixedRate(() -> trafficLight.changeColor(), 0, 1, TimeUnit.SECONDS);
        setPreferredSize(new Dimension(100, 200));
    }
    @Override
    public void update(TrafficLightColor color) {
        repaint();
    }
  @Override
  public void update(boolean isGreen) {
  trafficLight.setColor(isGreen ? TrafficLightColor.GREEN : TrafficLightColor.RED);
  }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        TrafficLightColor color = trafficLight.getColor();
        if (color == TrafficLightColor.GREEN) {
            g.setColor(Color.GREEN);
            g.fillOval(25, 25, 50, 50);
        } else if (color == TrafficLightColor.YELLOW) {
            g.setColor(Color.YELLOW);
            g.fillOval(25, 85, 50, 50);
        } else if (color == TrafficLightColor.RED) {
            g.setColor(Color.RED);
            g.fillOval(25, 145, 50, 50);
        }
    }
}