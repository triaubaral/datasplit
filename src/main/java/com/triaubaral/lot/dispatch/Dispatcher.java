package com.triaubaral.lot.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.triaubaral.lot.inter.Groupable;
import com.triaubaral.lot.inter.Sortable;

public class Dispatcher<T> {

	
	@SuppressWarnings("unchecked")
	public Map<Groupable, List<T>> dispatch(List<Sortable> sortables) {
		
		Map<Groupable, List<T>> dispatchedData = new HashMap<Groupable, List<T>>();		
		
		for(Sortable sortable : sortables){
			
			Groupable discriminant = sortable.getDiscriminant();			
			
			List<T> dataToDispatch = createOrGetExistingList(dispatchedData.get(discriminant));
			
			dataToDispatch.add((T) sortable);
			
			dispatchedData.put(discriminant, dataToDispatch);			
			
		}
		
		return dispatchedData;
	}
	
	private List<T> createOrGetExistingList(List<T> myList){
		
		if(myList == null || myList.isEmpty()){
			return new ArrayList<T>();
		}	
		
		return myList;
	}
	
	

	public Map<Groupable, List<List<T>>> dispatchByTailleDuLot(
			Map<Groupable, List<T>> map) {
		
		Map<Groupable, List<List<T>>> dispatch = new HashMap<Groupable, List<List<T>>>();
		
		for(Entry<Groupable, List<T>> entry : map.entrySet()){
			
			Groupable discriminant = entry.getKey();
			
			List<List<T>> partitions = Lists.partition(entry.getValue(),  discriminant.getGroupSize());

			dispatch.put(discriminant, partitions);
			
		}		
		
		return dispatch;
	}

}
