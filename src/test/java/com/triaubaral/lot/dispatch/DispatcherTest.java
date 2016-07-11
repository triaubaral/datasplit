package com.triaubaral.lot.dispatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.triaubaral.lot.bo.Fruit;
import com.triaubaral.lot.dispatch.Discriminant;
import com.triaubaral.lot.dispatch.Dispatcher;
import com.triaubaral.lot.inter.Groupable;
import com.triaubaral.lot.inter.Sortable;

public class DispatcherTest {
	
	List<Sortable> fruits = new ArrayList<Sortable>();
	Dispatcher<Fruit> dispatcher = new Dispatcher<Fruit>();
	
	Discriminant rouge = new Discriminant();
	Discriminant vert = new Discriminant();
	Discriminant jaune = new Discriminant();
	
	Fruit banane = new Fruit();
	Fruit pamplemouse = new Fruit();
	Fruit fraise = new Fruit();
	Fruit framboise = new Fruit();
	Fruit pomme = new Fruit();
	Fruit poire = new Fruit();
	
	
	@Before
	public void init() {
		
		rouge.setCriteria("rouge");
		rouge.setGroupSize(1);
		
		vert.setCriteria("vert");
		vert.setGroupSize(2);
		
		jaune.setCriteria("jaune");
		jaune.setGroupSize(2);
				
		banane.setName("banane");
		banane.setDiscriminant(jaune);		
		
		pamplemouse.setName("pamplemouse");
		pamplemouse.setDiscriminant(jaune);
				
		fraise.setName("fraise");
		fraise.setDiscriminant(rouge);		
		
		framboise.setName("framboise");
		framboise.setDiscriminant(rouge);		
		
		pomme.setName("pomme");
		pomme.setDiscriminant(vert);
				
		poire.setName("poire");
		poire.setDiscriminant(vert);			
		 
		 fruits.add(banane);
		 fruits.add(pamplemouse);
		 fruits.add(pomme);
		 fruits.add(poire);
		 fruits.add(fraise);
		 fruits.add(framboise);
		 		 
	}

	@Test
	public void shouldDispatch() {
		
		Map<Groupable, List<Fruit>> map = dispatcher.dispatch(fruits);	
						
		Assert.assertEquals(3, map.size());
		Assert.assertTrue(map.keySet().contains(jaune));
		Assert.assertTrue(map.keySet().contains(rouge));
		Assert.assertTrue(map.keySet().contains(vert));
		Assert.assertEquals(2, map.get(jaune).size());
		Assert.assertEquals(2, map.get(rouge).size());
		Assert.assertEquals(2, map.get(vert).size());
		
	}
	
	@Test
	public void shoulLimitArraySize() throws IOException{
		
		File workingDir = new File("src/test/resources");
		
		FileUtils.cleanDirectory(workingDir);
		
		Map<Groupable, List<Fruit>> map = dispatcher.dispatch(fruits);		
		
		Map<Groupable, List<List<Fruit>>> map2 = dispatcher.dispatchByTailleDuLot(map);
		
		System.out.println(map2);
		
		int i = 0;
		
		for(Entry<Groupable, List<List<Fruit>>> entry : map2.entrySet()){
			
			List<List<Fruit>> fruitsDeFruits = entry.getValue();			
			
			for(List<Fruit> fruits : fruitsDeFruits){
				
				File unLot = new File(workingDir+"/Lot"+i++);
				
				for(Sortable fruit: fruits){
					
					FileUtils.write(unLot, fruit.toString(), true);
				}
				
				
			}			
			
		}
		
		Assert.assertEquals(4, workingDir.listFiles().length);
	}

}
