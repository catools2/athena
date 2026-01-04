import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Layout } from 'antd';
import Sidebar from './components/Sidebar/Sidebar';
import Header from './components/Header/Header';
import HomePage from './pages/HomePage';
import PipelinePage from './pages/PipelinePage';
import QualityPage from './pages/QualityPage';
import TestsPage from './pages/TestsPage';
import GitPage from './pages/GitPage';
import KubePage from './pages/KubePage';
import TmsPage from './pages/TmsPage';
import CorePage from './pages/CorePage';
import MetricPage from './pages/MetricPage';
import SpecPage from './pages/SpecPage';
import NotFoundPage from './pages/NotFoundPage';
import './App.css';

const { Content } = Layout;

function App() {
  return (
    <Router>
      <Layout style={{ minHeight: '100vh' }}>
        <Sidebar />
        <Layout style={{ marginLeft: 200 }}>
          <Header />
          <Content style={{ padding: '24px', overflow: 'auto' }}>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/pipeline" element={<PipelinePage />} />
              <Route path="/quality" element={<QualityPage />} />
              <Route path="/tests" element={<TestsPage />} />
              <Route path="/git" element={<GitPage />} />
              <Route path="/kube" element={<KubePage />} />
              <Route path="/tms" element={<TmsPage />} />
              <Route path="/core" element={<CorePage />} />
              <Route path="/metric" element={<MetricPage />} />
              <Route path="/spec" element={<SpecPage />} />
              <Route path="*" element={<NotFoundPage />} />
            </Routes>
          </Content>
        </Layout>
      </Layout>
    </Router>
  );
}

export default App;

