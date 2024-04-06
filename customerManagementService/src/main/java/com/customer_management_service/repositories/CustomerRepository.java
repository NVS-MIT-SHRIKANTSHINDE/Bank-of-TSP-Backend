package com.customer_management_service.repositories;

import com.customer_management_service.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	Customer findByAadarNumber(String aadarNumber);

    Customer findByPhone(String phone);
    
 
}
