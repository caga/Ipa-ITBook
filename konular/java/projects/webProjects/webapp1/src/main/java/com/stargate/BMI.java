package com.stargate;

public class BMI {
	double kilo;
	double boy;
	double result;
	public void calculateBmi() {
		this.result=kilo/(boy*boy);
	}

	public BMI(double kilo, double boy) {
		this.kilo = kilo;
		this.boy = boy;
		calculateBmi();
	}
	public String toString(){
		String s=String.format("%.2f",result);
		return s;
	}

}
