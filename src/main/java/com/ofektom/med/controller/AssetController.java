package com.ofektom.med.controller;

import com.ofektom.med.dto.request.AssetDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.AssetResponse;
import com.ofektom.med.dto.response.PaginatedResponse;
import com.ofektom.med.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {
    @Autowired
    private final AssetService assetsService;

    public AssetController(AssetService assetsService) {
        this.assetsService = assetsService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<?> addAsset(@Valid @RequestBody AssetDto assetDto) {
        return assetsService.addAsset(assetDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<?> getAssetById(@PathVariable("id") UUID id) {
        return assetsService.getAssetById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<?> getAllAssets(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page == null || size == null) {
            List<AssetResponse> assets = assetsService.getAllAssetsAsList();
            ApiResponse<List<AssetResponse>> response = new ApiResponse<>(
                    200,
                    "Full list of assets retrieved successfully",
                    null,
                    assets
            );
            return ResponseEntity.ok(response);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<AssetResponse> assetsPage = assetsService.getAllAssetsAsPage(pageable);
            PaginatedResponse<AssetResponse> paginatedData = new PaginatedResponse<>(
                    assetsPage.getNumber(),
                    assetsPage.getSize(),
                    assetsPage.getTotalPages(),
                    assetsPage.getTotalElements(),
                    assetsPage.getContent()
            );
            ApiResponse<PaginatedResponse<AssetResponse>> response = new ApiResponse<>(
                    200,
                    "Paginated list of assets retrieved successfully",
                    null,
                    paginatedData
            );
            return ResponseEntity.ok(response);
        }
    }
}