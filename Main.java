import TrafficSim.TrafficSimulation;
import TrafficSim.CarPanel; 
import TrafficSim.ControlPanel;
import TrafficSim.TrafficSimulationGUI;

class Main {

  
  
  public static void main(String[] args) {
    TrafficSimulation mySim = new TrafficSimulation();
    CarPanel carPanel = new CarPanel();
    TrafficSimulationGUI trafficSimulationGUI = new TrafficSimulationGUI();
    ControlPanel controlPanel = new ControlPanel(trafficSimulationGUI);
    

    mySim.start();
  }
}
