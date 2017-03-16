	pointcut #policy#(#arguments#): execution (* #API#) && args(#args_val#);
	int amount_state = 0;
	boolean check = false;
	void around(#arguments#) : #policy#(#args_val#) {
		if(!check){
			if(amount_state <= #value#){
				amount_state+= #arg2#.toString().getBytes().length;
                proceed(#args_val#);
	
			}else{
				check = true;
                Utility.send(#message#);
                #action#
			}
		}else{
			proceed(#args_val#);
		}
	}

