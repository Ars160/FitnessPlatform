import React, { useEffect, useState } from "react";
import { Link, Outlet, useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function Navigation() {

    const location = useLocation();
    const navigate = useNavigate();

    const [hasToken, setHasToken] = useState(false);

    useEffect(() => {
        if(location.state){
            toast(location.state.message);
            location.state = null;
        }
        
        const user = JSON.parse(localStorage.getItem("user"))
        if(user){
            setHasToken(true);
        }

    } , [location])

    const handleLogout=()=>{
        localStorage.clear();
        setHasToken(false);
        navigate('/');
    }

    return (
        <>
        <ToastContainer/>
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">BePro</Link>
                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link" to="/" exact={true.toString()}>Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/products" exact={true.toString()}>Shop</Link>
                        </li>
                    </ul>
                </div>
                <div>
                    {
                        hasToken ? (
                            <button onClick={()=>{handleLogout()}} className="btn btn-primary">Log out</button>
                        ):(
                            <button onClick={()=>{navigate("/login")}} className="btn btn-primary">Login</button>
                        )
                    }
                
                </div>
            </div>
        </nav>
        <Outlet/>
        </>
    );
}
