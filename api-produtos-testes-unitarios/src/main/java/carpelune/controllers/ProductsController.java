package carpelune.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carpelune.dto.CreateProductDTO;
import carpelune.models.Product;
import carpelune.services.ProductsService;

@RestController
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	ProductsService productsService;
	
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO createProductDTO){
		return this.productsService.createProduct(createProductDTO);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable UUID productId){
		return this.productsService.findProductById(productId);
	}
	
	
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID productId){
		return this.productsService.deleteProductById(productId);
	}
	
}
