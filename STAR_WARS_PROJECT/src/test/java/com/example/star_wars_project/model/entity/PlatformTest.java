package com.example.star_wars_project.model.entity;

import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlatformTest {
    @Test
    public void testSetName() {
        Platform platform = new Platform();
        platform.setName(PlatformNameEnum.CONSOLE);
        Assertions.assertEquals(PlatformNameEnum.CONSOLE, platform.getName());
    }
}