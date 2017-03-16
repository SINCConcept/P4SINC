package edu.udayton.issslab.handler;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AmountHandler implements Handler {
	
	private String aspect;
	private String policy;
	private List<String> methods;
	private String[] details;
	private List<String> server;
	public AmountHandler() {
		super();
	}

	public AmountHandler(String policy, List<String> methods, String[] details, List<String> server) {
		super();
		this.policy = policy;
		this.methods = methods;
		this.details = details;
		this.server = server;
	}
	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

	public String[] getDetails() {
		return details;
	}

	public void setDetails(String[] details) {
		this.details = details;
	}
	
	public String getAspect() {
		return aspect;
	}

	@SuppressWarnings("resource")
	public Exception generateAspect() throws Exception {
		aspect = "aspect "+policy+"{\n";
		aspect = aspect+"\tstatic{\n\t\tUtility.init(\""+server.get(0)+"\",\""+server.get(1)+"\",\""+server.get(2)+"\");\n\t}\n";
		for(String method : methods){
			try{
				InputStream is = AmountHandler.class.getResourceAsStream("Amount.aj");
				aspect = aspect + new Scanner(is).useDelimiter("\\A").next();
			} catch (Exception e) {
				e.printStackTrace();
				throw new FileNotFoundException("Amount.aj");
			}
			List<String> vars = getArguments(method);
			if(aspect.indexOf("#API#")<0){
				System.out.println("API token not found in aspect file.");
				throw new Exception();
			}
			aspect = aspect.replace("#API#", method);
			String token = "#value#";
			if(aspect.indexOf(token)<0){
				System.out.println(details[0] +" token not found in aspect file.");
				throw new Exception();
			}
			String value = getValue(details[1]);
			aspect = aspect.replace(token, value);
			String pointcut = method.substring(0, method.lastIndexOf("("));
			pointcut = pointcut.substring(pointcut.lastIndexOf('.')+1);
			aspect = aspect.replace("#policy#", policy+"_"+pointcut);
			aspect = aspect.replace("#arguments#", vars.get(0));
			aspect = aspect.replace("#args_val#", vars.get(1));
			aspect = aspect.replace("#arg2#", vars.get(2));
			aspect = aspect.replace("#message#", "\""+details[2]+"\"");
			switch(details[3].toLowerCase()){
			case "suppression":
				aspect = aspect.replace("#action#", "return;");
				break;
			case "stop":
				aspect = aspect.replace("#action#", "System.exit(0);");
			}
		}
		aspect = aspect+"}\n\n";
		return null;
	}
	
	private List<String> getArguments(String method){
		List<String> vars = new ArrayList<>();
		method = method.substring(method.indexOf('(')+1, method.lastIndexOf(')'));
		String[] split = method.split(",");
		int count = 1;
		String args_val = "";
		String arguments = "";
		String imp_var = "";
		for(String s : split){
			String var = "var"+count++;
			if(count==3){
				imp_var = var;
			}
			args_val = args_val+","+var;
			arguments = arguments + ","+s+" "+var;
		}
		args_val = args_val.substring(1);
		arguments = arguments.substring(1);
		vars.add(arguments);
		vars.add(args_val);
		vars.add(imp_var);
		return vars;
	}
	
	private String getValue(String input){
		char ch = input.toLowerCase().charAt(input.length()-2);
		long multiplier = 1;
		switch(ch){
		case 'k':
			multiplier = 1024;
			break;
		case 'g':
			multiplier = 1024*1024;
			break;
		default:
			multiplier = 1;
			break;
		}
		long value;
		if(multiplier == 1){
			value = Long.parseLong(input.substring(0, input.length()-1));
		}else{
			value = Long.parseLong(input.substring(0, input.length()-2));
		}
		return String.valueOf(value*multiplier);
	}

}