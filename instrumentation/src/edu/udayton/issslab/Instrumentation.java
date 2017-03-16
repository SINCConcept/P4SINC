package edu.udayton.issslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.udayton.issslab.handler.AmountHandler;
import edu.udayton.issslab.handler.Handler;
import edu.udayton.issslab.handler.TimesHandler;
import edu.udayton.issslab.parsers.ApiMapParser;
import edu.udayton.issslab.parsers.PolicyParser;

public class Instrumentation {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String api_map = "";
		String policy = "";
		String apif="";
		String polf="";
		if(args.length<3){
			System.out.println("Json input not specified. Using defaults.");
			apif = "api-map.json";
			polf = "policy.json";
		}else{
			apif = args[0];
			polf = args[1];
		}
		//Reading both files and storing in string
		try{
			api_map = new Scanner(new File(apif)).useDelimiter("\\A").next();
			policy = new Scanner(new File(polf)).useDelimiter("\\A").next();
			
			//Parser for api-map.json
			ApiMapParser amp = new ApiMapParser();
			amp.parse(api_map);
			String output="import edu.udayton.isslab.Utility;\n\n";
			//Parser for policy.json
			PolicyParser policyP = new PolicyParser(amp);
			policyP.parse(policy);
			List<Handler> handlers = new ArrayList<Handler>();
			for(String pol : amp.getPolicies()){
				List<String> methods = amp.getPolicy_methods().get(pol);
				String[] details = policyP.getPolicy_details().get(pol);				
				switch(details[0].toLowerCase()){
				case "times":
					TimesHandler th = new TimesHandler(pol,methods,details,amp.getServerDetails());
					th.generateAspect();
					handlers.add(th);
					break;
				case "amount":
					AmountHandler ah = new AmountHandler(pol,methods,details,amp.getServerDetails());
					ah.generateAspect();
					handlers.add(ah);
					break;
				default:
					System.out.println("No Match found for policy: "+pol+" in configuration file");
				}
			}
			
			for(Handler h : handlers){
				output = output + h.getAspect() + "\n\n";
			}
			File aspectFile = new File("output.aj");
			PrintWriter p = new PrintWriter(aspectFile);
    		p.print(output);
    		p.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println("An Error Occured. Please see previous Message");
			e.printStackTrace();
		}
	}
}
