import { useLocation, useNavigate } from "react-router";
import { toast } from "react-hot-toast";
import axios from 'axios'
import { useState } from "react";
import { userEndpoints } from "../services/apis";

export const Login = () => {
  const navigate = useNavigate();
  const location = useLocation()
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [userData, setUserData] = useState({
    email: "",
    password: "",
    phone: "",
  });


  const handlePasswordClick = () => setIsPasswordVisible((prev) => !prev);

  const handleLogin = async () => {
    if (!userData.phone.trim() || !userData.password.trim()) {
      toast.warning("Enter all credentials!")
    } else {
      const res = await axios.post(userEndpoints.LOGIN_API, {
        phone: userData.email,
        pwd: userData.password,
      })

      if (userData.password === "12345") 
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
          <label for="phone">Phone:</label>
          <input
            type='text'
            id="phone"
            placeholder="Enter phone number"
            value={userData.phone}
            onChange={(e) =>
              setUserData((prev) => ({ ...prev, phone: e.target.value }))
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