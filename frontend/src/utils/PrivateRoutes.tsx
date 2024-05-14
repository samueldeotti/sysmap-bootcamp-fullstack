import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '@/hooks/UseAuth';

export function PrivateRoutes() {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
}
