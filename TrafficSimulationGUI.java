package TrafficSim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TrafficSimulationGUI extends JFrame {
    private final TrafficLightPanel trafficLightPanel;
    private final CarPanel carPanel;
    private final ControlPanel controlPanel;
    private final IntersectionPanel intersectionPanel;

    public TrafficSimulationGUI() {
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        trafficLightPanel = new TrafficLightPanel();
        carPanel = new CarPanel();
        intersectionPanel = new IntersectionPanel();
        controlPanel = new ControlPanel(intersectionPanel, trafficLightPanel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(trafficLightPanel, BorderLayout.WEST);
        panel.add(carPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void removeCar() {
        intersectionPanel.removeCar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrafficSimulationGUI());
    }
}
