import { Route, Routes } from 'react-router-dom';
import { Login } from './pages/Login/Login';
import { Dashboard } from './pages/Dashboard/Dashboard';
import Landing from './pages/Landing/Landing';
import { PrivateRoutes } from './utils/PrivateRoutes';
import ErrorPage from './pages/ErrorPage/ErrorPage';
import UserAlbums from './pages/UserAlbums/UserAlbums';
import { Signup } from './pages/Signup/Signup';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={ <Landing /> } />
      <Route path="/login" element={ <Login /> } />
      <Route path="/signup" element={ <Signup /> } />

      <Route path="" element={ <PrivateRoutes /> }>
        <Route path="/dashboard" element={ <Dashboard /> } />
        <Route path="/albums/my-collection" element={ <UserAlbums /> } />
      </Route>

      <Route path="*" element={ <ErrorPage /> } />

    </Routes>
  );
}
