package carpelune.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carpelune.dto.CreateProductDTO;
import carpelune.dto.UpdateProductDTO;
import carpelune.models.Product;
import carpelune.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Serviços para manipulação de produtos")
public class ProductsController {
	
	@Autowired
	ProductsService productsService;
	
	@Operation(
		summary = "Cria uma novo produto"
	)
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO createProductDTO){
		return this.productsService.createProduct(createProductDTO);
	}
	
	@Operation(
		summary = "Busca um produto por ID"
	)
	@GetMapping("/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable UUID productId){
		return this.productsService.findProductById(productId);
	}
	
	@Operation(
		summary = "Atualiza um produto existente"
	)

	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody UpdateProductDTO updateProductDTO){
		return this.productsService.updateProduct(updateProductDTO);
	}
	
	@Operation(
		summary = "Excluí um produto por ID"
	)
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID productId){
		return this.productsService.deleteProductById(productId);
	}
	
}
