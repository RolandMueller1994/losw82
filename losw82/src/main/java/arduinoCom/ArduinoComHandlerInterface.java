package arduinoCom;

import com.fazecast.jSerialComm.SerialPort;

public interface ArduinoComHandlerInterface {

	void availableSerialPortsChanged(SerialPort[] ports);
	
	void connectionStateChanged(boolean connected);
	
}
