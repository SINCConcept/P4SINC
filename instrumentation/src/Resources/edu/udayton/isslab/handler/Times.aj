	pointcut #policy#(): execution (* #API#);
	int #value_name# = 0;
	String around() : #policy#(){
		if(#value_name#<=#value#){
			#value_name#++;
			String ret = proceed();
			System.out.println("#value_name#="+#value_name#);
			return ret;
			
		}else{
			System.err.println("Policy violated!");
			try {
				//sleep 1 second
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return "";
		}

	}

