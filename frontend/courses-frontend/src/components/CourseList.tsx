import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import type { Course } from "../models/Course";
import type { CoursesResponse } from "../models/CoursesResponse";

export default function CourseList() {
  const [courses, setCourses] = useState<Course[]>([]);
  const [availableCourses, setAvailableCourses] = useState<Course[]>([]);
  const [activeCourses, setActiveCourses] = useState<Course[]>([]);
  const [error, setError] = useState("");
  const name = localStorage.getItem("name")
  const userId = localStorage.getItem("userId")

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await fetch(`http://localhost:8080/courses/user/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) throw new Error("Error al obtener cursos");

        const data: CoursesResponse = await res.json();
        setAvailableCourses(data.availableCourses || []);
        setActiveCourses(data.activeCourses || []);      
      } catch (err: any) {
        setError(err.message);
      }
    };

    fetchCourses();
  }, []);

if (error) return <p style={{ color: "red" }}>{error}</p>;

return (
  <div style={{ maxWidth: 600, margin: "2rem auto" }}>
    <h2>Perfil de: {name}</h2>

    <h3>Mis cursos</h3>
    {activeCourses.length === 0 ? (
      <p>No tienes cursos inscritos todavía.</p>
    ) : (
      <ul>
        {activeCourses.map((c) => (
          <li key={c.id}>
            <Link to={`/courses/${c.id}`}>{c.title}</Link>
          </li>
        ))}
      </ul>
    )}

    <h3>Cursos disponibles</h3>
    {availableCourses.length === 0 ? (
      <p>No hay cursos disponibles en este momento.</p>
    ) : (
      <ul>
        {availableCourses.map((c) => (
          <li key={c.id}>
            {c.title} –{" "}
            <button
              onClick={async () => {
                const token = localStorage.getItem("token");
                await fetch(
                  `http://localhost:8080/enrollments?userId=${userId}&courseId=${c.id}`,
                  {
                    method: "POST",
                    headers: { Authorization: `Bearer ${token}` },
                  }
                );
                // refrescar cursos
                window.location.reload();
              }}
            >
              Inscribirme
            </button>
          </li>
        ))}
      </ul>
    )}
  </div>
);

}
