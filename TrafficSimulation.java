package TrafficSim;

import TrafficSim.TrafficController;
import javax.swing.JFrame;

public class TrafficSimulation {
    private CarPanel carPanel;
    private IntersectionPanel intersectionPanel;
    private TrafficController trafficController;
    public void start() {
        carPanel.start();
        Thread(intersectionPanel).start();
        Thread(trafficController).start();
    }
    public static void main(String[] args) {
        TrafficSimulationGUI gui = new TrafficSimulationGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        TrafficSimulation trafficSimulation = new TrafficSimulation();
        trafficSimulation.carPanel = gui.getTrafficPanel();
        Thread carPanelThread = new Thread(trafficSimulation.carPanel);
        carPanelThread.start();
        trafficSimulation.intersectionPanel = gui.getIntersectionPanel();
        Thread intersectionPanelThread = new Thread(trafficSimulation.intersectionPanel);
        intersectionPanelThread.start();
        trafficSimulation.trafficController = new TrafficController(trafficSimulation.carPanel, trafficSimulation.intersectionPanel);
        Thread trafficControllerThread = new Thread(trafficSimulation.trafficController);
        trafficControllerThread.start();
        trafficSimulation.start();
    }
}