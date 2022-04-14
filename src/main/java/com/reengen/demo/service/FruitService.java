package com.reengen.demo.service;

import com.reengen.demo.domain.Fruit;
import com.reengen.demo.model.FruitDTO;
import com.reengen.demo.repos.FruitRepository;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(final FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public List<FruitDTO> findAll() {
        return fruitRepository.findAll()
                .stream()
                .map(fruit -> mapToDTO(fruit, new FruitDTO()))
                .collect(Collectors.toList());
    }

    public FruitDTO get(final Long id) {
        return fruitRepository.findById(id)
                .map(fruit -> mapToDTO(fruit, new FruitDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final FruitDTO fruitDTO) {
        final Fruit fruit = new Fruit();
        mapToEntity(fruitDTO, fruit);
        return fruitRepository.save(fruit).getId();
    }

    public void update(final Long id, final FruitDTO fruitDTO) {
        final Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(fruitDTO, fruit);
        fruitRepository.save(fruit);
    }

    public void delete(final Long id) {
        fruitRepository.deleteById(id);
    }

    private FruitDTO mapToDTO(final Fruit fruit, final FruitDTO fruitDTO) {
        fruitDTO.setId(fruit.getId());
        fruitDTO.setName(fruit.getName());
        fruitDTO.setQuantity(fruit.getQuantity());
        return fruitDTO;
    }

    private Fruit mapToEntity(final FruitDTO fruitDTO, final Fruit fruit) {
        fruit.setName(fruitDTO.getName());
        fruit.setQuantity(fruitDTO.getQuantity());
        return fruit;
    }
 
	public void saveAll(@Valid List<FruitDTO> values) {
		
		fruitRepository.saveAll(values.stream().map(p->mapToEntity(p, new Fruit())).collect(Collectors.toList()));
		
	}

	public List<FruitDTO> filterRecords(@Valid FruitDTO filter) {
		// TODO Auto-generated method stub 
		return fruitRepository.findByNameOrQuantity(filter.getName(),filter.getQuantity()).stream()
                .map(fruit -> mapToDTO(fruit, new FruitDTO()))
                .collect(Collectors.toList()); 
	}

}
