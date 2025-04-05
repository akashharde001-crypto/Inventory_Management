package com.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inventory.entity.InventoryEntity;

@Repository
public interface InventoryRepostory extends JpaRepository<InventoryEntity, Long> {
}




//package com.inventory.repository;
//
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.inventory.entity.InventoryEntity;
//
//@Repository
//public interface InventoryRepostory extends JpaRepository<InventoryEntity, Long>{
//
//}
