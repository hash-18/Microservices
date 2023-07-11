package com.mac.microservices.limitsservices.beans;

public class Limits {
	
	private int minLimit;
	private int maxLimit;
	

	public Limits() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Limits(int minLimit, int maxLimit) {
		super();
		this.minLimit = minLimit;
		this.maxLimit = maxLimit;
	}

	public int getMinLimit() {
		return minLimit;
	}

	public void setMinLimit(int minLimit) {
		this.minLimit = minLimit;
	}


	public int getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}



}
