package com.vule.workoutcalendar.musclegroup.api;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/muscle-groups")
public interface MuscleGroupControllerApi {

    List<MuscleGroup> findAll();

    MuscleGroup findById(Integer id);

    void create(MuscleGroup muscleGroup);

    void delete(Integer id);
}
