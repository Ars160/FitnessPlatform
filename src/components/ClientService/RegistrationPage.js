import { useState } from 'react';
import { Link, useNavigate} from 'react-router-dom';
import {registerUser} from '../../api';

export default function RegistrationPage(){

    const navigate = useNavigate();

    const [nameError, setNameError] = useState('');
    const [emailError, setEmailError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [rePasswordError, setRePasswordError] = useState('');
    const [error, setError] = useState('');

    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        confirmPassword: ''
    });

    const handleName = (e) => {
        setFormData({
            ...formData, name: e.target.value
        });
    }

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

    const handleConfirmPassword = (e) => {
        const confirmPassword = e.target.value;
        setFormData({
            ...formData, confirmPassword
        });
    
        if (confirmPassword !== formData.password) {
            setRePasswordError('Пароли не совпадают');
        } else {
            setRePasswordError(null);
        }
    }
    

    const checkValidate = () => {
        var hasError = false;

        if (formData.name.length <= 2){
            setNameError('Имя должно состоять как миинимум из 2 символов');
            hasError = true;
        } else {
            setNameError(null);
        }

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

        if (checkValidate()) {
            return
        }
        else {
            if (formData.password != formData.confirmPassword){
                setError("Пароли не совпадают");
            }
            else {
                const registeredUser = await registerUser(formData);
                if (registeredUser) {
                    setError(registeredUser);
                }
                else {
                    localStorage.setItem('successReg', 1);
                    navigate('/login');
                }
            }
        }
    }

    return (
        <form onSubmit={handleSubmit} method='POST' className="container mt-5 border p-4 rounded" style={{width: 400}}>
            <h2>Регистрация</h2>
            <div className="mb-3">
                <label htmlFor="name" className="form-label">Имя</label>
                <input type='text' className="form-control" name="name" id="name" onChange={handleName}/>
                <p className='error'>{nameError}</p>
            </div>
            <div className="mb-3">
                <label htmlFor="email" className="form-label">Почта</label>
                <input type='email' className="form-control" name="email" id="email" onChange={handleEmail}/>
                <p className='error'>{emailError}</p>
            </div>
            <div className="mb-3">
                <label htmlFor="upassword" className="form-label">Пароль</label>
                <input type='password' className="form-control" name="upassword" id="upassword" onChange={handlePassword}/>
                <p className='error'>{passwordError}</p>
            </div>
            <div className="mb-3">
                <label htmlFor="uRePassword" className="form-label">Повторите пароль</label>
                <input type='password' className="form-control" name="uRePassword" id="uRePassword" onChange={handleConfirmPassword}/>
                <p className='error'>{rePasswordError}</p>
            </div>
            <button type='submit' className="btn btn-primary">Регистрация</button>
            <div className="mt-3">
                <label>
                    Уже есть аккаунт? <Link to='/login'>Логин</Link>
                </label>
                <p className='error'>{error}</p>
            </div>
        </form>
    )
}
