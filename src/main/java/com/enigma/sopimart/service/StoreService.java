package com.enigma.sopimart.service;

import com.enigma.sopimart.dto.request.StoreRequest;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Store;

import java.util.List;

public interface StoreService {
    Store create (Store store);
    Store getById (String id);
    List<Store> getAll();
    Store update (Store store);
    void delete (String id);
    StoreResponse create (StoreRequest storeRequest);
}
