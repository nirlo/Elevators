package simulator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulator.Observer;

/**
 * CST8288 Object Oriented Programming Design Patterns
 * Assignment 1
 * Due: February 16th 2018
 * 
 * @author lock0134@algonquinlive.com
 * Nicholas Lockhart
 * 040905348
 * 
 * Class: ElevatorApplication
 * Description: Main view of the Elevator Simulator. Provides a GUI for the 
 * 				user to operate the elevator system. Observes ElevatorImp
 * 				to receive data to put into Queue and processed for the user
 * 				to see.
 *
 */
public class ElevatorApplication extends Application implements Observer {
	private Queue< List< Integer>> queue = new LinkedList<>();

	/**
	 * Starts the application
	 * 
	 * @param Stage
	 */
	@Override
	public void start(Stage mainStage) throws Exception {
		/**
		 * Simulator, takes in this observer and passes it through to the elevatorimp
		 */
		Simulator sim = new Simulator(this);
		/**
		 * Panes that organize the GUI
		 */
		BorderPane root = new BorderPane();
		VBox elevator = new VBox();
		VBox info = new VBox();
		//Next lines fill the elevator VBox with the visual representation of the floors
		int floors = 21;

		for(int i=0; i<floors;i++) {
			elevator.getChildren().add(new HBox());
			elevator.getChildren().get(i).setId((i == 20) ? "elevator":"empty");
		}

		/**
		 * Check box controls the animation of the elevator, either restarting the elevator
		 * animation or stopping it mid sequence.
		 */
		CheckBox cb = new CheckBox("Animate");
		cb.setSelected(false);

		//Sets the info panel
		info.getChildren().addAll(new Text("Current Floor: "), new Text("0"), new Text("Requested Floor: "),
				new Text("0"), new Text("Power Consumed: "), new Text("0"), cb);

		root.setCenter(elevator);
		root.setRight(info);

		Scene scene = new Scene(root, 300, 600);
		scene.getStylesheets().add("simulator/elevator.css");
		mainStage.setScene(scene);
		sim.start();
		mainStage.show();

		ObservableList<Node> elevatorFloors = elevator.getChildren();

		AnimationTimer elevatorAnimation = new AnimationTimer() {

			private long lastUpdate = 0;
			@Override
			public void handle(long now) {

				//Sets the framerate of the animation
				if(now - lastUpdate >= 500_000_70) {
					//Gets the data from queue to be used to set visualizations
					List<Integer> current = queue.poll();
					
					/**
					 * Goes through the visuals of floors and places the currentFloor of the elevator
					 * and the target floor. Uses length - i so the visual places the 0th floor at the
					 * bottom of the visual
					 */
					int length = 20;
					for(int i = 0; i < length+1; i++){
						if(length -i == length - current.get(0))
							elevatorFloors.get(length - i).setId("elevator");
						else if(length - i == length - current.get(1))
							elevatorFloors.get(length - i).setId("target");
						else
							elevatorFloors.get(length - i).setId("empty");
					}
					//Sets the information passed in from elevatorimp
					((Text) info.getChildren().get(1)).setText(current.get(0).toString());
					((Text) info.getChildren().get(3)).setText(current.get(1).toString());
					((Text) info.getChildren().get(5)).setText(current.get(2).toString());
					lastUpdate = now ;

					//adds the information to the end of the queue. This allows the animation to loop
					queue.add(current);
					
					//Ends the animation at the preset power usage
					if(current.get(2) == 68)
						cb.setSelected(false);

				}
			}

		};
		cb.selectedProperty().addListener((Observable o) -> {
			if (cb.isSelected()) {
				elevatorAnimation.start();
			} else {
				elevatorAnimation.stop();
			}
		});



	}

	public static void main(String[] args) {
		launch();
	}

	
	@Override
	public void update(List<Integer> list) {
		queue.add(list);
	}

}
