package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.StoreRequest;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Store;
import com.enigma.sopimart.repository.StoreRepository;
import com.enigma.sopimart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    @Override
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store getById(String id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store update(Store store) {
        Store currentStoreId = getById(store.getId());
        if (currentStoreId != null){
            return storeRepository.save(store);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreResponse create(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
        storeRepository.save(store);

        return StoreResponse.builder()
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .build();
    }
}
