package common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Data;

@Configuration
@Data
public class JsonReader {

    private final ObjectMapper mapper;

    public JsonReader() {
    	mapper = new ObjectMapper();
    	mapper.registerModule(new JavaTimeModule());
    	mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    	mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        SimpleModule simpleModule = new SimpleModule();
        mapper.registerModule(simpleModule);
    }
    
    public <T> T readFromJson(String resourcePath, Class<T> clazz) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Arquivo não encontrado no classpath: " + resourcePath);
            }
            return mapper.readValue(is, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + e.getMessage(), e);
        }
    }
    
    public String readJsonAsString(String resourcePath) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Arquivo não encontrado no classpath: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + e.getMessage(), e);
        }
    }
    
}
