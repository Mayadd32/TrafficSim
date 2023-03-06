package TrafficSim;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements ActionListener {
    private JButton startButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton addCarButton;
    private JButton removeCarButton;
    private TrafficSimulationGUI trafficSimulationGUI;

    public ControlPanel(TrafficSimulationGUI tsg) {
        this.trafficSimulationGUI = tsg;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setPreferredSize(new Dimension(600, 50));

        startButton = new JButton("Start");
        startButton.addActionListener(this);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);

        addCarButton = new JButton("Add Car");
        addCarButton.addActionListener(this);

        removeCarButton = new JButton("Remove Car");
        removeCarButton.addActionListener(this);

        add(startButton);
        add(pauseButton);
        add(stopButton);
        add(addCarButton);
        add(removeCarButton);
    }

    public void addCar() {
        trafficSimulationGUI.addCar();
    }

    public void removeCar() {
        trafficSimulationGUI.removeCar();
    }

    public void start() {
        trafficSimulationGUI.startSimulation();
    }

    public void pause() {
        trafficSimulationGUI.pauseSimulation();
    }

    public void stop() {
        trafficSimulationGUI.stopSimulation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            start();
        } else if (e.getSource() == pauseButton) {
            pause();
        } else if (e.getSource() == stopButton) {
            stop();
        } else if (e.getSource() == addCarButton) {
            addCar();
        } else if (e.getSource() == removeCarButton) {
            removeCar();
        }
    }
}
