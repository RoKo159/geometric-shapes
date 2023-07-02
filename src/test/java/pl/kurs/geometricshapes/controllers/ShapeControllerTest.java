package pl.kurs.geometricshapes.controllers;

import org.json.JSONException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import pl.kurs.geometricshapes.commands.CreateCircleCommand;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.config.ModelMapperConfig;
import pl.kurs.geometricshapes.dto.CircleDto;
import pl.kurs.geometricshapes.dto.RectangleDto;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;
import pl.kurs.geometricshapes.models.*;
import pl.kurs.geometricshapes.services.CircleManagementServices;
import pl.kurs.geometricshapes.services.RectangleManagementServices;
import pl.kurs.geometricshapes.services.ShapeManagementServices;
import pl.kurs.geometricshapes.services.SquareManagementServices;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean_database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ShapeControllerTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    HttpHeaders headers = new HttpHeaders();

    ModelMapper modelMapper = new ModelMapper();

    private ModelMapperConfig modelMapperConfig = Mockito.mock(ModelMapperConfig.class);

    private CircleManagementServices circleServices = Mockito.mock(CircleManagementServices.class);

    private RectangleManagementServices rectangleServices = Mockito.mock(RectangleManagementServices.class);

    private SquareManagementServices squareServices = Mockito.mock(SquareManagementServices.class);

    private ShapeController shapeController = new ShapeController(modelMapperConfig, circleServices, rectangleServices, squareServices);


    @Before
    void init() {
        Map<ShapeType, ShapeManagementServices> shapeServicesMap = new HashMap<>();
        shapeServicesMap.put(ShapeType.CIRCLE, circleServices);
        shapeServicesMap.put(ShapeType.SQUARE, squareServices);
        shapeServicesMap.put(ShapeType.RECTANGLE, rectangleServices);

        CreateShapeCommand circle1 = mock(CreateShapeCommand.class);
        when(circle1.getType()).thenReturn(ShapeType.CIRCLE);
        when(circle1.getParameters()).thenReturn(new double[]{10.0});

        CreateShapeCommand circle2 = mock(CreateShapeCommand.class);
        when(circle2.getType()).thenReturn(ShapeType.CIRCLE);
        when(circle2.getParameters()).thenReturn(new double[]{20.0});

        CreateShapeCommand square = mock(CreateShapeCommand.class);
        when(square.getType()).thenReturn(ShapeType.SQUARE);
        when(square.getParameters()).thenReturn(new double[]{10.0});

        CreateShapeCommand rectangle = mock(CreateShapeCommand.class);
        when(rectangle.getType()).thenReturn(ShapeType.RECTANGLE);
        when(rectangle.getParameters()).thenReturn(new double[]{10.0, 20.0});

        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);

        List<CreateShapeCommand> createShapeCommandList = Arrays.asList(circle1, circle2, square, rectangle);
    }

    private URI createServerAddress(String uri) throws URISyntaxException {
        return new URI("http://localhost:" + serverPort + uri);
    }

    @Test
    public void testShouldReturnExactlySameDataFromInsertDataSQl() throws URISyntaxException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(createServerAddress("/api/v1/shapes/circle"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"id\":1,\"type\":\"CIRCLE\",\"version\":v1,\"createdBy\":roko,\"createdAt\":\"2023-07-01\",\"lastModifiedAt\":2023-07-01,\"lastModifiedBy\":roko,\"area\":314.1592653589793,\"perimeter\":62.83185307179586,\"radius\":10.0}, {\"id\":2,\"type\":\"CIRCLE\",\"version\":\"v1\",\"createdBy\":\"roko\",\"createdAt\":\"2023-07-01\",\"lastModifiedAt\":\"2023-07-01\",\"lastModifiedBy\":\"roko\",\"area\":1256.6370614359173,\"perimeter\":125.66370614359172,\"radius\":20.0}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    @Test
    void shouldCheckTheReturnCodeAndTheCorrespondingBody() {

        // Given
        CircleDto circleDto = new CircleDto();
        CreateShapeCommand createShapeCommand = mock(CreateShapeCommand.class);
        when(createShapeCommand.getType()).thenReturn(ShapeType.CIRCLE);
        when(createShapeCommand.getParameters()).thenReturn(new double[]{10.0});
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);

        // When
        ResponseEntity<ShapesDto> result = shapeController.createShape(createShapeCommand);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(circleDto.getClass(), result.getBody().getClass());
    }

    @Test
    void getShapesByType() {

        // Given
        Circle circle1 = new Circle();
        Circle circle2 = new Circle();
        List<Circle> circleList = Arrays.asList(circle1, circle2);
        ModelMapper modelMapper = new ModelMapper();

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(circleServices.getAll()).thenReturn(circleList);

        // When
        ResponseEntity<List<ShapesDto>> result = shapeController.getShapesByType("CIRCLE");

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(CircleDto.class, result.getBody().get(0).getClass());
    }


    @Test
    void getShapeByAreaBetweenMinAndMax() {

        // Given
        double areaFrom = 10.0;
        double areaTo = 55.0;

        Circle circle1 = new Circle(1L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 3.0);
        Circle circle2 = new Circle(2L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 4.0);
        Circle circle3 = new Circle(2L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 100.0);
        List<Circle> circleList = Arrays.asList(circle1, circle2, circle3);

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(circleServices.findAllByAreaBetween(areaFrom, areaTo)).thenReturn(circleList);

        // When
        ResponseEntity<List<ShapesDto>> result = shapeController.getShapeByAreaBetweenMinAndMax(areaFrom, areaTo);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(CircleDto.class, result.getBody().get(0).getClass());
    }

    @Test
    void getShapeByPerimeterBetweenMinAndMax() {

        // Given
        double perimeterFrom = 10.0;
        double perimeterTo = 20.0;

        Circle circle1 = new Circle(1L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 3.0);
        Circle circle2 = new Circle(2L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 4.0);
        List<Circle> circleList = Arrays.asList(circle1, circle2);

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(circleServices.findAllByPerimeterBetween(perimeterFrom, perimeterTo)).thenReturn(circleList);

        // When
        ResponseEntity<List<ShapesDto>> result = shapeController.getShapeByPerimeterBetweenMinAndMax(perimeterFrom, perimeterTo);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(CircleDto.class, result.getBody().get(0).getClass());
    }

    @Test
    void findAllByCreatedAtBetween() {

        // Given
        LocalDate createdFrom = LocalDate.of(2023, 1, 1);
        LocalDate createdTo = LocalDate.of(2023, 12, 31);

        Circle circle1 = new Circle(1L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 3.0);
        Circle circle2 = new Circle(2L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 4.0);
        List<Circle> circleList = Arrays.asList(circle1, circle2);

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(circleServices.findAllByCreatedAtBetween(createdFrom, createdTo)).thenReturn(circleList);

        // When
        ResponseEntity<List<ShapesDto>> result = shapeController.findAllByCreatedAtBetween(createdFrom, createdTo);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(CircleDto.class, result.getBody().get(0).getClass());
    }

    @Test
    void findAllByCreatedBy() {

        // Given
        String createdBy = "testUser";

        Circle circle1 = new Circle(1L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), createdBy, 3.0);
        Circle circle2 = new Circle(2L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), createdBy, 4.0);
        List<Circle> circleList = Arrays.asList(circle1, circle2);

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(circleServices.findAllByCreatedBy(createdBy)).thenReturn(circleList);

        // When
        ResponseEntity<List<ShapesDto>> result = shapeController.findAllByCreatedBy(createdBy);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(CircleDto.class, result.getBody().get(0).getClass());
    }

    @Test
    void findAllCircleByRadiusBetween() {

        // Given
        double radiusFrom = 2.0;
        double radiusTo = 5.0;

        Circle circle1 = new Circle(1L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 3.0);
        Circle circle2 = new Circle(2L, ShapeType.CIRCLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 4.0);
        List<Circle> circleList = Arrays.asList(circle1, circle2);

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(circleServices.findAllByRadiusBetween(radiusFrom, radiusTo)).thenReturn(circleList);

        // When
        ResponseEntity<List<CircleDto>> result = shapeController.findAllCircleByRadiusBetween(radiusFrom, radiusTo);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(CircleDto.class, result.getBody().get(0).getClass());
    }


    @Test
    void findAllSquareByWidthBetween() {

        // Given
        double widthFrom = 2.0;
        double widthTo = 5.0;

        Square square1 = new Square(1L, ShapeType.SQUARE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 3.0);
        Square square2 = new Square(2L, ShapeType.SQUARE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 4.0);
        List<Square> squareList = Arrays.asList(square1, square2);

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(squareServices.findAllByWidthBetween(widthFrom, widthTo)).thenReturn(squareList);

        // When
        ResponseEntity<List<SquareDto>> result = shapeController.findAllSquareByWidthBetween(widthFrom, widthTo);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(SquareDto.class, result.getBody().get(0).getClass());
    }


    @Test
    void findAllRectangleByWidthBetween() {

        // Given
        double widthFrom = 2.0;
        double widthTo = 5.0;
        double lengthFrom = 4.0;
        double lengthTo = 7.0;

        Rectangle rectangle1 = new Rectangle(1L, ShapeType.RECTANGLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 3.0, 5.0);
        Rectangle rectangle2 = new Rectangle(2L, ShapeType.RECTANGLE, "1.0", "test", LocalDate.now(), LocalDate.now(), "test", 4.0, 6.0);
        List<Rectangle> rectangleList = Arrays.asList(rectangle1, rectangle2);

        // Mock behaviors
        when(modelMapperConfig.modelMapper()).thenReturn(modelMapper);
        when(rectangleServices.findAllByWidthBetweenAndAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo)).thenReturn(rectangleList);

        // When
        ResponseEntity<List<RectangleDto>> result = shapeController.findAllRectangleByWidthBetween(widthFrom, widthTo, lengthFrom, lengthTo);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(RectangleDto.class, result.getBody().get(0).getClass());
    }


}
