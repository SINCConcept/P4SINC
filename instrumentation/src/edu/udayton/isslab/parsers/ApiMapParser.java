package edu.udayton.isslab.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiMapParser implements Parser{
	
	private List<String> policies = new ArrayList<>();
	private Map<String, String[]> policy_methods = new HashMap<>();
	
	public List<String> getPolicies() {
		return policies;
	}
	
	public Map<String, String[]> getPolicy_methods() {
		return policy_methods;
	}

	public void parse(String api_map){
		
		//Splitting map into a string array.
		//Each value in the array represents one policy and the methods it corresponds to.
		api_map = api_map.substring(api_map.indexOf('[')+1, api_map.lastIndexOf(']'));
		String[] maps = api_map.split("\\{");
		for(int x=1;x<maps.length;x++){
			maps[x]=maps[x].replace("}", "");
		}
				
		//Breaking down policies into names and methods they are to be applied to.
		//The List contains the names of all policies.
		//The map contains array of the methods to which each policy is to be applied.
		//The policy name acts as key to the map.
		for(String s : maps){
			if(s.equals(""))
				continue;
			int index = s.indexOf('\"');
			int fIndex = s.indexOf('\"', index+1);
			String temp = s.substring(index+1, fIndex);
			policies.add(temp);
			s = s.substring(s.indexOf('[')+1, s.lastIndexOf(']'));
			String[] splits = s.split(",");
			for(int x=0;x<splits.length;x++){
				splits[x] = splits[x].replaceAll("\"", "");
			}
			policy_methods.put(temp, splits);
		}
	}
	
}
