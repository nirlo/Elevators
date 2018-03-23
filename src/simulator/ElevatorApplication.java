package simulator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		int floors = 21;

		HBox center = new HBox();
		Button start = new Button("Start");

		for(int i = 0; i < sim.getElevatorCount(); i++) {
			Text title = new Text("Elevator " + i);
			VBox elevator = new VBox();
			VBox info = new VBox();
			for (int j = 0; j <floors; j++) {
				elevator.getChildren().add(new HBox());
				elevator.getChildren().get(j).setId((j == 20) ? "elevator":"empty");
			}

			info.getChildren().addAll(new Text("Current Floor: "), new Text("0"), new Text("Requested Floor: "),
					new Text("0"), new Text("Power Consumed: "), new Text("0"));

			center.getChildren().add(new VBox(title, new HBox(elevator, info)));
		}

		root.setCenter(center);
		root.setBottom(start);



		Scene scene = new Scene(root, 1200, 640);
		scene.getStylesheets().add("simulator/elevator.css");
		mainStage.setScene(scene);
		mainStage.show();

		start.setOnAction(e -> {
			sim.start();
			});
		
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				
				if(!queue.isEmpty()) {
					ObservableList<Node> elevators = center.getChildren();

					List<Integer> current = queue.poll();
					VBox wholeView = (VBox) elevators.get(current.get(0));
					HBox infoView = (HBox) wholeView.getChildren().get(1);
					VBox fls = (VBox) infoView.getChildren().get(0);
					VBox info = (VBox) infoView.getChildren().get(1);
					ObservableList<Node> elevatorFloors = fls.getChildren();

					int length = 20;
					for(int i = 0; i < length+1; i++){
						if(length -i == length - current.get(1))
							elevatorFloors.get(length - i).setId("elevator");
						else if(length - i == length - current.get(2))
							elevatorFloors.get(length - i).setId("target");
						else
							elevatorFloors.get(length - i).setId("empty");
					}
					//Sets the information passed in from elevatorimp
					((Text) info.getChildren().get(1)).setText(current.get(1).toString());
					((Text) info.getChildren().get(3)).setText(current.get(2).toString());
					((Text) info.getChildren().get(5)).setText(current.get(3).toString());

				}
			}
			
		}.start();

		}
	
	


		public static void main(String[] args) {
			launch();
		}


		@Override
		public void update(List<Integer> list) {
			queue.add(list);
		}

	}
