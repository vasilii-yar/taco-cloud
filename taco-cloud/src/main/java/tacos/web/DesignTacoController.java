package tacos.web;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.JdbcIngredientRepository;
import tacos.data.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	private final IngredientRepository ingRep;
	private TacoRepository tacoRep;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingRep, TacoRepository tacoRep) {
		this.ingRep = ingRep;
		this.tacoRep = tacoRep;
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingRep.findAll().forEach(i -> ingredients.add(i));		
				
				/*Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Type.WRAP), new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), new Ingredient("LETC", "Lettuce", Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Type.CHEESE), new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Type.SAUCE), new Ingredient("SRCR", "Sour Cream", Type.SAUCE));*/
		Type[] types = Ingredient.Type.values();
		for (Type t : types) {
			model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
		}
		model.addAttribute("design", new Taco());
		return "design";
	}
	
	@ModelAttribute(name="order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name="taco")
	public Taco taco() {
		return new Taco();
	}
	
	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			return "design";
		}
		Taco saved = tacoRep.save(taco);
		order.addDesign(saved);
		log.info("Processing design: " + taco);
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> l, Type t) {
		return l.stream().filter(x -> x.getType().equals(t)).collect(Collectors.toList());
	}
}
