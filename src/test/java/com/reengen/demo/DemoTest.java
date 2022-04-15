package com.reengen.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reengen.demo.model.FruitDTO;
import com.reengen.demo.repos.FruitRepository;
import com.reengen.demo.rest.FruitController;
import com.reengen.demo.service.FruitService;


@ExtendWith(MockitoExtension.class)
class DemoTest {
	
	@Mock
	FruitRepository fruitRepository;

	@Mock
	FruitService fruitService;
	
	@InjectMocks
	FruitController fruitController;  
	
	@Test
	void test_InsertRecords_Quantity_Control_AutofRange_witGreaterThan999() {
		
		List<FruitDTO> list =  getAutofRangeRecordAsList(1); 	 
		
		assertEquals(false, fruitController.insertRecords(list));
	}
	
	@Test
	void test_InsertRecords_Quantity_Control_AutofRange_witSmallerThanMinus999() {
		
		List<FruitDTO> list = getAutofRangeRecordAsList(-1); 		
		
		assertEquals(false, fruitController.insertRecords(list));
	}

	
	
	@Test
	void test_InsertRecords_Name_Length_Control() {
		
		List<FruitDTO> list = getRecordWithLongNameLengthAsList(); 		
		
		assertEquals(false, fruitController.insertRecords(list)); 
	}

	
	
	@Test
	void test_InsertRecords_Normal_Record() {
		
		List<FruitDTO> list = getNormalRecordAsList(); 	 	
		
		assertEquals(true, fruitController.insertRecords(list));
	}
	
	
	

	@Test
	void test_getAllRecords() {
		
		List<FruitDTO> list = getList(); 	 	
		
		when(fruitService.findAll()).thenReturn(list);  
		
		assertEquals(list.size(), fruitController.getAllRecords().size()); 
		assertEquals(list.get(0).getName(), fruitController.getAllRecords().get(0).getName());
	}
	
	@Test
	void test_filterRecords_Wiht_Just_Name() {
		
		List<FruitDTO> list = getList(); 	 	
		
		when(fruitService.findAll()).thenReturn(list); 
		
		FruitDTO filter = new FruitDTO(); 
		filter.setName("pear");
		 
		assertEquals(list.stream().filter(p->p.getName().equals("pear")).collect(Collectors.toList()).size(), fruitController.filterRecords(filter).size()); 
		assertEquals(2, fruitController.filterRecords(filter).size());
		assertEquals("pear", fruitController.filterRecords(filter).get(0).getName()); 
	}
	
	
	@Test
	void test_filterRecords_Wiht_Just_Quantity() {
		
		List<FruitDTO> list = getList(); 	 	
		
		when(fruitService.findAll()).thenReturn(list); 
		
		FruitDTO filter = new FruitDTO(); 
		filter.setQuantity(10); 
		 
		assertEquals(list.stream().filter(p->p.getQuantity().equals(10)).collect(Collectors.toList()).size(), fruitController.filterRecords(filter).size()); 
		assertEquals(2, fruitController.filterRecords(filter).size()); 
		assertEquals(true, Arrays.asList("apple","strawberry").contains(fruitController.filterRecords(filter).get(0).getName()));  
		assertEquals(true,  Arrays.asList("apple","strawberry").contains(fruitController.filterRecords(filter).get(1).getName())); 
	}
	
	
	@Test
	void test_filterRecords_Wiht_Name_Quantity() {
		
		List<FruitDTO> list = getList(); 	 	
		
		when(fruitService.findAll()).thenReturn(list); 
		
		FruitDTO filter = new FruitDTO(); 
		filter.setName("apple"); 
		filter.setQuantity(10); 
		 
		 
		assertEquals(1, fruitController.filterRecords(filter).size()); 
		assertEquals("apple", fruitController.filterRecords(filter).get(0).getName()); 
		
	}
	
	

	private List<FruitDTO> getRecordWithLongNameLengthAsList() {
		FruitDTO fruit = new FruitDTO();
		fruit.setQuantity(10); 
		fruit.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vivamus at augue eget arcu dictum varius duis at consectetur. Cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo. Non pulvinar neque laoreet suspendisse interdum consectetur libero id faucibus. Arcu dui vivamus arcu felis bibendum. Consectetur adipiscing elit duis tristique sollicitudin nibh. Urna molestie at elementum eu facilisis sed odio. Quisque non tellus orci ac auctor augue mauris augue. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Vitae justo eget magna fermentum iaculis. Tellus at urna condimentum mattis pellentesque id nibh. Non arcu risus quis varius quam quisque. Iaculis at erat pellentesque adipiscing commodo elit. Laoreet suspendisse interdum consectetur libero id faucibus. Odio aenean sed adipiscing diam donec adipiscing.");  
		List<FruitDTO> list = Arrays.asList(fruit);
		return list;
	}
	
	private List<FruitDTO> getNormalRecordAsList() {
		FruitDTO fruit = new FruitDTO();
		fruit.setQuantity(10); 
		fruit.setName("apple");  
		List<FruitDTO> list = Arrays.asList(fruit);
		return list;
	}
	
	private List<FruitDTO> getAutofRangeRecordAsList(int sign) {
		FruitDTO fruit = new FruitDTO();
		fruit.setQuantity(1000*sign);  
		fruit.setName("apple"); 
		List<FruitDTO> list = Arrays.asList(fruit);
		return list;
	}
	
	private List<FruitDTO> getList() {
		
		List<FruitDTO> list = new ArrayList<FruitDTO>(); 
		
		FruitDTO fruit1 = new FruitDTO("apple",10);
		list.add(fruit1);
		
		FruitDTO fruit2 = new FruitDTO("apple",20);
		list.add(fruit2);
		
		FruitDTO fruit3 = new FruitDTO("pear",30);
		list.add(fruit3);
		
		FruitDTO fruit4 = new FruitDTO("strawberry",10);
		list.add(fruit4); 
		
		FruitDTO fruit5 = new FruitDTO("cherry",40);
		list.add(fruit5); 
		
		FruitDTO fruit6 = new FruitDTO("pear",25);
		list.add(fruit6); 
						
		return list;
	}
	

}
