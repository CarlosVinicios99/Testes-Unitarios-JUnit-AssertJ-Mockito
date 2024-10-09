package carpelune.services;

import java.util.UUID;
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
	
	
	public ResponseEntity<Product> findProductById(UUID productId){
		this.logger.log(Level.INFO, "Iniciando busca de produto por ID: " + productId);
		
		try {		
			if(productId == null) {
				this.logger.log(Level.WARNING, "ID do produto inválido!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			this.logger.log(Level.WARNING, "Buscando produto por ID no banco");
			Product searchedProduct = this.productsRepository.findById(productId).get();
			
			if(searchedProduct == null) {
				this.logger.log(Level.WARNING, "ID fornecido não corresponde a nenhum produto cadastrado!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(searchedProduct);
		}
		catch(Exception error) {
			this.logger.log(Level.SEVERE, "Erro ao buscar produto por ID. Error: " + error.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}
	
	
	/*//update
	public ResponseEntity<Product> updateProduct(){
		
	}
	*/
	
	public ResponseEntity<Void> deleteProductById(UUID productId){
		this.logger.log(Level.INFO, "Iniciando exclusão de produto por ID: " + productId);
		
		try {
			if(productId == null) {
				this.logger.log(Level.WARNING, "ID do produto inválido!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			this.logger.log(Level.WARNING, "Excluindo produto no Banco de dados");
			this.productsRepository.deleteById(productId);
			
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		catch(Exception error) {
			this.logger.log(Level.SEVERE, "Erro ao excluir produto por ID. Error: " + error.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}
}
