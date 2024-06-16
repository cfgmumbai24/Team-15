import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router";
import "./login.css";
import { useAuth } from "../../context/authContext";
import { toast } from "react-toastify";
import ClipLoader from "react-spinners/ClipLoader";

export const Login = () => {
  const navigate = useNavigate();
  const { userLogin, authState } = useAuth();
  const location = useLocation();
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [userData, setUserData] = useState({
    email: "",
    password: "",
  });

  const testUserData = {
    email: "testuser@example.com", // Replace with valid test user email
    password: "password123", // Replace with valid test user password
  };

  const handlePasswordClick = () => setIsPasswordVisible((prev) => !prev);

  const handleLogin = async () => {
    if (!userData.email.trim() || !userData.password.trim()) {
      toast.warning("Enter all credentials!");
    } else {
      try {
        await userLogin(userData);
        navigate(location?.state?.from?.pathname || "/products");
      } catch (error) {
        toast.error("Login failed: " + error.message);
      }
    }
  };

  const handleTestLogin = async () => {
    try {
      setUserData(testUserData);
      await userLogin(testUserData);
      navigate(location?.state?.from?.pathname || "/products");
    } catch (error) {
      toast.error("Test login failed: " + error.message);
    }
  };

  // Automatically login as guest on mount
  useEffect(() => {
    handleTestLogin();
  }, []);

  return (
    <div>
      <div className="login">
        <h2>Login</h2>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            placeholder="johndoe@example.com"
            value={userData.email}
            onChange={(e) =>
              setUserData((prev) => ({ ...prev, email: e.target.value }))
            }
          />
        </div>

        <div>
          <label htmlFor="password">Password:</label>
          <div className="password-wrapper">
            <input
              id="password"
              type={isPasswordVisible ? "text" : "password"}
              placeholder={isPasswordVisible ? "password" : "********"}
              value={userData.password}
              onChange={(e) =>
                setUserData((prev) => ({ ...prev, password: e.target.value }))
              }
            />
            <button onClick={handlePasswordClick}>
              {isPasswordVisible ? (
                <i className="fa-regular fa-eye-slash"></i>
              ) : (
                <i className="fa-regular fa-eye"></i>
              )}
            </button>
          </div>
        </div>

        <button className="login-button" onClick={handleLogin}>
          {authState.isAuthLoading && <ClipLoader color={`#fff`} size={15} />}
          Login
        </button>

        <button className="login-button" onClick={handleTestLogin}>
          Login as Guest
        </button>

        <p onClick={() => navigate("/signup")}>
          Create New account <i className="fa-solid fa-angle-right"></i>
        </p>
      </div>
    </div>
  );
};
