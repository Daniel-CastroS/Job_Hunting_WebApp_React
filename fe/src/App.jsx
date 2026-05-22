import logo from './assets/logo.png';
import './App.css';
import Puestos from "./Puestos.jsx";
import { Link, BrowserRouter, Routes, Route } from 'react-router';

function App() {
  return (
      <BrowserRouter>
        <Header />
        <Main />
        <Footer />
      </BrowserRouter>
  );
}

function Header() {
  return (
      <header className="header">
        <div className="header-left">
          <img src={logo} className="logo" alt="logo"/>
          <span className="header-title">BolsaEmpleo</span>
        </div>
        <nav className="header-nav">
          <Link to="/puestos">Buscar puestos</Link>
          <a href="#empresas">Empresas</a>
          <a href="#oferentes">Oferentes</a>
        </nav>
        <button className="login-btn">Login</button>
      </header>
  );
}

function Main() {
  return (
      <div className="main">
        <Routes>
          <Route exact path="/" element={<Puestos />}/>
          <Route exact path="/puestos" element={<Puestos />}/>
        </Routes>
      </div>
  );
}

function Footer() {
  return (
      <footer className="footer">
        <div>
          <strong>Bolsa de Empleo</strong><br/>
          <small>Total Soft Inc.</small>
        </div>
        <div>
          <small>Contacto: info@bolsaempleo.local</small><br/>
          <small>Créditos: Equipo de desarrollo</small>
        </div>
      </footer>
  );
}

export default App;