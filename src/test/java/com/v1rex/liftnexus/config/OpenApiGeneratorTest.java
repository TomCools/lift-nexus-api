package com.v1rex.liftnexus.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    properties = {
      "springdoc.api-docs.enabled=true",
      "springdoc.use-management-port=false", // Forces it onto the main server context
      "springdoc.api-docs.path=/v3/api-docs" // Forces the default path
    })
@AutoConfigureMockMvc
@Import({
  TestContainersConfiguration.class,
  TimefoldTestConfig.class,
})
@ActiveProfiles("test")
@DisplayName("OpenAPI Spec Generator")
class OpenApiGeneratorTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void generateOpenApiSpec() throws Exception {
    String json =
        mockMvc
            .perform(get("/v3/api-docs"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    Path output = Path.of("target", "openapi.json");
    Files.writeString(output, json);
  }
}
