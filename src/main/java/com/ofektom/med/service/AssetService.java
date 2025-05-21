package com.ofektom.med.service;

import com.ofektom.med.dto.request.AssetDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.AssetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AssetService {
    ResponseEntity<?> addAsset(AssetDto assetDto);
    ResponseEntity<?> getAssetById(UUID id);
    List<AssetResponse> getAllAssetsAsList();
    Page<AssetResponse> getAllAssetsAsPage(Pageable pageable);
}