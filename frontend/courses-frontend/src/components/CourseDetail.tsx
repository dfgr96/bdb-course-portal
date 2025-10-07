import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { Course } from "../models/Course";

export default function CourseDetail() {
  const { id } = useParams<{ id: string }>();
  const [course, setCourse] = useState<Course | null>(null);
  const [error, setError] = useState("");
  const [loadingModule, setLoadingModule] = useState<number | null>(null);
  const userId = localStorage.getItem("userId") || "3";

  useEffect(() => {
    const fetchCourse = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await fetch(
          `http://localhost:8080/courses/${id}?userId=${userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        if (!res.ok) throw new Error("Error al obtener curso");
        const data: Course = await res.json();
        data.modules = data.modules.map((m) => ({
          ...m,
          progressPercent: m.progressPercent ?? 0,
          completed: m.completed ?? (m.progressPercent ?? 0) >= 100,
        }));
        setCourse(data);
      } catch (err: any) {
        setError(err.message || "Error desconocido");
      }
    };

    fetchCourse();
  }, [id, userId]);

  const computeCoursePercent = (c: Course | null) => {
    if (!c || !c.modules || c.modules.length === 0) return 0;
    const sum = c.modules.reduce((acc, m) => acc + (m.progressPercent ?? 0), 0);
    return Math.round(sum / c.modules.length);
  };

  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!course) return <p>Cargando curso...</p>;

  const coursePercent = computeCoursePercent(course);

  return (
    <div style={{ maxWidth: 700, margin: "2rem auto" }}>
      <h2>{course.title}</h2>
      <p>{course.description}</p>

      <div style={{ marginTop: 12 }}>
        <div style={{ height: 12, background: "#eee", borderRadius: 8 }}>
          <div
            style={{
              height: "100%",
              width: `${coursePercent}%`,
              background: "#4caf50",
              borderRadius: 8,
            }}
          />
        </div>
        <p style={{ margin: "6px 0 0 0" }}>{coursePercent}% completado</p>
      </div>

      <h3 style={{ marginTop: 18 }}>Módulos</h3>
      <ul style={{ paddingLeft: 0, listStyle: "none" }}>
        {course.modules.map((m) => (
          <li
            key={m.id}
            style={{
              marginBottom: "1.25rem",
              padding: 12,
              border: "1px solid #eee",
              borderRadius: 8,
            }}
          >
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <strong>{m.title}</strong>
              <small>{m.contentType}</small>
            </div>

            <div style={{ marginTop: 8 }}>
              {m.contentType === "IMAGE" && (
                <img
                  src={m.contentUrl}
                  alt={m.title}
                  style={{ maxWidth: "100%", border: "1px solid #ccc" }}
                />
              )}
              {m.contentType === "VIDEO" && (
                <video
                  controls
                  style={{ maxWidth: "100%", border: "1px solid #ccc" }}
                >
                  <source src={m.contentUrl} type="video/mp4" />
                  Tu navegador no soporta el tag de video.
                </video>
              )}
            </div>

            <div style={{ marginTop: 8 }}>
              <div style={{ height: 8, background: "#f3f3f3", borderRadius: 6 }}>
                <div
                  style={{
                    height: "100%",
                    width: `${m.progressPercent ?? 0}%`,
                    background: "#1976d2",
                    borderRadius: 6,
                  }}
                />
              </div>
              <div style={{ marginTop: 6, display: "flex", gap: 8, alignItems: "center" }}>
                <span style={{ minWidth: 40 }}>{m.progressPercent ?? 0}%</span>

                <button
                  disabled={(m.progressPercent ?? 0) >= 100 || loadingModule === m.id}
                  onClick={async () => {
                    if (!course) return;
                    setLoadingModule(m.id);
                    try {
                      const token = localStorage.getItem("token");
                      const res = await fetch(
                        `http://localhost:8080/progress?userId=${userId}&moduleId=${m.id}&percent=100`,
                        {
                          method: "POST",
                          headers: {
                            Authorization: `Bearer ${token}`,
                          },
                        }
                      );
                      if (!res.ok) throw new Error("No se pudo actualizar el progreso");

                      const updatedModules = course.modules.map((mod) =>
                        mod.id === m.id ? { ...mod, progressPercent: 100, completed: true } : mod
                      );
                      setCourse({ ...course, modules: updatedModules });

                      const allDone = updatedModules.every((mod) => (mod.progressPercent ?? 0) >= 100);
                      if (allDone) {
                        await fetch(
                          `http://localhost:8080/progress/completeCourse?userId=${userId}&courseId=${course.id}`,
                          {
                            method: "POST",
                            headers: { Authorization: `Bearer ${token}` },
                          }
                        );
                        setCourse((prev) => (prev ? { ...prev, completed: true } : prev));
                      }
                    } catch (err: any) {
                      setError(err.message || "Error actualizando módulo");
                    } finally {
                      setLoadingModule(null);
                    }
                  }}
                >
                  {(m.progressPercent ?? 0) >= 100
                    ? "✅ Completado"
                    : loadingModule === m.id
                    ? "..."
                    : "Marcar módulo completado"}
                </button>
              </div>
            </div>
          </li>
        ))}
      </ul>

      {course.completed && <p style={{ color: "green" }}>¡Curso completado! 🏅</p>}
    </div>
  );
}
