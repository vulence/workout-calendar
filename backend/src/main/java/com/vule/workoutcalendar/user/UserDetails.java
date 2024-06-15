package com.vule.workoutcalendar.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserDetails {

    @Id private Integer id;
    @Min(30) private Integer weight;
    @Min(100) private Integer height;
    private String goals;
    @NotNull private Integer externalId;

    public UserDetails(Integer weight, Integer height, String goals, Integer externalId) {
        this.weight = weight;
        this.height = height;
        this.goals = goals;
        this.externalId = externalId;
    }

    public void setWeight(Integer weight) {
        if (weight < 30) {
            throw new IllegalArgumentException("Weight must be at least 30 kg.");
        }

        this.weight = weight;
    }

    public void setHeight(Integer height) {
        if (height < 100) {
            throw new IllegalArgumentException("Height must be at least 100 cm.");
        }

        this.height = height;
    }

    public void setExternalId(Integer externalId) {
        if (externalId == null || externalId <= 0) {
            throw new IllegalArgumentException("External ID must be a positive integer.");
        }

        this.externalId = externalId;
    }

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
