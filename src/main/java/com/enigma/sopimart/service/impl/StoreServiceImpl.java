package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.StoreRequest;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Store;
import com.enigma.sopimart.repository.StoreRepository;
import com.enigma.sopimart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public StoreResponse creates(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
       storeRepository.save(store);

        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .noSiup(store.getNoSiup())
                .address(store.getAddress())
                .mobilePhone(store.getMobilePhone())
                .build();
    }

    @Override
    public StoreResponse updateStore(StoreRequest storeRequest) {
        Store store = storeRepository.findById(storeRequest.getId()).orElse(null);
        if (store !=null) {
            store.setName(storeRequest.getName());
            store.setNoSiup(storeRequest.getNoSiup());
            store.setAddress(storeRequest.getAddress());
            store.setMobilePhone(storeRequest.getMobilePhone());
//            store = Store.builder()
//                    .id(storeRequest.getId())
//                    .name(storeRequest.getName())
//                    .noSiup(storeRequest.getNoSiup())
//                    .address(storeRequest.getAddress())
//                    .mobilePhone(storeRequest.getMobilePhone())
//                    .build();
            storeRepository.save(store);
            return StoreResponse.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .noSiup(store.getNoSiup())
                    .address(store.getAddress())
                    .mobilePhone(store.getMobilePhone())
                    .build();
        }
        return  null;

    }

    @Override
    public StoreResponse getByIds(String id) {
      Store store =  storeRepository.findById(id).orElse(null);

        if (store == null) {
            return null;
        }
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .noSiup(store.getNoSiup())
                .address(store.getAddress())
                .mobilePhone(store.getMobilePhone())
                .build();
    }

    @Override
    public List<StoreResponse> getAllStore() {
        List<Store> listStore = storeRepository.findAll();
        return listStore.stream().map(
                store -> StoreResponse.builder()
                        .id(store.getId())
                        .noSiup(store.getNoSiup())
                        .name(store.getName())
                        .address(store.getAddress())
                        .mobilePhone(store.getMobilePhone()).build()
        ).collect(Collectors.toList());
    }
}
