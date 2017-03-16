package edu.udayton.issslab.parsers;

import java.util.HashMap;
import java.util.Map;

public class PolicyParser implements Parser{
	
	private ApiMapParser amp;
	private Map<String, String[]> policy_details = new HashMap<>();
	
	public Map<String, String[]> getPolicy_details() {
		return policy_details;
	}

	public ApiMapParser getAmp() {
		return amp;
	}

	public void setAmp(ApiMapParser amp) {
		this.amp = amp;
	}
	
	public PolicyParser(ApiMapParser amp){
		this.amp = amp;
	}
	public void parse(String policies) throws Exception{
		
		policies = policies.substring(policies.indexOf('[')+1, policies.lastIndexOf(']'));
		policies = policies.replace("{", "");
		policies = policies.replace("}", "");
		String[] arr = policies.split("\"action\":");
		for(int x=1;x<arr.length;x++){
			String pName = arr[x].substring(arr[x].indexOf("\"")+1, arr[x].indexOf("\"",arr[x].indexOf("\"")+1));
			String[] temp = arr[x].split(":");
			String[] details = {temp[1].substring(temp[1].indexOf("\"")+1, temp[1].indexOf("\"",temp[1].indexOf("\"")+1)),
								temp[3].substring(temp[3].indexOf("\"")+1, temp[3].indexOf("\"",temp[3].indexOf("\"")+1)),
								temp[6].substring(temp[6].indexOf("\"")+1, temp[6].indexOf("\"",temp[6].indexOf("\"")+1)),
								temp[7].substring(temp[7].indexOf("\"")+1, temp[7].indexOf("\"",temp[7].indexOf("\"")+1))};
			policy_details.put(pName, details);
		}
	}
}
