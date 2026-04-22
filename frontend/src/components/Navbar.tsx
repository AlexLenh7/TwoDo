import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext.tsx";

export default function Navbar() {
  const { user, isAuthenticated, logout } = useAuth();

  const handleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  };

  return (
    <nav className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
      <Link to="/" className="text-xl font-bold">
        PityPlanner
      </Link>

      <div className="flex items-center gap-6">
        <Link to="/" className="text-sm text-gray-600 hover:text-black">
          Home
        </Link>

        {isAuthenticated ? (
          <div className="flex items-center gap-4">
            <Link to="/dashboard" className="text-sm text-gray-600 hover:text-black">
              Dashboard
            </Link>
            <div className="flex items-center gap-3">
              {user?.picture && <img src={user.picture} alt={user.name} className="w-8 h-8 rounded-full" />}
              <span className="text-sm text-gray-700">{user?.name}</span>
              <button onClick={logout} className="text-sm text-gray-500 hover:text-black">
                Sign out
              </button>
            </div>
          </div>
        ) : (
          <button onClick={handleLogin} className="px-4 py-2 text-sm bg-black text-white rounded-lg hover:bg-gray-800">
            Sign in with Google
          </button>
        )}
      </div>
    </nav>
  );
}
