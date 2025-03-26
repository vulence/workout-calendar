package com.vule.workoutcalendar.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.catalina.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsTest {

    private UserDetails userDetails;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        userDetails = new UserDetails();
    }

    @AfterEach
    void tearDown() {
        userDetails = null;
        validator = null;
    }

    @Test
    void testValidUserDetails() {
        userDetails = new UserDetails(75, 175, "Gaining muscle", 5);
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidWeight() {
        userDetails = new UserDetails(5, 175, "Gaining muscle", 5);
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);
        assertEquals(1, violations.size());
        assertEquals("must be greater than or equal to 30", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidWeightSetter() {
        userDetails = new UserDetails();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDetails.setWeight(5));
        assertEquals("Weight must be at least 30 kg.", exception.getMessage());
    }

    @Test
    void testInvalidHeight() {
        userDetails = new UserDetails(75, 50, "Gaining muscle", 5);
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);
        assertEquals(1, violations.size());
        assertEquals("must be greater than or equal to 100", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidHeightSetter() {
        userDetails = new UserDetails();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDetails.setHeight(50));
        assertEquals("Height must be at least 100 cm.", exception.getMessage());
    }

    @Test
    void testNullExternalId() {
        userDetails = new UserDetails(75, 175, "Gaining muscle", null);
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testNullExternalIdSetter() {
        userDetails = new UserDetails();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDetails.setExternalId(null));
        assertEquals("External ID must be a positive integer.", exception.getMessage());
    }

    @Test
    void testNegativeExternalIdSetter() {
        userDetails = new UserDetails();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDetails.setExternalId(-1));
        assertEquals("External ID must be a positive integer.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(Integer weight1, Integer height1, String goals1, Integer externalId1,
                    Integer weight2, Integer height2, String goals2, Integer externalId2, boolean eq) {
        userDetails = new UserDetails(weight1, height1, goals1, externalId1);

        UserDetails userDetails2 = new UserDetails(weight2, height2, goals2, externalId2);
        assertEquals(eq, userDetails.equals(userDetails2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
                Arguments.of(70, 175, "Gaining muscle", 5, 70, 175, "Gaining muscle", 5, true),
                Arguments.of(70, 170, "none", 5, 70, 175, "none", 5, false)
        );
    }
}