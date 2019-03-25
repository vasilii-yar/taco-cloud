package tacos.web;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import tacos.OrderProperties;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderCurrentController {
	private OrderRepository orderRepo;
	private OrderProperties orderProp;
	public OrderCurrentController(OrderRepository ordRep, OrderProperties prop) {
		this.orderRepo = ordRep;
		this.orderProp = prop;
	}
    @GetMapping("/current")
    public String orderForm (Model model, @AuthenticationPrincipal User user) {
    	//model.addAttribute("orders", new Order());
    	return "orderForm";
    }
    @PostMapping
    public String processOrdering(@Valid Order order, Errors error, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
    	if (error.hasErrors()) {
    		return "orderForm";
    	}
    	order.setUser(user);
    	//log.info("Processing ordering: " + order);
    	orderRepo.save(order);
    	sessionStatus.setComplete();
    	return "redirect:/";
    } 
    
    @GetMapping
    public String ordersForUser(Model model, @AuthenticationPrincipal User user) {
    	Pageable pageble = PageRequest.of(0, orderProp.getPageCount());
    	model.addAttribute("orders", orderRepo.findByUserOrderByCreateAtDesc(user, pageble));
    	return "orderList";
    }
}
