package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Platform;
import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import com.example.star_wars_project.repository.PlatformRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PlatformServiceImplTest {

    private PlatformServiceImpl platformService;

    @Mock
    private PlatformRepository platformRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        platformService = new PlatformServiceImpl(platformRepository);
    }

    @Test
    void testInitPlatforms() {
        when(platformRepository.count()).thenReturn(0L);

        platformService.initPlatforms();

        verify(platformRepository, times(PlatformNameEnum.values().length)).save(any(Platform.class));
    }
}
