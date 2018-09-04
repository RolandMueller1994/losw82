package switchconfig;

import xmlreader.XMLElement;

public class SwitchConfigWrapper {
	
	private int switchNumber;
	private boolean preset;
	private boolean inactive;
	
	private boolean[] loops;
	private boolean ab1;
	private boolean ab2;
	
	public SwitchConfigWrapper(int switchNumber, boolean inactive) {
		setDefault();
		
		this.switchNumber = switchNumber;
		this.inactive = inactive;
	}
	
	public boolean isInactive() {
		return inactive;
	}
	
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	
	public void setDefault() {
		preset = true;
		loops = new boolean[8];
		loops[0] = true;
		for(int i=1; i<8; i++) {
			loops[i] = false;
		}
		ab1 = false;
		ab2 = false;
	}
	
	public int getSwitchNumber() {
		return switchNumber;
	}
	
	public void setSwitchNumber(int switchNumber) {
		this.switchNumber = switchNumber;
	}
	
	public boolean isPreset() {
		return preset;
	}
	
	public void setPreset(boolean preset) {
		this.preset = preset;
	}
	
	public boolean getLoopActive(int loop) {
		return loops[loop];
	}
	
	public void setLoopActive(int loop, boolean active) {
		loops[loop] = active;
	}
	
	public boolean isAb1() {
		return ab1;
	}
	
	public void setAb1(boolean ab1) {
		this.ab1 = ab1;
	}
	
	public boolean isAb2() {
		return ab2;
	}
	
	public void setAb2(boolean ab2) {
		this.ab2 = ab2;
	}
	
	public void getXMLConfig(XMLElement parent) {
		XMLElement swNr = new XMLElement("number");
		swNr.setTextContent(new Integer(switchNumber).toString());
		parent.addElement(swNr);
		
		XMLElement presetElem = new XMLElement("preset");
		presetElem.setTextContent(new Boolean(preset).toString());
		parent.addElement(presetElem);
		
		XMLElement inactiveElem = new XMLElement("inactive");
		inactiveElem.setTextContent(new Boolean(inactive).toString());
		parent.addElement(inactiveElem);
		
		for(int i=0; i<loops.length; i++) {
			XMLElement loopElem = new XMLElement("loop");
			
			XMLElement nrElem = new XMLElement("number");
			nrElem.setTextContent(new Integer(i).toString());
			XMLElement stateElem = new XMLElement("state");
			stateElem.setTextContent(new Boolean(loops[i]).toString());
			
			loopElem.addElement(nrElem);
			loopElem.addElement(stateElem);
			parent.addElement(loopElem);
		}
		
		XMLElement ab1Element = new XMLElement("ab1");
		ab1Element.setTextContent(new Boolean(ab1).toString());
		parent.addElement(ab1Element);
		
		XMLElement ab2Element = new XMLElement("ab2");
		ab2Element.setTextContent(new Boolean(ab2).toString());
		parent.addElement(ab2Element);
	}
}
