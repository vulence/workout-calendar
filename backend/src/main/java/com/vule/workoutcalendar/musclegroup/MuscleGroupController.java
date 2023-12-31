package com.vule.workoutcalendar.musclegroup;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/muscleGroups")
@CrossOrigin
public class MuscleGroupController {
    private final MuscleGroupService muscleGroupService;

    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }
    @GetMapping("")
    @RequiresJwtToken
    public List<MuscleGroup> findAll() {
        return muscleGroupService.findAll();
    }

    @GetMapping("/{id}")
    @RequiresJwtToken
    public MuscleGroup findById(@PathVariable Integer id) {
        return muscleGroupService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    @RequiresJwtToken
    public void create(@Valid @RequestBody MuscleGroup muscleGroup) {
        muscleGroupService.create(muscleGroup);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public void delete(@PathVariable Integer id) {
        muscleGroupService.delete(id);
    }
}
