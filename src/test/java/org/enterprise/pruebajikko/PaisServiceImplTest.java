package org.enterprise.pruebajikko;

import org.enterprise.pruebajikko.model.repository.PaisRepository;
import org.enterprise.pruebajikko.model.service.PaisServiceImpl;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaisServiceImplTest {

    @InjectMocks
    PaisServiceImpl paisService;

    @Mock
    PaisRepository paisRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findPaisReturnsCorrectDataWhenCapitalExists() {
        Object[] paisData = new Object[]{"Colombia", "Peso", 50000000L, "Bogota", true};
        List<Object> objects = new ArrayList<>();
        objects.add(paisData);
        when(paisRepository.findById("Colombia")).thenReturn(objects);

        List<PaisDto> result = paisService.findPais("Colombia");

        assertTrue(!result.isEmpty());
    }

    @Test
    public void findPaisReturnsEmptyListWhenNoCapitalExists() {
        Object[] paisData = new Object[]{"Colombia", "Peso", 50000000L, "", false};
        List<Object> objects = new ArrayList<>();
        objects.add(paisData);
        when(paisRepository.findById("Colombia")).thenReturn(objects);

        List<PaisDto> result = paisService.findPais("Colombia");

        assertTrue(result.isEmpty());
    }
}