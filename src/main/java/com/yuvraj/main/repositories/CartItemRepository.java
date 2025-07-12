package com.yuvraj.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yuvraj.main.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem , Long> {

}
