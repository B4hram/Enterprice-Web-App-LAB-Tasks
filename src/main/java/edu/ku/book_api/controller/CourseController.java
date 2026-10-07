package edu.ku.book_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin(origins = "http://localhost:5173")
public class CourseController {

    public record Course(
            long id,
            String code,
            String title,
            int credits
    ) {
    }

    @GetMapping
    public List<Course> getCourses() {

        return List.of(
                new Course(
                        1,
                        "EWA301",
                        "Enterprise Web App",
                        3
                ),

                new Course(
                        2,
                        "DB201",
                        "Database Systems",
                        3
                ),

                new Course(
                        3,
                        "SE202",
                        "Software Engineering",
                        3
                )
        );
    }
}