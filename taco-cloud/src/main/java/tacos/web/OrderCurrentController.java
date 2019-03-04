package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tacos.Order;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderCurrentController {
    @GetMapping("/current")
    public String orderForm (Model model) {
    	model.addAttribute("order", new Order());
    	return "orderForm";
    }
    @PostMapping
    public String processOrdering(Order order) {
    	log.info("Processing ordering: " + order);
    	return "redirect:/";
    } 
}
