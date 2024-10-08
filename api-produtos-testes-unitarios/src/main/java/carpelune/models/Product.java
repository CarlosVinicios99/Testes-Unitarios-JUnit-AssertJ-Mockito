package carpelune.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "product_id")
	private UUID productId;
	
	@Column
	private String name;
	
	@Column
	private Double price;
	
	@Column
	private String description;
	
	
	public Product() {
		
	}
	
	public Product(UUID productId, String name, Double price, String description) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	public Product(String name, Double price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}
	

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(description, name, price, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
			&& Objects.equals(price, other.price) && Objects.equals(productId, other.productId);
	}
	
}
