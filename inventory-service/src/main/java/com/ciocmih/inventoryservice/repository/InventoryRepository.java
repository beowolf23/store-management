package com.ciocmih.inventoryservice.repository;

import com.ciocmih.inventoryservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Item, Long> {
}
