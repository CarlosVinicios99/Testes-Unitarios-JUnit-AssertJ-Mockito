package carpelune.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import carpelune.dto.CreateProductDTO;
import carpelune.models.Product;
import carpelune.repositories.ProductsRepository;

public class ProductsServiceTest {
	
	@Mock
	ProductsRepository productsRepository;
	
	@InjectMocks
	ProductsService productsService;
	
	@BeforeEach
	public void setUp() {
	   MockitoAnnotations.openMocks(this); 
	}
	
	@Test
	public void testCreateProductCase1() {
        
	    CreateProductDTO createProductDTO = new CreateProductDTO(
	    	"Bermuda Jeans Masculina", 
	    	44.90, 
	    	"Bermuda Jeans Masculina adulto, vários tamanhos"
	    );
	    
	    Product savedProduct = new Product(
	    	UUID.randomUUID(),
	    	"Bermuda Jeans Masculina", 
	    	44.90, 
	    	"Bermuda Jeans Masculina adulto, vários tamanhos"
	    );
	    
	    when(productsRepository.save(any(Product.class))).thenReturn(savedProduct);

	    ResponseEntity<Product> response = productsService.createProduct(createProductDTO);

	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	    assertThat(response.getBody()).isNotNull();
	    assertThat(response.getBody().getName()).isEqualTo("Bermuda Jeans Masculina");
	    assertThat(response.getBody().getPrice()).isEqualTo(44.90);
	    assertThat(response.getBody().getDescription()).isEqualTo("Bermuda Jeans Masculina adulto, vários tamanhos");
	        
	    verify(productsRepository, times(1)).save(any(Product.class));
	    
	}
	
}
