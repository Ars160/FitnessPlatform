import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getMyUserId, loginUser } from "../../api";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function LoginPage(){

    // localStorage.removeItem('user_id');
    useEffect(() => {



        if (localStorage.getItem('invalidTokenError')){
            toast("Сперва вы должны войти в профиль");
            localStorage.removeItem('invalidTokenError');
        }

        if (localStorage.getItem('successReg')){
            toast('Вы успешно зарегистрированы');
            localStorage.removeItem('successReg');
        }
    }, [])

    const navigate = useNavigate();

    const [emailError, setEmailError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');

    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });

    const handleEmail = (e) => {
        setFormData({
            ...formData, email: e.target.value
        });
    }

    const handlePassword = (e) => {
        setFormData({
            ...formData, password: e.target.value
        });
    }

    const checkValidate = () => {
        var hasError = false;

        if (formData.email == ''){
            setEmailError('Email не может быть пустым');
            hasError = true;
        } else {
            setEmailError(null);
        }

        if (formData.password.length < 8 || !/[!@#$%^&*(),.?":{}|<>0-9]/.test(formData.password)) {
            setPasswordError('Пароль должен содержать минимум 8 символов и хотя бы один специальный символ или цифру');
            hasError = true;
        } else {
            setPasswordError(null);
        }

        return hasError;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (checkValidate()){
            return
        }
        else {
            const token = await loginUser(formData);
            if (token) {
                localStorage.setItem("token", JSON.stringify(token.token));
                localStorage.setItem("userEmail", formData.email);
                
                const user = await getMyUserId(localStorage.getItem("userEmail"));
                localStorage.setItem("user", JSON.stringify(user));
                navigate("/products/",{
                    state: { message: "You have logged in"}
                });
            } else {
                setError("Неправильный логин или пароль!");
            }
        }
    };

    return (
        <>
        <ToastContainer/>
        <form onSubmit={handleSubmit} method="POST" className="container mt-5 border p-4 rounded" style={{width: 400}}>
            <h2>Логин</h2>
            <div className="mb-3">
                <label htmlFor="email" className="form-label">Почта</label>
                <input type='email' className="form-control" name="email" id="email" onChange={handleEmail}/>
                <p className="error">{emailError}</p>
            </div>
            <div className="mb-3">
                <label htmlFor="upassword" className="form-label">Пароль</label>
                <input type='password' className="form-control" name="upassword" id="upassword" onChange={handlePassword}/>
                <p className="error">{passwordError}</p>
            </div>
            <div className="mb-3 form-check">
                <input type="checkbox" className="form-check-input" id="rememberMe" />
                <label className="form-check-label" htmlFor="rememberMe">Запомнить меня</label>
            </div>
            <button type='submit' className="btn btn-primary">Логин</button>
            <div className="mt-3">
                <label>
                    Нет аккаунта? <Link to='/register'>Регистрация</Link>
                </label>
                <p className='error'>{error}</p>
                <p className='message'>{message}</p>
            </div>
        </form>
        </>
    )
}
