package ru.netology.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class PropertyLoader {

    @SneakyThrows(IOException.class)
    public String getProperty(String propertyName) {
        Properties instance = new Properties();
        InputStream resourceStream = PropertyLoader.class.getResourceAsStream("/application.properties");
        InputStreamReader inputStream = new InputStreamReader(Objects.requireNonNull(resourceStream), UTF_8);
        instance.load(inputStream);
        return instance.getProperty(propertyName);
    }
}
