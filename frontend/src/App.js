import React from 'react';
import { HashRouter, Routes, Route } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import DashboardPage from './pages/DashboardPage';
import RsaModule from './pages/RsaModule';
import DhModule from './pages/DhModule';
import DsaModule from './pages/DsaModule';

export default function App() {
  return (
    <HashRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/rsa" element={<RsaModule />} />
        <Route path="/dh" element={<DhModule />} />
        <Route path="/dsa" element={<DsaModule />} />
      </Routes>
    </HashRouter>
  );
}
