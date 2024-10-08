package carpelune.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import carpelune.dto.CreateProductDTO;
import carpelune.models.Product;
import carpelune.repositories.ProductsRepository;

@Service
public class ProductsService {


	private Logger logger = Logger.getLogger(ProductsService.class.getName());
		
	@Autowired
	private ProductsRepository productsRepository;
		
		
	public ResponseEntity<Product> createProduct(CreateProductDTO createProductDTO) {
		this.logger.log(Level.INFO, "Iniciando criação de produto");
	
		try {
			if(createProductDTO.name() == null) {
				this.logger.log(Level.WARNING, "nome do produto não informado");
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
			}
				
			if(createProductDTO.price() == null) {
				this.logger.log(Level.WARNING, "preço do produto não informado");
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
			}
				
			Product newProduct = new Product(createProductDTO.name(), createProductDTO.price(), createProductDTO.description());
				
			this.logger.log(Level.WARNING, "Criando novo produto no banco");
			newProduct = this.productsRepository.save(newProduct);
				
			return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
		}
		catch(Exception error) {
			this.logger.log(Level.SEVERE, "Erro ao criar novo produto. Error: " + error.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}
}
