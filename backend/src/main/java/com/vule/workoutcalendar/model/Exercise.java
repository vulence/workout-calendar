package com.vule.workoutcalendar.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Exercise {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Set<MuscleGroupRef> muscleGroups = new HashSet<>();

    public Exercise() {}
    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Integer> getMuscleGroupIds() {
        return muscleGroups.stream().map(muscleGroupRef -> muscleGroupRef.getMuscleGroup().getId()).collect(Collectors.toSet());
    }

    public List<String> getMuscleGroupNames() {
        return muscleGroups.stream().map(muscleGroupRef -> muscleGroupRef.getName()).collect(Collectors.toList());
    }

    public void addMuscleGroup(MuscleGroup muscleGroup, String name) {
        MuscleGroupRef ref = new MuscleGroupRef();
        ref.muscleGroup = AggregateReference.to(muscleGroup.getId());
        ref.name = name;
        muscleGroups.add(ref);
    }

    public void removeMuscleGroup(MuscleGroup muscleGroup) {
        muscleGroups.remove(muscleGroup);
    }
}