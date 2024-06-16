import { useNavigate } from "react-router";
import { logo } from "../../assets";
import "./footer.css";

export const Footer = () => {
  const navigate = useNavigate();
  return (
    <footer>
      <div className="footer-div">
        <div className="logo-footer">
          <img src={logo} alt="" />
          <p>
            Specializes in providing high-quality, stylish hand crafted products
          </p>
        </div>
        <div className="contacts">

          <div className="socials">
            <h3>CONTACTS: </h3>
            <a
              href="/"
              target="_blank"
              rel="noreferrer"
            >
              <i class="fa-brands fa-github fa-lg"></i> Github
            </a>
            <a
              href="/"
              target="_blank"
              rel="noreferrer"
            >
              <i class="fa-brands fa-twitter fa-lg"></i> Twitter
            </a>
            <a
              href="/"
              target="_blank"
              rel="noreferrer"
            >
              <i class="fa-brands fa-linkedin fa-lg"></i> LinkedIn
            </a>
          </div>
        </div>
      </div>
      <hr />
      <p>
        Copyright <i class="fa-regular fa-copyright"></i>2023 JPMMSSS. All rights
        reserved.
      </p>
    </footer>
  );
};
