package com.vule.workoutcalendar.musclegroup.impl;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MuscleGroupServiceTest {

    @MockBean
    private MuscleGroupRepository muscleGroupRepository;

    @Autowired
    private MuscleGroupService muscleGroupService;

    @Test
    void testFindAll() {
        MuscleGroup muscleGroup1 = new MuscleGroup("Chest", "Pectoralis major and minor");
        MuscleGroup muscleGroup2 = new MuscleGroup("Biceps", "Short and long head");
        List<MuscleGroup> muscleGroupList = Arrays.asList(muscleGroup1, muscleGroup2);

        when(muscleGroupService.findAll()).thenReturn(muscleGroupList);

        List<MuscleGroup> result = muscleGroupService.findAll();
        assertEquals(2, result.size());
        assertEquals(muscleGroup1, result.get(0));
        assertEquals(muscleGroup2, result.get(1));
        verify(muscleGroupRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdIfItExists() {
        Integer muscleGroupId = 1;
        MuscleGroup muscleGroup = new MuscleGroup("Chest", "Pectoralis major and minor");

        when(muscleGroupRepository.findById(muscleGroupId)).thenReturn(Optional.of(muscleGroup));

        MuscleGroup result = muscleGroupService.findById(muscleGroupId);

        assertNotNull(result);
        assertEquals(muscleGroup, result);
        verify(muscleGroupRepository, times(1)).findById(muscleGroupId);
    }

    @Test
    void testFindByIdIfItDoesNotExist() {
        when(muscleGroupRepository.findById(any())).thenReturn(Optional.empty());
        MuscleGroup result = muscleGroupService.findById(any());
        assertNull(result);
    }

    @Test
    void testCreateNewExercise() {
        MuscleGroup muscleGroup = new MuscleGroup("Chest", "Pectoralis major and minor");
        muscleGroupService.create(muscleGroup);

        verify(muscleGroupRepository, times(1)).save(muscleGroup);
    }

    @Test
    void testDeleteExercise() {
        muscleGroupService.delete(1);
        verify(muscleGroupRepository, times(1)).deleteById(1);
    }
}