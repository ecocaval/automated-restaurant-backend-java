package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.data.enums.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateTableRequest {

    @NotNull(message = "O id da mesa a ser atualizada não pode ser nulo.")
    private UUID id;

    private Boolean active;

    private String identification;

    private Long capacity;

    private TableStatus status;
}
