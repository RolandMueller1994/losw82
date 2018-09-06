package arduinoCom;

import com.fazecast.jSerialComm.SerialPort;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import main.gui.guielements.ExtendedChoiceBox;

public class ArduinoComGUI extends GridPane implements ArduinoComHandlerInterface {
	
	private ExtendedChoiceBox<SerialPort> comChoice;
	private Button transmitButton;
	private Label connectedLabel;
	private Button connectButton;
	private Button disconnectButton;
	
	private SerialPort selectedPort;
	
	public ArduinoComGUI() {
		
		comChoice = new ExtendedChoiceBox<SerialPort>("Port: ", false) {
			@Override
			protected void stateChanged(SerialPort selected) {
				if(selected != null) {
					connectButton.setDisable(false);
				}
				
				selectedPort = selected;
			}
		};
		
		SerialPort[] ports = ArduinoComHandler.getInstance().getPorts();
		
		for(int i=0; i<ports.length; i++) {
			comChoice.addChoice(ports[i]);
		}
		
		add(comChoice, 0, 0);
		
		connectButton = new Button("Connect");
		add(connectButton, 1, 0);
		connectButton.setDisable(true);
		connectButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				boolean connected = ArduinoComHandler.getInstance().connect(selectedPort);
				
				if(connected) {
					connectionStateChanged(true);
				}
			}
		});
		
		disconnectButton = new Button("Disconnect");
		add(disconnectButton, 2, 0); 
		disconnectButton.setDisable(true);
		disconnectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArduinoComHandler.getInstance().disconnect();
			}
		});
		
		transmitButton = new Button("Transmit");
		add(transmitButton, 3, 0);
		transmitButton.setDisable(true);
		
		connectedLabel = new Label("Disconnected");
		connectedLabel.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(3), new Insets(0))));
		GridPane.setHgrow(connectedLabel, Priority.ALWAYS);
		connectedLabel.setMaxWidth(Double.MAX_VALUE);
		connectedLabel.setAlignment(Pos.CENTER);
		connectedLabel.setPrefHeight(20);
		add(connectedLabel, 0, 1, 4, 1);
		
		setPadding(new Insets(5));
		setHgap(5);
		setVgap(5);
		
		ArduinoComHandler.getInstance().addListener(this);
		
		setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
	}

	@Override
	public void availableSerialPortsChanged(SerialPort[] ports) {
		comChoice.removeAllChoices();
		
		if(ports.length == 0) {
			connectButton.setDisable(true);
		}
		
		for(int i=0; i<ports.length; i++) {
			comChoice.addChoice(ports[i]);
		}
	}

	@Override
	public void connectionStateChanged(boolean connected) {
		if(connected) {
			connectedLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(3), new Insets(0))));
			connectedLabel.setText("Connected");
		} else {
			connectedLabel.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(3), new Insets(0))));
			connectedLabel.setText("Disconnected");
		}
		
		transmitButton.setDisable(!connected);
		connectButton.setDisable(connected);
		disconnectButton.setDisable(!connected);
	}
	
}
