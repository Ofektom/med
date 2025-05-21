package com.ofektom.med.serviceImpl;

import com.ofektom.med.dto.request.AssetDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.AssetResponse;
import com.ofektom.med.model.Asset;
import com.ofektom.med.repositroy.AssetRepository;
import com.ofektom.med.repositroy.UserRepository;
import com.ofektom.med.service.AssetService;
import com.ofektom.med.exception.NotFoundException;
import com.ofektom.med.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetRepository assetsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> addAsset(AssetDto assetDto) {
        Asset asset = new Asset();
        asset.setName(assetDto.getName());
        asset.setQuantity(assetDto.getQuantity());
        asset.setCondition(assetDto.getCondition());
        asset.setStatus(assetDto.getStatus());
        asset.setDescription(assetDto.getDescription());

        if (assetDto.getManagedById() != null) {
            Optional<User> user = userRepository.findById(assetDto.getManagedById());
            user.ifPresent(asset::setManagedBy);
        }

        asset = assetsRepository.save(asset);

        AssetResponse data = getAssetResponse(asset);
        return ResponseEntity.ok(new ApiResponse<>(200, "Asset added successfully", null, data));
    }

    @Override
    public ResponseEntity<?> getAssetById(UUID id) {
        Asset asset = assetsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asset not found"));
        AssetResponse data = getAssetResponse(asset);
        return ResponseEntity.ok(new ApiResponse<>(200, "Asset fetched successfully", null, data));
    }

    @Override
    public List<AssetResponse> getAllAssetsAsList() {
        List<Asset> assets = assetsRepository.findAll();
        return assets.stream().map(this::getAssetResponse).collect(Collectors.toList());
    }

    @Override
    public Page<AssetResponse> getAllAssetsAsPage(Pageable pageable) {
        Page<Asset> assetsPage = assetsRepository.findAll(pageable);
        return assetsPage.map(this::getAssetResponse);
    }

    private AssetResponse getAssetResponse(Asset asset) {
        return new AssetResponse(
                asset.getId(),
                asset.getName(),
                asset.getQuantity(),
                asset.getCondition(),
                asset.getStatus(),
                asset.getDescription(),
                asset.getManagedBy() != null ? asset.getManagedBy().getId() : null
        );
    }
}