import ReactDOM from 'react-dom/client';
import './global.css';
import React from 'react';
import { Toaster } from 'react-hot-toast';
import { AuthProvider } from './context/AuthContext';
import { BrowserRouter } from 'react-router-dom';

import App from './App';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.Fragment>
    <BrowserRouter>
      <AuthProvider>
        <Toaster position="top-right" toastOptions={{ duration: 2000 }} />
        <App />
      </AuthProvider>
    </BrowserRouter>
  </React.Fragment>
);
