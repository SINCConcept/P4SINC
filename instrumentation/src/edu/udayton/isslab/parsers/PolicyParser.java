package edu.udayton.isslab.parsers;

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
		policies = policies.substring(policies.indexOf("\""), policies.lastIndexOf("}"));
		//System.out.println(policies);
		
		int index = policies.indexOf(":");
		while(index>0){
			int ind1 = policies.indexOf("\"",index);
			int ind2 = policies.indexOf("\"",ind1+1);
			String policy_name = policies.substring(ind1+1,ind2);
			int s = amp.getPolicies().indexOf(policy_name);
			if(s<0){
				//System.out.println("Policy Name: "+policy_name+" not found in api_map");
				throw new Exception();
			}
			ind1 = policies.indexOf("{",ind2+1);
			ind2 = policies.indexOf("}",ind1+1);
			String det = policies.substring(ind1+1,ind2);
			det = det.replace("\"", "");
			String[] details = det.split(":");
			policy_details.put(policy_name, details);
			//System.out.println(det);
			index = policies.indexOf(":",ind2);
		}
	}
}
