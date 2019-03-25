package tacos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProperties {
	@Min(value=5, message="This property mast be more then 5")
	@Max(value=25, message="This property mast be less then 25")
	private int pageCount = 20;
}
