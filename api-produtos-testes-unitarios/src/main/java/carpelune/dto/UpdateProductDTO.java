package carpelune.dto;

import java.util.UUID;

public record UpdateProductDTO(UUID productId, String name, Double price, String description) {

}
