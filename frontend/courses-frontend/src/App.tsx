import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./components/Login";
import CourseList from "./components/CourseList";
import CourseDetail from "./components/CourseDetail";
import "./App.css";
import NewCourse from "./components/NewCourse";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/courses" element={<CourseList />} />
        <Route path="/courses/:id" element={<CourseDetail />} />
        <Route path="/courses/new" element={<NewCourse />} />
      </Routes>
    </Router>
  );
}

export default App;
