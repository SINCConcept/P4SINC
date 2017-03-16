package edu.udayton.issslab.handler;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class TimesHandler implements Handler {
	
	private String aspect;
	private String policy;
	private List<String> methods;
	private String[] details;
	private List<String> server;
	public TimesHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimesHandler(String policy, List<String> methods, String[] details, List<String> server) {
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
	
	@Override
	public String getAspect() {
		return aspect;
	}

	@SuppressWarnings("resource")
	@Override
	public Exception generateAspect() throws Exception{
		aspect = "aspect "+policy+"{\n";
		aspect = aspect+"\tstatic{\n\t\tUtility.init(\""+server.get(0)+"\",\""+server.get(1)+"\",\""+server.get(2)+"\");\n\t}\n";
		aspect = aspect + "\tboolean check = false;\n";
		aspect = aspect + "\tint "+policy+"_"+details[0]+" = 1;\n";
		for(String method : methods){
			try{
				InputStream is = TimesHandler.class.getResourceAsStream("Times.aj");
				aspect = aspect + new Scanner(is).useDelimiter("\\A").next();
			} catch (Exception e) {
				e.printStackTrace();
				throw new FileNotFoundException("Times.aj");
			}
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
			aspect = aspect.replace(token, details[1]);
			String pointcut = method.substring(0, method.lastIndexOf("("));
			pointcut = pointcut.substring(pointcut.lastIndexOf('.')+1);
			aspect = aspect.replace("#policy#", policy+"_"+pointcut);
			aspect = aspect.replace("#value_name#", policy+"_"+details[0]);
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

}