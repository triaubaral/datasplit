package com.triaubaral.lot.bo;

import com.triaubaral.lot.dispatch.Discriminant;
import com.triaubaral.lot.inter.Sortable;

public class Fruit implements Sortable {
	
	private Discriminant discriminant;
	private String name;
		
	public void setName(String name) {
		this.name = name;
		
	}
	
	public String getName() {
		return name;
	}
			
	public Discriminant getDiscriminant(){		
		return discriminant;
	}
	
	public void setDiscriminant(Discriminant pDiscriminant) {
		this.discriminant=pDiscriminant;
	}

	@Override
	public String toString() {
		return "Fruit [name=" + name + "]";
	}	
		
}
