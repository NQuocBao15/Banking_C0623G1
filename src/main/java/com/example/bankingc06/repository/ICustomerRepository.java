package com.example.bankingc06.repository;

import com.example.bankingc06.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
//    @Modifying
//    @Query("UPDATE Customer c SET c.fullName = :fullName, c.email = :email, c.phone = :phone, c.address = :address, c.balance = :balance, c.deleted = :deleted WHERE c.id = :id")
//    void updateCustomerById(@Param("id") Long id, @Param("fullName") String fullName, @Param("email") String email, @Param("phone") String phone, @Param("address") String address, @Param("balance") BigDecimal balance, @Param("deleted") Boolean deleted);

//    List<Customer> findCustomersWithoutId();
}
