
package pl.kurs.geometricshapes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.geometricshapes.GeometricShapesApplication;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql("/ShapeControllerTest.sql")
@Sql(value = "/clean_database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = {GeometricShapesApplication.class, TestConfig.class})
@AutoConfigureMockMvc
class ShapeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createShape() throws Exception {
        CreateShapeCommand createShapeCommand = new CreateShapeCommand();
        createShapeCommand.setType("CIRCLE");
        createShapeCommand.setParameters(new double[]{5.0});

        mockMvc.perform(post("http://localhost:8080/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createShapeCommand)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("CIRCLE"))
                .andExpect(jsonPath("$.radius").value(5.0))
                .andExpect(jsonPath("$.area").value(78.53981633974483))
                .andExpect(jsonPath("$.perimeter").value(31.41592653589793));
    }

    @Test
    void updateShape() throws Exception {
        UpdateShapeCommand updateShapeCommand = new UpdateShapeCommand();
        updateShapeCommand.setId(1L);
        updateShapeCommand.setType("CIRCLE");
        updateShapeCommand.setParameters(new double[]{10.0});

        mockMvc.perform(put("http://localhost:8080/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateShapeCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("CIRCLE"))
                .andExpect(jsonPath("$.radius").value(10.0)) //we expect that radius was updated
                .andExpect(jsonPath("$.area").value(314.1592653589793)) //area and perimeter should be calculated according to new radius
                .andExpect(jsonPath("$.perimeter").value(62.83185307179586));
    }


    @Test
    void getShapesByType() throws Exception {
        String shapeType = "SQUARE";

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/" + shapeType)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].area").value(100.0))
                .andExpect(jsonPath("$[0].perimeter").value(40.0))
                .andExpect(jsonPath("$[0].width").value(10))
                .andExpect(jsonPath("$[1].type").value("SQUARE"))
                .andExpect(jsonPath("$[1].area").value(400.0))
                .andExpect(jsonPath("$[1].perimeter").value(80.0))
                .andExpect(jsonPath("$[1].width").value(20));
    }

    @Test
    void getShapeByAreaBetweenMinAndMax() throws Exception {
        double areaFrom = 50.0;
        double areaTo = 120.0;

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/area-range")
                .param("areafrom", String.valueOf(areaFrom))
                .param("areato", String.valueOf(areaTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].width").value(10))
                .andExpect(jsonPath("$[0].area").value(100.0))
                .andExpect(jsonPath("$[1].type").value("CIRCLE"))
                .andExpect(jsonPath("$[1].radius").value(5.0))
                .andExpect(jsonPath("$[1].area").value(78.53981633974483));


    }

    @Test
    void getShapeByPerimeterBetweenMinAndMax() throws Exception {
        double perimeterFrom = 30.0;
        double perimeterTo = 90.0;

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/perimeter-range")
                .param("perimeterfrom", String.valueOf(perimeterFrom))
                .param("perimeterto", String.valueOf(perimeterTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].width").value(10))
                .andExpect(jsonPath("$[0].perimeter").value(40.0))
                .andExpect(jsonPath("$[1].type").value("SQUARE"))
                .andExpect(jsonPath("$[1].width").value(20))
                .andExpect(jsonPath("$[1].perimeter").value(80.0));
    }


    @Test
    void findAllByCreatedAtBetween() throws Exception {
        LocalDate createdFrom = LocalDate.parse("2023-07-01");
        LocalDate createdTo = LocalDate.parse("2023-07-01");

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/created-date-range")
                .param("createdfrom", createdFrom.toString())
                .param("createdto", createdTo.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].width").value(10))
                .andExpect(jsonPath("$[0].area").value(100.0))
                .andExpect(jsonPath("$[0].perimeter").value(40.0));
    }


    @Test
    void findAllByCreatedBy() throws Exception {
        String createdBy = "roko";

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/createdby")
                .param("createdby", createdBy)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].width").value(10))
                .andExpect(jsonPath("$[0].area").value(100.0))
                .andExpect(jsonPath("$[0].perimeter").value(40.0));
    }

    @Test
    void findAllCircleByRadiusBetween() throws Exception {
        double radiusFrom = 5.0;
        double radiusTo = 10.0;

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/circle-parameter-range")
                .param("radiusfrom", String.valueOf(radiusFrom))
                .param("radiusto", String.valueOf(radiusTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("CIRCLE"))
                .andExpect(jsonPath("$[0].radius").value(5.0))
                .andExpect(jsonPath("$[0].area").value(78.53981633974483))
                .andExpect(jsonPath("$[0].perimeter").value(31.41592653589793));
    }

    @Test
    void findAllSquareByWidthBetween() throws Exception {
        double widthFrom = 5.0;
        double widthTo = 20.0;

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/square-parameter-range")
                .param("widthfrom", String.valueOf(widthFrom))
                .param("widthto", String.valueOf(widthTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].width").value(10))
                .andExpect(jsonPath("$[0].area").value(100.0))
                .andExpect(jsonPath("$[0].perimeter").value(40.0))
                .andExpect(jsonPath("$[1].type").value("SQUARE"))
                .andExpect(jsonPath("$[1].width").value(20))
                .andExpect(jsonPath("$[1].area").value(400.0))
                .andExpect(jsonPath("$[1].perimeter").value(80.0));
    }

    @Test
    void findAllRectangleByWidthBetweenAndAndLengthBetween() throws Exception {
        double widthFrom = 5.0;
        double widthTo = 20.0;
        double lengthFrom = 10.0;
        double lengthTo = 30.0;

        mockMvc.perform(get("http://localhost:8080/api/v1/shapes/rectangle-parameter-range")
                .param("widthfrom", String.valueOf(widthFrom))
                .param("widthto", String.valueOf(widthTo))
                .param("lengthfrom", String.valueOf(lengthFrom))
                .param("lengthto", String.valueOf(lengthTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("RECTANGLE"))
                .andExpect(jsonPath("$[0].width").value(10.0))
                .andExpect(jsonPath("$[0].length").value(20.0))
                .andExpect(jsonPath("$[0].area").value(200.0))
                .andExpect(jsonPath("$[0].perimeter").value(60.0));
    }


}

