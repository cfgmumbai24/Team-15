import React from "react";
import {BrowserRouter as Router,Routes,Route, useNavigate} from "react-router-dom";
import Admin from "./pages/Admin";
import { Login } from "./pages/Login";

const App=()=>{
  return (
  <Router>
    <Routes>
      <Route path="/login" element={<Login/>}/>
      <Route path="/*" element={<Admin/>}/>
    </Routes>
  </Router>
  );
};

export default App;
