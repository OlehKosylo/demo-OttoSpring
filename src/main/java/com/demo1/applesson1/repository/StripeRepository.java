package com.demo1.applesson1.repository;

import com.demo1.applesson1.models.Stripe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StripeRepository extends JpaRepository<Stripe, Integer> {

}
