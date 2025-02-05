package com.akentech.microservices.order.client;

import com.akentech.microservices.inventory.dto.InventoryRequest;
import com.akentech.microservices.inventory.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "http://localhost:8086/api/inventory")
public interface InventoryClient {

    @PostMapping("/check")
    InventoryResponse checkInventory(@RequestBody InventoryRequest inventoryRequest);
}
