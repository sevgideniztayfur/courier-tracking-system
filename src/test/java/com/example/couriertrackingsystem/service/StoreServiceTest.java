package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.mapper.StoreMapper;
import com.example.couriertrackingsystem.model.Store;
import com.example.couriertrackingsystem.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreMapper storeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        //given
        Store store = new Store();
        store.setName("Test Store");

        //when
        storeService.save(store);

        //then
        verify(storeRepository, times(1)).save(store);
    }

    @Test
    void testGetAll() {
        //given
        Store store1 = new Store();
        store1.setName("Store 1");

        Store store2 = new Store();
        store2.setName("Store 2");

        List<Store> expectedStores = Arrays.asList(store1, store2);
        when(storeRepository.findAll()).thenReturn(expectedStores);

        //when
        List<Store> actualStores = storeService.getAll();

        //then
        assertEquals(expectedStores, actualStores);
        verify(storeRepository, times(1)).findAll();
    }
}

