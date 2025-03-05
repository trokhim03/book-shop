package mate.academy.bookshop.dto.order;

import jakarta.validation.constraints.NotBlank;

public record OrderUpdateStatusDto(@NotBlank String status) {
}
