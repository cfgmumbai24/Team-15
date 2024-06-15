import { useLocation, useNavigate } from "react-router";
import { toast } from "react-hot-toast";

import { useState } from "react";

export const Login = () => {
  const navigate = useNavigate();
  const location = useLocation()
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [userData, setUserData] = useState({
    email: "",
    password: "",
    phone: "",
  });

  const testUserData = {
    email: "test@gmail.com",
    password: "test1234",
  };

  const handlePasswordClick = () => setIsPasswordVisible((prev) => !prev);

  const handleLogin = () => {
    if (!userData.email.trim() || !userData.password.trim()) {
      toast.warning("Enter all credentials!")
    } else {
      if (userData.email === "admin@gmail.com") 
      {
          localStorage.setItem("role", 0);
      }
      else
      {
        localStorage.setItem("role", 1);
        }
      navigate("/")

    }
  };


  return (
    <div>
      <div className="login">
        <h2>Login</h2>
        <div>
          <label for="email">Email:</label>
          <input
            type='login'
            id="email"
            placeholder="johndoe@example.com"
            value={userData.email}
            onChange={(e) =>
              setUserData((prev) => ({ ...prev, email: e.target.value }))
            }
          />
        </div>

        <div>
          <label for="password">Password:</label>
          <div className="password-wrapper">
            <input
              id="password"
              type={isPasswordVisible ? "text" : "password"}
              placeholder={isPasswordVisible ? "password" : "****"}
              value={userData.password}
              onChange={(e) =>
                setUserData((prev) => ({ ...prev, password: e.target.value }))
              }
            />
            <button onClick={handlePasswordClick}>
              {isPasswordVisible ? (
                <i class="fa-regular fa-eye-slash"></i>
              ) : (
                <i class="fa-regular fa-eye"></i>
              )}
            </button>
          </div>
        </div>

        <button className="login-button" onClick={handleLogin}>
          Login
        </button>

      </div>
    </div>
  );
};