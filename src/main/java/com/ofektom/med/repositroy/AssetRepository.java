package com.ofektom.med.repositroy;

import com.ofektom.med.model.Asset;
import com.ofektom.med.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
    Page<Asset> findByManagedBy(User managedBy, Pageable pageable);
}