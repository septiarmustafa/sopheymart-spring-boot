package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.StoreRequest;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Store;
import com.enigma.sopimart.repository.StoreRepository;
import com.enigma.sopimart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StoreServiceImplTest {

    // buat mock store respository
    private final StoreRepository storeRepository = mock(StoreRepository.class);

    // create store servicenya instance
    private final StoreService storeService = new StoreServiceImpl(storeRepository);


    @Test
    void itShouldReturnStoreWhenCreateNewStore() {
        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("123");
        dummyStoreRequest.setAddress("Jaya");

        StoreResponse dummyStoreResponse = storeService.creates(dummyStoreRequest);

        verify(storeRepository).save(any(Store.class));
        assertEquals(dummyStoreRequest.getAddress(), dummyStoreResponse.getAddress());
    }

    @Test
    void itShouldGetAllDataWhenCallGetAllStore() {
        List<Store> dummyStore = new ArrayList<>();
        dummyStore.add(new Store("1","1","Berkah", "Ragunan", "12"));
        dummyStore.add(new Store("2","12","Berkah selalu", "Ragunan baru", "123"));

        when(storeRepository.findAll()).thenReturn(dummyStore);
        List<StoreResponse> retriveStore = storeService.getAllStore();

        assertEquals(dummyStore.size(), retriveStore.size());

        for (int i = 0; i <  dummyStore.size(); i++) {
            assertEquals(dummyStore.get(i).getName(), retriveStore.get(i).getName());
        }

        for (int i = 0; i <  dummyStore.size(); i++) {
            assertEquals(dummyStore.get(i).getNoSiup(), retriveStore.get(i).getNoSiup());
        }
    }

    @Test
    void itShouldGetIdDataWhenCallGetByIds() {
        String id = "1";
        Store store = new Store("1","1","Berkah", "Ragunan", "12");

        when(storeRepository.findById(id)).thenReturn(Optional.of(store));

        StoreResponse storeResponse = storeService.getByIds(id);

        verify(storeRepository).findById(id);
        assertNotNull(storeResponse);
        assertEquals(id,storeResponse.getId());
        assertEquals("Berkah", storeResponse.getName());
    }
    @Test
    void itShouldDeleteDataWhenCallDeleteStore() {
        String id = "1";
        storeService.delete(id);
        verify(storeRepository, times(1)).deleteById(id);
    }

    @Test
    void itShouldUpdateDataWhenCallUpdateStore() {

        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("123");
        dummyStoreRequest.setNoSiup("11");
        dummyStoreRequest.setName("tes");
        dummyStoreRequest.setAddress("bogor");
        dummyStoreRequest.setMobilePhone("0899");

        Store existingStore = new Store("123","122","tes","bogor","0899");

        when(storeRepository.findById(dummyStoreRequest.getId())).thenReturn(Optional.of(existingStore));

        StoreResponse storeResponse = storeService.updateStore(dummyStoreRequest);

        verify(storeRepository, times(1)).findById(dummyStoreRequest.getId());

        verify(storeRepository, times(1)).save(existingStore);

        assertNotNull(storeResponse);
        assertEquals(dummyStoreRequest.getId(), storeResponse.getId());
        assertEquals(dummyStoreRequest.getName(), storeResponse.getName());
        assertEquals(dummyStoreRequest.getNoSiup(), storeResponse.getNoSiup());
        assertEquals(dummyStoreRequest.getAddress(), storeResponse.getAddress());
        assertEquals(dummyStoreRequest.getMobilePhone(), storeResponse.getMobilePhone());

    }



}