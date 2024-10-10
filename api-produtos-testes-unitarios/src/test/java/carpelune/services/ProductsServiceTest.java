package carpelune.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import carpelune.dto.CreateProductDTO;
import carpelune.dto.UpdateProductDTO;
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
	
	@Test
	public void testCreateProductCase2() {
		
		CreateProductDTO createProductDTO = new CreateProductDTO(null, 50.00, "Boné masculino praiano, várias cores");
		
		ResponseEntity<Product> response = productsService.createProduct(createProductDTO);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(response.getBody()).isNull();
		
		verify(productsRepository, times(0)).save(any(Product.class));
	}
	
	@Test
	public void testCreateProductCase3() {
		
		CreateProductDTO createProductDTO = new CreateProductDTO("Boné de praia masculino", null, "Boné masculino praiano, várias cores");
		
		ResponseEntity<Product> response = productsService.createProduct(createProductDTO);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(response.getBody()).isNull();
		
		verify(productsRepository, times(0)).save(any(Product.class));
	}
	
	
	@Test
	public void testFindProductByIdCase1() {
		
		UUID productId = UUID.randomUUID();
		
		Product searchedProduct = new Product(
		    productId,
		    "Bermuda Jeans Masculina", 
		    44.90, 
		    "Bermuda Jeans Masculina adulto, vários tamanhos"
		);
		    
		when(productsRepository.findById(any(UUID.class))).thenReturn(Optional.of(searchedProduct));
		
		ResponseEntity<Product> response = productsService.findProductById(productId);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getProductId()).isEqualTo(productId);
		assertThat(response.getBody().getName()).isEqualTo(searchedProduct.getName());
		assertThat(response.getBody().getDescription()).isEqualTo(searchedProduct.getDescription());
		
		verify(productsRepository, times(1)).findById(any(UUID.class));
	}
	
	@Test
	public void testFindProductByIdCase2() {
		
		UUID productId = null;
		
		ResponseEntity<Product> response = productsService.findProductById(productId);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isNull();
		
		verify(productsRepository, times(0)).findById(any(UUID.class));
	}
	
	@Test
	public void testFindProductByIdCase3() {
		
		UUID productId = UUID.randomUUID();
		
		when(productsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		
		ResponseEntity<Product> response = productsService.findProductById(productId);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(response.getBody()).isNull();
		
		verify(productsRepository, times(1)).findById(any(UUID.class));
	}
	
	
	@Test
	public void testUpdateProductCase1() {
		
		UpdateProductDTO updateProductDTO = new 
			UpdateProductDTO(UUID.randomUUID(), "Camisa polo", 79.90, "Camisa Polo masculina adulto");
		
		Product updatedProduct = new Product(UUID.randomUUID(), "Camisa polo", 79.90, "Camisa Polo masculina adulto");
		
		when(productsRepository.findById(any(UUID.class))).thenReturn(Optional.of(updatedProduct));
		when(productsRepository.save(any(Product.class))).thenReturn(updatedProduct);

	    ResponseEntity<Product> response = productsService.updateProduct(updateProductDTO);
	    
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	    assertThat(response.getBody().getName()).isEqualTo(updatedProduct.getName());
	    assertThat(response.getBody().getPrice()).isEqualTo(updatedProduct.getPrice());
	    assertThat(response.getBody().getDescription()).isEqualTo(updatedProduct.getDescription());
	    
	    verify(productsRepository, times(1)).save(any(Product.class));
	    verify(productsRepository, times(1)).findById(any(UUID.class));
	    
	}
	
	@Test
	public void testUpdateProductCase2() {
		
		UpdateProductDTO updateProductDTO = new 
			UpdateProductDTO(null, "Camisa polo", 79.90, "Camisa Polo masculina adulto");

	    ResponseEntity<Product> response = productsService.updateProduct(updateProductDTO);
	    
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	    assertThat(response.getBody()).isNull();
	    
	    verify(productsRepository, times(0)).findById(any(UUID.class));
	    verify(productsRepository, times(0)).save(any(Product.class));
	}
	
	@Test
	public void testUpdateProductCase3() {
		
		UpdateProductDTO updateProductDTO = new 
			UpdateProductDTO(UUID.randomUUID(), null, 79.90, null);
		
		when(productsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

	    ResponseEntity<Product> response = productsService.updateProduct(updateProductDTO);
	    
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	    assertThat(response.getBody()).isNull();
	    
	    verify(productsRepository, times(1)).findById(any(UUID.class));
	    verify(productsRepository, times(0)).save(any(Product.class));
	}
	
	
	@Test
	public void test
	@Test
	
}
