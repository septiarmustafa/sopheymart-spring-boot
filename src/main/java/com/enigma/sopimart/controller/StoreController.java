package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.dto.request.StoreRequest;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.STORE)
public class StoreController {

    private final StoreService storeService;

    @PostMapping(value = "/v1")
    public StoreResponse createStores (@RequestBody StoreRequest storeRequest){
        return storeService.creates(storeRequest);
    }

    @PutMapping("/v1")
    public StoreResponse updateStore(@RequestBody StoreRequest storeRequest){
        return storeService.updateStore(storeRequest);
    }

    @GetMapping(value = "/v1/{id}")
    public StoreResponse getById(@PathVariable String id){
        return storeService.getByIds(id);
    }

    @GetMapping
    public List<StoreResponse> getAll(){
        return storeService.getAllStore();
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        storeService.delete(id);
    }
}
