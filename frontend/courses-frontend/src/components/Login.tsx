import { useState } from "react";
import { useNavigate } from "react-router-dom";
import type { Login } from "../models/Login";
import type { Register } from "../models/Register";

export default function Login() {
  const [isRegistering, setIsRegistering] = useState(false);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [passwordHash, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    try {
      const url = isRegistering
        ? "http://localhost:8081/users"
        : "http://localhost:8081/auth/login";

      let body: string;

      if (isRegistering) {
        const registerData: Register = {
          name,
          email,
          passwordHash,
          role: "USER",
        };
        body = JSON.stringify(registerData);
      } else {
        body = JSON.stringify({ email, passwordHash });
      }

      const response = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body,
      });

      if (!response.ok) {
        throw new Error(
          isRegistering ? "Error al registrarse" : "Credenciales inválidas"
        );
      }

      if (isRegistering) {
        alert("✅ Registro exitoso. Ahora puedes iniciar sesión.");
        setIsRegistering(false);
        setPassword("");
        return;
      }

      const data: Login = await response.json();
      localStorage.setItem("token", data.token);
      localStorage.setItem("userId", data.id.toString());
      localStorage.setItem("name", data.name);
      localStorage.setItem('role', data.role);
      navigate("/courses");
    } catch (err: any) {
      setError(err.message);
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: "2rem auto" }}>
      <h2>{isRegistering ? "Crear cuenta" : "Iniciar sesión"}</h2>

      <form onSubmit={handleSubmit}>
        {isRegistering && (
          <div>
            <label>Nombre</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
        )}

        <div>
          <label>Correo electrónico</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Contraseña</label>
          <input
            type="password"
            value={passwordHash}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button type="submit">
          {isRegistering ? "Registrarse" : "Ingresar"}
        </button>
      </form>

      <p style={{ marginTop: "1rem" }}>
        {isRegistering ? (
          <>
            ¿Ya tienes cuenta?{" "}
            <button
              type="button"
              onClick={() => {
                setIsRegistering(false);
                setError("");
              }}
            >
              Inicia sesión
            </button>
          </>
        ) : (
          <>
            ¿No tienes cuenta?{" "}
            <button
              type="button"
              onClick={() => {
                setIsRegistering(true);
                setError("");
              }}
            >
              Regístrate
            </button>
          </>
        )}
      </p>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}
