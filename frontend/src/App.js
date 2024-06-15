import React from "react";
import {BrowserRouter as Router,Routes,Route} from "react-router-dom";
import Home from "./Home";
import About from "./About";
const App=()=>{
  return (
  <Router>
    <Routes>
      <Route path="/" element={<Home/>}/>
      <Route path="/About" element={<About/>}/>
    </Routes>
  </Router>
  );
};

export default App;
