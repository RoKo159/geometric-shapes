package pl.kurs.geometricshapes.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.exceptions.BadNumberOfParametersExceptions;
import pl.kurs.geometricshapes.exceptions.NoShapeTypeException;
import pl.kurs.geometricshapes.models.Shape;
import pl.kurs.geometricshapes.repository.ShapeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ShapeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShapeRepository shapeRepository;

    @BeforeEach
    void setUp() {
        shapeRepository.deleteAll();
    }

    @Test
    public void shouldAddShape() throws Exception {
        CreateShapeCommand shapeCommand = new CreateShapeCommand();
        shapeCommand.setType("SQUARE");
        shapeCommand.setParameters(List.of(10.0));

        this.mockMvc.perform(post("/shapes")
                .content(objectMapper.writeValueAsString(shapeCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.version").value(0))
                .andExpect(jsonPath("$.type").value("SQUARE"));

        List<Shape> all = shapeRepository.findAll();
        assertThat(all.get(0)).extracting("width").isEqualTo(10.0);
    }

    @Test
    public void shouldThrowExceptionWhenTypeIsNotSupported() throws Exception {
        CreateShapeCommand shapeCommand = new CreateShapeCommand();
        shapeCommand.setType("NEW_SHAPE");
        shapeCommand.setParameters(List.of(5.0));

        this.mockMvc.perform(post("/shapes")
                .content(objectMapper.writeValueAsString(shapeCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ShapeType: NEW_SHAPE is not supported"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoShapeTypeException));
    }

    @Test
    public void shouldThrowExceptionWhenBadNumberParameters() throws Exception {
        CreateShapeCommand shapeCommand = new CreateShapeCommand();
        shapeCommand.setParameters(List.of(5.0));
        shapeCommand.setType("RECTANGLE");

        this.mockMvc.perform(post("/shapes")
                .content(objectMapper.writeValueAsString(shapeCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ShapeType: RECTANGLE should have 2 parameter(s)"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadNumberOfParametersExceptions));
    }


    @Test
    public void shouldGetOnlySquare() throws Exception {
        addShape("RECTANGLE", List.of(3.0, 5.0));
        addShape("CIRCLE", List.of(10.0));
        addShape("SQUARE", List.of(15.0));
        addShape("SQUARE", List.of(25.0));

        MvcResult mvcResult = this.mockMvc.perform(get("/shapes?type=SQUARE"))
                .andExpect(status().isOk())
                .andReturn();

        List<ShapeDto> squareShapes = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ShapeDto>>() {});
        assertEquals(2, squareShapes.size());
    }

    @Test
    public void shouldUpdateShape() throws Exception {
        addShape("RECTANGLE", List.of(40.0, 50.0));

        UpdateShapeCommand updateShapeCommand = new UpdateShapeCommand();
        updateShapeCommand.setId(shapeRepository.findAll().get(0).getId());
        updateShapeCommand.setType("RECTANGLE");
        updateShapeCommand.setParameters(List.of(10.0, 20.0));

        this.mockMvc.perform(put("/shapes")
                .content(objectMapper.writeValueAsString(updateShapeCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.width").value(10.0))
                .andExpect(jsonPath("$.length").value(20.0));
    }

    @Test
    public void shouldDeleteShape() throws Exception {
        addShape("RECTANGLE", List.of(40.0, 50.0));

        this.mockMvc.perform(delete("/shapes/5"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldThrowExceptionWhenObjectNotFound() throws Exception {
        this.mockMvc.perform(delete("/shapes/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Shape with id: 100 not found"))
                .andReturn();
    }

    private void addShape(String type, List<Double> parameters) throws Exception {
        CreateShapeCommand shapeCommand = new CreateShapeCommand();
        shapeCommand.setType(type);
        shapeCommand.setParameters(parameters);
        this.mockMvc.perform(post("/shapes").content(objectMapper.writeValueAsString(shapeCommand))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }
}