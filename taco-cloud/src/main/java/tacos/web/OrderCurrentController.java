package tacos.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderCurrentController {
	private OrderRepository orderRepo;
	public OrderCurrentController(OrderRepository ordRep) {
		this.orderRepo = ordRep;
	}
    @GetMapping("/current")
    public String orderForm (Model model) {
    	//model.addAttribute("order", new Order());
    	return "orderForm";
    }
    @PostMapping
    public String processOrdering(@Valid Order order, Errors error, SessionStatus sessionStatus) {
    	if (error.hasErrors()) {
    		return "orderForm";
    	}
    	//log.info("Processing ordering: " + order);
    	orderRepo.save(order);
    	sessionStatus.setComplete();
    	return "redirect:/";
    } 
}
