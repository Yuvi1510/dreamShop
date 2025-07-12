package com.yuvraj.main.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yuvraj.main.models.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

	Optional<Cart> findByUserId(Long userId);

}
