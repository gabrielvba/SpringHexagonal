package com.github.gabrielvba.ms_order_management.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonReader {

    public <T> T readFromJson(String pathToJsonFile, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
//        // Ativar ou desativar a formatação para a serialização de data e hora
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.readValue(new File(pathToJsonFile), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + e.getMessage(), e);
        }
    }
}
