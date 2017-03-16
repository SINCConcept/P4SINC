	pointcut #policy#(): execution (* #API#);
	void around() : #policy#(){
        if(!check){
            if(#value_name#<=#value#){
                #value_name#++;
                proceed();
                //System.out.println("#value_name#="+#value_name#);
            }else{
                check = true;
                Utility.send(#message#);
                #action#
            }
		}else{
            proceed();
		}

	}

