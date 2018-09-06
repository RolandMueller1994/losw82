package arduinoCom;

import java.util.HashSet;

import com.fazecast.jSerialComm.SerialPort;

import javafx.application.Platform;

public class ArduinoComHandler {
	
	private static ArduinoComHandler instance;
	
	private ArduinoComRunnable comRunnable;
	
	private HashSet<ArduinoComHandlerInterface> listeners;
	
	private SerialPort ports[];
	private SerialPort connectedPort;
	
	public static ArduinoComHandler getInstance() {
		if(instance == null) {
			instance = new ArduinoComHandler();
		}
		return instance;
	}
	
	private ArduinoComHandler() {
		ports = SerialPort.getCommPorts();
		
		listeners = new HashSet<>();
		
		comRunnable = new ArduinoComRunnable();
		
		Thread comThread = new Thread(comRunnable);
		comThread.start();
	}
	
	public SerialPort[] getPorts() {
		synchronized(ports) {
			return ports;			
		}
	}
	
	
	
	public synchronized void addListener(ArduinoComHandlerInterface listener) {
		listeners.add(listener);
	}
	
	private synchronized void portsChanged() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				synchronized (ports) {
					for(ArduinoComHandlerInterface listener : listeners) {
						listener.availableSerialPortsChanged(ports);
					}					
				}
			}
		});
	}
	
	private class ArduinoComRunnable implements Runnable {

		private boolean run;
		
		@Override
		public void run() {
			run = true;
			
			while(run) {
				SerialPort[] newPorts = SerialPort.getCommPorts();
				
				boolean portsChanged = false;
				if(newPorts.length == ports.length) {
					for(int i=0; i<newPorts.length; i++) {
						SerialPort port = newPorts[i];
						for(int j=0; j<ports.length; j++) {
							if(!ports[j].getDescriptivePortName().equals(port.getDescriptivePortName())) {
								portsChanged = true;
								break;
							}
						}
					}					
				} else {
					portsChanged = true;
				}
				
				if(portsChanged) {
					boolean connectedPresent = false;
					
					if(connectedPort != null) {
						synchronized(connectedPort) {
							for(int i=0; i<newPorts.length; i++) {
								if(newPorts[i].getDescriptivePortName().equals(connectedPort.getDescriptivePortName())) {
									connectedPresent = true;
									break;
								}
							}													
						}
					}
					
					if(!connectedPresent && connectedPort != null) {
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								for(ArduinoComHandlerInterface listener : listeners) {
									listener.connectionStateChanged(false);
								}								
							}
						});
						synchronized(connectedPort) {
							connectedPort.closePort();
							connectedPort = null;
							
						}
					}
					if(connectedPort == null || !connectedPresent) {
						ports = newPorts;
						portsChanged();						
					}
				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void stopComThread() {
		comRunnable.run = false;
	}
	
	public boolean connect(SerialPort port) {
		port.openPort();
		
		if(port.isOpen()) {
			byte[] sendConnectData = new byte[1];
			sendConnectData[0] = 100;
			byte[] readConnectData = new byte[1];
			port.writeBytes(sendConnectData, 1);
			
			port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 500, 0);
			
			int readBytes = port.readBytes(readConnectData, 1);
			
			if(readBytes == 1) {
				if(readConnectData[0] == sendConnectData[0] -1) {
					System.out.println("Connected");
					connectedPort = port;
					return true;
				}
			}
		}
		System.out.println("Not Connected");
		return false;
	}
	
	public void disconnect() {
		if(connectedPort != null) {
			synchronized(connectedPort) {
				connectedPort.closePort();
				connectedPort = null;
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						for(ArduinoComHandlerInterface listener : listeners) {
							listener.connectionStateChanged(false);
						}
					}
					
				});
			}
		}
	}
}
