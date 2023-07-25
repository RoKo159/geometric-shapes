package pl.kurs.geometricshapes.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kurs.geometricshapes.GeometricShapesApplication;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;
import pl.kurs.geometricshapes.models.Square;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GeometricShapesApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ShapeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public void rollback() {
        TestTransaction.end();
    }

    @AfterEach
    public void cleanup() {
        rollback();
    }

    @Test
    public void shouldCreateShapeAndReturnStatusIsCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shapes/square"))
                .andExpect(status().isNotFound());

        CreateShapeCommand square = new CreateShapeCommand();
        square.setParameters(new double[]{5.0});
        square.setType("SQUARE");

        String json = objectMapper.writeValueAsString(square);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shapes/parameters?type=SQUARE"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Square> responseObj = objectMapper.readValue(response, new TypeReference<>() {
        });
        Assertions.assertFalse(responseObj.isEmpty());
        Assertions.assertEquals("SQUARE", responseObj.get(0).getType());
        Assertions.assertEquals(5.0, responseObj.get(0).getWidth());
    }

    @Test
    public void shouldUpdateShapeAndReturnStatusIsOk() throws Exception {
        CreateShapeCommand square = new CreateShapeCommand();
        square.setParameters(new double[]{5.0});
        square.setType("SQUARE");

        String json = objectMapper.writeValueAsString(square);

        MvcResult createResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        String createResponse = createResult.getResponse().getContentAsString();
        ShapesDto createdShapeDto = objectMapper.readValue(createResponse, SquareDto.class);

        UpdateShapeCommand updateCommand = new UpdateShapeCommand();
        updateCommand.setId(createdShapeDto.getId());
        updateCommand.setType("SQUARE");
        updateCommand.setParameters(new double[]{10.0});

        String updateJson = objectMapper.writeValueAsString(updateCommand);

        MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andReturn();

        String updateResponse = updateResult.getResponse().getContentAsString();
        ShapesDto updatedShapeDto = objectMapper.readValue(updateResponse, SquareDto.class);

        Assertions.assertEquals(createdShapeDto.getId(), updatedShapeDto.getId());
        Assertions.assertEquals("SQUARE", updatedShapeDto.getType());
        Assertions.assertEquals(100.0, updatedShapeDto.getArea(), 0.001);
    }

    @Test
    public void shouldGetShapeByCustomParametersAndReturnStatusOk() throws Exception {
        CreateShapeCommand square = new CreateShapeCommand();
        square.setParameters(new double[]{5.0});
        square.setType("SQUARE");

        String json = objectMapper.writeValueAsString(square);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shapes/parameters?type=SQUARE&areafrom=20&areato=30"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Square> responseObj = objectMapper.readValue(response, new TypeReference<>() {
        });
        Assertions.assertEquals("SQUARE", responseObj.get(0).getType());
        Assertions.assertEquals(25.0, responseObj.get(0).getArea());
    }

    @Test
    public void shouldGetShapeByCustomParametersAndReturnStatusNotFound() throws Exception {

        CreateShapeCommand square = new CreateShapeCommand();
        square.setParameters(new double[]{5.0});
        square.setType("SQUARE");

        String json = objectMapper.writeValueAsString(square);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shapes/parameters?type=SQUARE&areafrom=20&areato=30"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Square> responseObj = objectMapper.readValue(response, new TypeReference<>() {
        });
        Assertions.assertEquals("SQUARE", responseObj.get(0).getType());
        Assertions.assertEquals(25.0, responseObj.get(0).getArea());
    }
}