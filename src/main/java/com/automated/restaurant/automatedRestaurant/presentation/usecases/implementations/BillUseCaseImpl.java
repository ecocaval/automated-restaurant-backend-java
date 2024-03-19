package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Bill;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.BIllRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.BillUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillUseCaseImpl implements BillUseCase {

    @Autowired
    private BIllRepository bIllRepository;

    @Override
    public List<Bill> findAllActiveBillsByRestaurantId(UUID restaurantId) {
        return this.bIllRepository.findAllActiveBillsByRestaurantId(restaurantId);
    }

    @Override
    public Optional<Bill> findByRestaurantTableAndActiveTrue(RestaurantTable restaurantTable) {
        return this.bIllRepository.findByRestaurantTableAndActiveTrue(restaurantTable);
    }

    @Override
    public Bill bindCustomerToBill(
            Customer customer,
            Optional<Bill> optionalBill,
            RestaurantTable restaurantTable
    ) {
        if (optionalBill.isEmpty()) {
            var newBill = Bill.builder()
                    .restaurantTable(restaurantTable)
                    .customers(List.of(customer))
                    .build();

            return this.bIllRepository.save(newBill);
        }

        var bill = optionalBill.get();

        bill.getCustomers().add(customer);

        return this.bIllRepository.save(bill);
    }
}
