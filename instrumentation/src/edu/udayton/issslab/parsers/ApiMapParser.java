package edu.udayton.issslab.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiMapParser implements Parser{
	
	private List<String> policies = new ArrayList<>();
	private Map<String, List<String>> policy_methods = new HashMap<>();
	private List<String> serverDetails = new ArrayList<>();
	
	public List<String> getServerDetails() {
		return serverDetails;
	}

	public List<String> getPolicies() {
		return policies;
	}
	
	public Map<String, List<String>> getPolicy_methods() {
		return policy_methods;
	}

	public void parse(String api_map){
		String[] parts = api_map.split("server_details");
		parseServerDetails(parts[1]);
		parseApiDetails(parts[0]);
	}
	
	private void parseServerDetails(String input){
		input = input.substring(input.indexOf('[')+1, input.lastIndexOf(']'));
		input = input.replace("\"", "");
		String[] parts = input.split(",");
		for(String st : parts){
			serverDetails.add(st);
		}
	}
	
	private void parseApiDetails(String input){
		//Splitting map into a string array.
		//Each value in the array represents one policy and the methods it corresponds to.
		input = input.substring(input.indexOf('[')+1, input.lastIndexOf(']'));
		String[] maps = input.split("\\{");
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
			List<String> methods = new ArrayList<>();
			while(s.indexOf("\"")>=0){
				int in = s.indexOf("\"");
				int ine = s.indexOf("\"",in+1);
				String t = s.substring(in+1,ine);
				methods.add(t);
				in = s.indexOf(",",ine);
				if(in==-1){
					break;
				}
				s =s.substring(in+1);
			}
			policy_methods.put(temp, methods);
		}
	}
}
