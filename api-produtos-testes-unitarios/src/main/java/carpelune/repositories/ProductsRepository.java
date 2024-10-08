package carpelune.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import carpelune.models.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
	
}
