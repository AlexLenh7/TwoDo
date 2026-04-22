import { Navigate, Outlet } from "react-router-dom";

/* 
For user OAuth sign in (protected route)
 - if token exists then render outlet inside app.tsx
*/
const ProtectedRoute = () => {
  const token = localStorage.getItem("token");
  return token ? <Outlet /> : <Navigate to="/" replace />;
};

export default ProtectedRoute;
