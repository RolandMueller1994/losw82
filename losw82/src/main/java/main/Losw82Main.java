package main;

import java.io.IOException;

import arduinoCom.ArduinoComHandler;
import i18n.LanguageResourceHandler;
import main.gui.Losw8GUI;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

public class Losw82Main {
	
	public static void main(String[] args) {
		Losw8GUI gui = new Losw8GUI();
		
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();

		// Register current working directory
		String current;
		try {
			current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
			LanguageResourceHandler lanHandler = LanguageResourceHandler.getInstance();

			gui.buildGUI(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArduinoComHandler.getInstance().stopComThread();
		
	}	
}
