import React from "react";
import "./Header.css";
import logo from "../../assets/esquizorro.png";

import { BrowserRouter as Routes, useNavigate } from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate();
    function MainMenu() {
        localStorage.removeItem("login");
        localStorage.removeItem("idDoctor");
        localStorage.removeItem("idPaciente");
        navigate("/");
    }
    return (
        <header className="header">
            <div className="header_logo">
                <img src={logo} alt="logo" onClick={MainMenu}/>
            </div>
            <h2 className="header_title" onClick={MainMenu}>Guineuro</h2>
        </header>
    );
};

    export default Header;