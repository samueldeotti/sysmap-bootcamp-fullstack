import { FormEvent, useEffect, useState } from 'react';
import { userApi } from '../../services/apiService';
import toast from 'react-hot-toast';
import UserForm, { UserData } from '@/components/UserForm/UserForm';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '@/hooks/UseAuth';

export function Signup() {
  const { isAuthenticated } = useAuth();

  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/dashboard');
    }
  }, []);

  async function handleSigup(event: FormEvent, userData: UserData) {
    setLoading(true);
    event.preventDefault();
    const toastId = toast.loading('Criando conta...');

    // I put this in because I wanted to simulate what it would be like in production, but when I release it to production, remove it
    //I refer to setTimeOut in the following way:

    try {
      await userApi.post('/users/create', userData);

      setTimeout(() => {
        toast.success('Conta criada com sucesso!');
      }, 1001);
      setTimeout(() => {
        navigate('/login');
      }, 1500);
    } catch (error: any) {
      setTimeout(() => {
        if (error.response.status === 401) {
          toast.error('Email já cadastrado!');
          return;
        }
        toast.error('Algo deu errado!');
      }, 1001);
    } finally {
      setTimeout(() => {
        toast.dismiss(toastId);
        setLoading(false);
      }, 1000);
    }
  }

  return <UserForm handleSubmit={handleSigup} isSignup loading={loading} />;
}
