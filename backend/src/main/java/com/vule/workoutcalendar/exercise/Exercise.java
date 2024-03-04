package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.musclegroup.MuscleGroupRef;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Exercise {

    @Id @Getter @Setter private Integer id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private String imageUrl;

    private final Set<MuscleGroupRef> muscleGroups = new HashSet<>();

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Set<Integer> getMuscleGroupIds() {
        return muscleGroups.stream().map(muscleGroupRef -> muscleGroupRef.getMuscleGroup().getId()).collect(Collectors.toSet());
    }

    public List<String> getMuscleGroupNames() {
        return muscleGroups.stream().map(MuscleGroupRef::getName).collect(Collectors.toList());
    }

    public void addMuscleGroup(MuscleGroup muscleGroup, String name) {
        MuscleGroupRef ref = new MuscleGroupRef();
        ref.setMuscleGroup(AggregateReference.to(muscleGroup.getId()));
        ref.setName(name);

        muscleGroups.add(ref);
    }

    public void removeMuscleGroup(MuscleGroup muscleGroup) {
        muscleGroups.removeIf(mg -> Objects.equals(mg.getMuscleGroup().getId(), muscleGroup.getId()));
    }
}