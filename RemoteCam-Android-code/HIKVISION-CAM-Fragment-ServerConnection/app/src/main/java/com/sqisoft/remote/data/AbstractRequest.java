package com.sqisoft.remote.data;

public abstract class AbstractRequest<U> {

    abstract public U getData();
    
    public boolean isSuccess(){
    	return getData()!=null;
    }
}
