package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.model.entity.Time;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class MissionStepTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @Test
    void testHomePageReturn() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .contentType(ContentType.HTML)
                .statusCode(200);
    }

    @Test
    void testAccessReservationPageAndGetReservationList() {

        RestAssured.given()
                .when().log().all()
                .get("/reservation")
                .then()
                .log().all()
                .contentType(ContentType.HTML)
                .statusCode(200);

        RestAssured.given().log().all()
                .get("/reservations")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(0));
    }


    @Test
    void testExceptionHandling() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        // 필요한 인자가 없는 경우
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void testDatabaseConnection() {

        SoftAssertions softly = new SoftAssertions();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            softly.assertThat(connection).isNotNull();
            softly.assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            softly.assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
            softly.assertAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testTimeAddGetDelete() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1");

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void testTimeValueIsNotStringt() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("날짜/시간 중복 예약시 예외 발생 테스트")
    void duplicatedReservationFail() {
        LocalDate date = LocalDate.of(2025, 6, 27);
        long timeId = 1L;
        Time time = Time.builder()
                .id(timeId)
                .time(LocalTime.of(10, 0))
                .build();

    }

    @Test
    void testLayeredSeparation() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }
        assertThat(isJdbcTemplateInjected).isFalse();
    }


}


