import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {Library} from "./pages/library/Library";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import "primeicons/primeicons.css";
import {App} from "./App";
import {Home} from "./pages/home/Home";

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App/>}>
                    <Route index element={<Home/>}/>
                    <Route path="library" element={<Library/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
)