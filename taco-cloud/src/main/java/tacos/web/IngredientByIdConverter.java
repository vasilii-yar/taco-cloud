package tacos.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	private IngredientRepository ingRep;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ing) {
	    this.ingRep = ing;
	}
    
	@Override
	public Ingredient convert(String id) {
		return ingRep.findById(id);
	}
}
