import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { checkTokenValidity } from '../../api';

export default function useTokenValidation(token, userEmail) {
    const navigate = useNavigate();

    useEffect(() => {
        if (!token){
            localStorage.setItem('invalidTokenError', 1);
            navigate('/login');
        }
        else {
            const checkToken = async () => {
                const response = await checkTokenValidity(token, userEmail);
                return response;
            }

            checkToken().then((result) => {
                if (!result){
                    localStorage.setItem('invalidTokenError', 1);
                    navigate('/login');
                }
            })
        }
    },[token, userEmail])
}

