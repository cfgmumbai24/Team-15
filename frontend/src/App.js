import React from "react";
import {BrowserRouter as Router,Routes,Route} from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import Admin from "./pages/Admin";

const App=()=>{
  return (
  <Router>
    <Routes>
      <Route path="/admin/*" element={<Admin/>}/>
      <Route path="/" element={<LandingPage/>}/>
    </Routes>
  </Router>
  );
};

export default App;
