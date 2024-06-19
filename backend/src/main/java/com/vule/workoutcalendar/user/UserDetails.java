package com.vule.workoutcalendar.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

/**
 * Represents the details of a user.
 *
 * A user's details include his unique id, his weight, height, goals, and an external ID.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDetails {

    /**
     * A unique identifier of the user's details.
     */
    @Id private Integer id;

    /**
     * The weight of the user (in kg).
     */
    @Min(30) private Integer weight;

    /**
     * The height of the user (in cm).
     */
    @Min(100) private Integer height;

    /**
     * User's goals.
     */
    private String goals;

    /**
     * The user's external ID (ID from the Userfront service).
     */
    @NotNull private Integer externalId;

    /**
     * Creates new user details and sets weight, height, goals, and externalID to the input values.
     *
     * @param weight User's weight as an integer
     * @param height User's height as an integer
     * @param goals User's goals as a string
     * @param externalId User's external ID as an integer
     */
    public UserDetails(Integer weight, Integer height, String goals, Integer externalId) {
        this.weight = weight;
        this.height = height;
        this.goals = goals;
        this.externalId = externalId;
    }

    /**
     * Sets the weight (in kg) of the user to the input value
     *
     * The inputted weight cannot be null or less than 30
     *
     * @param weight User's weight as an integer
     *
     * @throws IllegalArgumentException if the weight is null or the entered weight is less than 30
     */
    public void setWeight(Integer weight) {
        if (weight == null || weight < 30) {
            throw new IllegalArgumentException("Weight must be at least 30 kg.");
        }

        this.weight = weight;
    }

    /**
     * Sets the height (in cm) of the user to the input value
     *
     * The inputted height cannot be null or less than 100
     *
     * @param height User's height as an integer
     *
     * @throws IllegalArgumentException if the height is null or the entered height is less than 100
     */
    public void setHeight(Integer height) {
        if (height == null || height < 100) {
            throw new IllegalArgumentException("Height must be at least 100 cm.");
        }

        this.height = height;
    }

    /**
     * Sets the external ID of the user to the input value
     *
     * The inputted external ID cannot be null or less than 0
     *
     * @param externalId User's external ID as an integer
     *
     * @throws IllegalArgumentException If the externalID is null or the entered externalID is less than 0
     */
    public void setExternalId(Integer externalId) {
        if (externalId == null || externalId <= 0) {
            throw new IllegalArgumentException("External ID must be a positive integer.");
        }

        this.externalId = externalId;
    }

    /**
     * Compares two user details according to their weights, heights, goals, and external IDs.
     *
     * @param o The second user details to be compared with
     *
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the UserDetails class,
     *     and have the same weights, heights, goals, and externalIds</li>
     *     <li><b>false</b> - if objects don't belong to the UserDetails class, if the inputted object is null
     *     or if they don't have the same weights, heights, goals, and externalIds</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(weight, that.weight) && Objects.equals(height, that.height) && Objects.equals(goals, that.goals) && Objects.equals(externalId, that.externalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, height, goals, externalId);
    }
}
