package com.triaubaral.lot.dispatch;

import com.triaubaral.lot.inter.Groupable;

public class Discriminant implements Groupable{
	
	private String criteria;
	private int tailleDuLot;

	public void setCriteria(String pCriteria) {
		criteria = pCriteria;		
	}
	
	public String getCriteria() {
		return criteria;
	}
	
	public void setGroupSize(int tailleDuLot) {
		this.tailleDuLot = tailleDuLot;
	}	

	@Override
	public String toString() {
		return "Discriminant [criteria=" + criteria + ", tailleDuLot="
				+ tailleDuLot + "]";
	}

	@Override
	public int getGroupSize() {
		return this.tailleDuLot;
	}	

}
