import { FormEvent, useEffect, useState } from 'react';
import { useAuth } from '@/hooks/UseAuth';
import { useNavigate } from 'react-router-dom';

import UserForm, { UserData } from '@/components/UserForm/UserForm';
import toast from 'react-hot-toast';

export function Login() {
  const [loading, setLoading] = useState(false);
  const { login, isAuthenticated } = useAuth();

  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/dashboard');
    }
  }, []);

  async function handleLogin(event: FormEvent, userData: UserData) {
    event.preventDefault();
    setLoading(true);

    const toastId = toast.loading('Verificando dados...');

    // I put this in because I wanted to simulate what it would be like in production, but when I release it to production, remove it
    //I refer to setTimeOut in the following way:

    try {
      await login(userData.email, userData.password);
      setTimeout(() => {
        navigate('/dashboard');
      }, 1001);
    } catch (error: any) {
      setTimeout(() => {
        toast.error('Dados inválidos!');
      }, 1001);
    } finally {
      setTimeout(() => {
        toast.dismiss(toastId);
        setLoading(false);
      }, 1000);
    }
  }

  return <UserForm handleSubmit={handleLogin} loading={loading} />;
}
