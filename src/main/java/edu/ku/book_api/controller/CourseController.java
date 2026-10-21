// package edu.ku.book_api.controller;

// import java.util.List;

// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/v1/courses")
// @CrossOrigin(origins = "http://localhost:5173")
// public class CourseController {

//     public record Course(
//             long id,
//             String code,
//             String title,
//             int credits
//     ) {
//     }

//     @GetMapping
//     public List<Course> getCourses() {

//         return List.of(
//                 new Course(
//                         1,
//                         "EWA301",
//                         "Enterprise Web App",
//                         3
//                 ),

//                 new Course(
//                         2,
//                         "DB201",
//                         "Database Systems",
//                         3
//                 ),

//                 new Course(
//                         3,
//                         "SE202",
//                         "Software Engineering",
//                         3
//                 )
//         );
//     }
// }



package edu.ku.book_api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin(origins = "http://localhost:5173")
public class CourseController {

    // Course returned to the frontend.
    public record Course(
            long id,
            String code,
            String title,
            int credits
    ) {
    }

    // Data received when editing a course.
    public record CourseRequest(
            String code,
            String title,
            int credits
    ) {
    }

    // Temporary in-memory storage.
    private final List<Course> courses = new ArrayList<>(
            List.of(
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
            )
    );

    // GET: Return all courses.
    @GetMapping
    public synchronized List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    // PUT: Update an existing course.
    @PutMapping("/{id}")
    public synchronized ResponseEntity<?> updateCourse(
            @PathVariable long id,
            @RequestBody CourseRequest request
    ) {
        if (request.code() == null
                || request.code().isBlank()
                || request.title() == null
                || request.title().isBlank()
                || request.credits() < 1) {

            return ResponseEntity.badRequest()
                    .body("Invalid course data.");
        }

        Optional<Course> existing = courses.stream()
                .filter(course -> course.id() == id)
                .findFirst();

        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course not found with ID: " + id);
        }

        Course updatedCourse = new Course(
                id,
                request.code().trim(),
                request.title().trim(),
                request.credits()
        );

        int index = courses.indexOf(existing.get());
        courses.set(index, updatedCourse);

        return ResponseEntity.ok(updatedCourse);
    }

    // DELETE: Remove an existing course.
    @DeleteMapping("/{id}")
    public synchronized ResponseEntity<Void> deleteCourse(
            @PathVariable long id
    ) {
        boolean removed = courses.removeIf(
                course -> course.id() == id
        );

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}