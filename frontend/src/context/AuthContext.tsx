import { UserModel } from '@/models/UserModel';
import { albumApi, userApi } from '@/services/apiService';
import { createContext, useCallback, useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';

interface AuthContextModel extends UserModel {
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<string | void>;
  logout: () => void;
}

export const AuthContext = createContext({} as AuthContextModel);

interface Props {
  children: React.ReactNode;
}

export const AuthProvider: React.FC<Props> = ({ children }) => {
  const token = JSON.parse(localStorage.getItem('@Auth.Token') || '{}');

  const [userData, setUserData] = useState<UserModel>();
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(
    typeof token !== 'string' ? false : true
  );

  useEffect(() => {
    const data: UserModel = JSON.parse(
      localStorage.getItem('@Auth.Data') || '{}'
    );
    const storage = JSON.parse(localStorage.getItem('@Auth.Token') || '{}');

    if (data.id) {
      setIsAuthenticated(true);
      setUserData(data);
      userApi.defaults.headers.common.Authorization = `Basic ${storage}`;
      albumApi.defaults.headers.common.Authorization = `Basic ${storage}`;
    }
  }, []);

  const Login = useCallback(async (email: string, password: string) => {
    const respAuth = await userApi.post('/users/auth', { email, password });

    if (respAuth instanceof Error) {
      return respAuth.message;
    }

    localStorage.setItem('@Auth.Token', JSON.stringify(respAuth.data.token));

    userApi.defaults.headers.common.Authorization = `Basic ${respAuth.data.token}`;
    albumApi.defaults.headers.common.Authorization = `Basic ${respAuth.data.token}`;

    const respUserInfo = await userApi.get(`/users/${respAuth.data.id}`);

    if (respUserInfo instanceof Error) {
      return respUserInfo.message;
    }
    setUserData(respUserInfo.data);

    localStorage.setItem('@Auth.Data', JSON.stringify(respUserInfo.data));
    setUserData(respUserInfo.data);
    setIsAuthenticated(true);
  }, []);

  const Logout = useCallback(() => {
    localStorage.removeItem('@Auth.Data');
    localStorage.removeItem('@Auth.Token');
    setUserData(undefined);
    setIsAuthenticated(false);
    return <Navigate to="/" />;
  }, []);

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated: isAuthenticated,
        ...userData,
        login: Login,
        logout: Logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
