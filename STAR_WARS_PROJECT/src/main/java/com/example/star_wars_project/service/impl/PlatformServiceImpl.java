package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Platform;
import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import com.example.star_wars_project.repository.PlatformRepository;
import com.example.star_wars_project.service.PlatformService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PlatformServiceImpl implements PlatformService {
    private final PlatformRepository platformRepository;

    public PlatformServiceImpl(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Override
    public void initPlatforms() {
        if (platformRepository.count() > 0) {
            return;
        }

        Arrays.stream(PlatformNameEnum.values()).forEach(platformNameEnum -> {
            Platform platform = new Platform();
            platform.setName(platformNameEnum);
            platformRepository.save(platform);
        });
    }
}