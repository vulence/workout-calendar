package com.vule.workoutcalendar.musclegroup.impl;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface MuscleGroupRepository extends ListCrudRepository<MuscleGroup, Integer> {

    @Query("""
       SELECT *
       FROM muscle_group
       WHERE lower(name) = lower(:name)
       """)
    MuscleGroup findByName(String name);
}
