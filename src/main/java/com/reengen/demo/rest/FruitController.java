package com.reengen.demo.rest;

import com.reengen.demo.model.FruitDTO;
import com.reengen.demo.service.FruitService;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/fruits", produces = MediaType.APPLICATION_JSON_VALUE)
public class FruitController {

	private final FruitService fruitService;

	public FruitController(final FruitService fruitService) {
		this.fruitService = fruitService;
	}


	@GetMapping("/{id}")
	public ResponseEntity<FruitDTO> getFruit(@PathVariable final Long id) {
		return ResponseEntity.ok(fruitService.get(id));
	}

	@PostMapping
	public ResponseEntity<Long> createFruit(@RequestBody @Valid final FruitDTO fruitDTO) {
		return new ResponseEntity<>(fruitService.create(fruitDTO), HttpStatus.CREATED);
	}

	

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateFruit(@PathVariable final Long id, @RequestBody @Valid final FruitDTO fruitDTO) {
		fruitService.update(id, fruitDTO);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFruit(@PathVariable final Long id) {
		fruitService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/insertRecords")
	public Boolean insertRecords(@RequestBody @Valid final List<FruitDTO> values) {

		for (FruitDTO k : values) {

			if (k.getName() instanceof String && k.getName().length() > 255) {

				System.out.println("Fruit Name must be 255 characters or less");

				return false;

			} else if ((k.getQuantity() instanceof Integer)
					&& (k.getQuantity().intValue() < -999 || k.getQuantity().intValue() > 999)) {

				System.out.println("Quantity is out of range");

				return false;

			}

		}

		fruitService.saveAll(values);

		return true;
	}
	
	
	@GetMapping
	public List<FruitDTO> getAllRecords () {
		return fruitService.findAll(); 
	}

	@PostMapping("/filterRecords")
	public List<FruitDTO> filterRecords(@RequestBody @Valid final FruitDTO filter) {

		List<FruitDTO> matches = new ArrayList<FruitDTO>();

		List<FruitDTO> rows = fruitService.findAll(); 

		for (int i = 0; i < rows.size(); i++) {

			if (filter.getName() != null && filter.getName().equals(rows.get(i).getName())
					&& filter.getQuantity() != null && filter.getQuantity().equals(rows.get(i).getQuantity()))
				matches.add(rows.get(i));
			else if (filter.getName() != null && filter.getName().equals(rows.get(i).getName())
					&& filter.getQuantity() == null)
				matches.add(rows.get(i));
			else if (filter.getName() == null && filter.getQuantity().equals(rows.get(i).getQuantity()))
				matches.add(rows.get(i));
		}

		return matches;

	}

}
