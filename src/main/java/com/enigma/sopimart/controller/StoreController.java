package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.dto.request.StoreRequest;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Store;
import com.enigma.sopimart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.STORE)
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public Store createStore(@RequestBody Store store){
        return storeService.create(store);
    }

    @GetMapping
    public List<Store> getAll(){
        return storeService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Store getById(@PathVariable String id){
        return storeService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        storeService.delete(id);
    }

    @PutMapping
    public Store update(@RequestBody Store store){
       return storeService.update(store);
    }

    @GetMapping(value = "/search{key}")
    public Store search(@RequestParam String key){
        return null;
    }

    @PostMapping(value = "/v1")
    public StoreResponse createStores (@RequestBody StoreRequest storeRequest){
        return storeService.create(storeRequest);
    }
}
