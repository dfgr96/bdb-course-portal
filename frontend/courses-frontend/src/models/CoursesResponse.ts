import type { Course } from "./Course";

export interface CoursesResponse {
  availableCourses: Course[];
  activeCourses: Course[];
}