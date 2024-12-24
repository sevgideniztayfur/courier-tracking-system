package com.example.couriertrackingsystem.provider;

import com.example.couriertrackingsystem.mapper.StoreMapper;
import com.example.couriertrackingsystem.model.Store;
import com.example.couriertrackingsystem.model.dto.StoreDto;
import com.example.couriertrackingsystem.service.StoreService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StoreProviderTest {

    @InjectMocks
    private StoreProvider storeProvider;
    @Mock
    private StoreService storeService;
    @Mock
    private StoreMapper storeMapper;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SneakyThrows
    void saveStores_ShouldCallSaveForEachStore() {
        //given
        StoreDto storeDto = new StoreDto("Store1", 40.0, 29.0);
        Store store = new Store(1L,"Store1", 40.0, 29.0);
        List<StoreDto> storeDtos = List.of(storeDto);
        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class)))
                .thenReturn(storeDtos);
        when(storeMapper.mapStoreFrom(storeDto)).thenReturn(store);

        //when
        storeProvider.saveStores();

        //then
        verify(storeService, times(1)).save(store);
        verify(storeMapper, times(1)).mapStoreFrom(storeDto);
    }

    @Test
    void readStoresFromJson_ShouldReturnListOfStoreDtos() throws IOException {
        //given
        List<StoreDto> expectedStores = List.of(new StoreDto("Store1", 40.0, 29.0));
        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class)))
                .thenReturn(expectedStores);

        //when
        List<StoreDto> actualStores = storeProvider.readStoresFromJson();

        //then
        assertEquals(expectedStores, actualStores);
        verify(objectMapper, times(1)).readValue(any(InputStream.class), any(TypeReference.class));
    }

    @Test
    void readStoresFromJson_ShouldThrowRuntimeExceptionOnIOException() throws IOException {
        //given
        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class)))
                .thenThrow(new IOException("Test IOException"));

        //when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> storeProvider.readStoresFromJson());
        assertEquals("Could not read stores.json file", exception.getMessage());
        verify(objectMapper, times(1)).readValue(any(InputStream.class), any(TypeReference.class));
    }
}
