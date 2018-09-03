package switchconfig;

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
}
