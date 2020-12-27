package oss.airtel.entity;

import java.util.HashMap;

public class BuiltinExpert {

	private HashMap<String, String> inputs;
	private Builtin builtin;
	private Expert expert;
	
	public HashMap<String, String> getInputs() {
		return inputs;
	}
	public void setInputs(HashMap<String, String> inputs) {
		this.inputs = inputs;
	}
	public Builtin getBuiltin() {
		return builtin;
	}
	public void setBuiltin(Builtin builtin) {
		this.builtin = builtin;
	}
	public Expert getExpert() {
		return expert;
	}
	public void setExpert(Expert expert) {
		this.expert = expert;
	}
	
	@Override
	public String toString() {
		return "BuiltinExpert [inputs=" + inputs + ", builtin=" + builtin + ", expert=" + expert + "]";
	}
	
	
}
