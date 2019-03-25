package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import tacos.Taco;

@Data
@Entity
@Table(name="Taco_Order")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Date createAt;
	@NotBlank(message="Name is required")
    private String name;
	@NotBlank(message="street is required")
    private String street;
	@NotBlank(message="city is required")
    private String city;
	@NotBlank(message="state is required")
    private String state;
	@NotBlank(message="zip code is required")
    private String zip;
	@CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
	@Pattern(regexp="^(0[1 - 9] | 1[0 - 2])([\\/])([1 - 9][0 - 9])$", message="Must be formated MM/YY")
    private String ccExpiration;
	@Digits(integer=3, fraction=0, message="Invalid CVV code")
    private String ccCVV;
	
	@ManyToOne
	private User user;
	
	@ManyToMany(targetEntity=Taco.class)
	private List<Taco> tacos = new ArrayList<>();
	public void addDesign(Taco taco) {
		this.tacos.add(taco);
	}
	
	@PrePersist
	void createAt() {
		this.createAt = new Date();
	}
}
