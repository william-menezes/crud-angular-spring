package com.loiane.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.loiane.crudspring.model.Course;
import com.loiane.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id).map(course -> ResponseEntity.ok().body(course))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course) {
        return courseRepository.findById(id).map(courseFoud -> {
            courseFoud.setName(course.getName());
            courseFoud.setCategory(course.getCategory());
            Course updated = courseRepository.save(courseFoud);
            return ResponseEntity.ok().body(updated);
        })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id).map(course -> {
            courseRepository.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        })
                .orElse(ResponseEntity.notFound().build());
    }
}
