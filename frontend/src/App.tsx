import {Navbar} from "./components/navbar/Navbar";
import {Outlet} from "react-router-dom";
import {useEffect} from "react";

export const App = () => {
    useEffect(() => {
        if (window.location.hostname !== 'tauri.localhost' && window.location.protocol !== 'tauri:') {
            return
        }

        function contextMenuListener(e: Event) {
            e.preventDefault();
            return false;
        }

        document.addEventListener('contextmenu', contextMenuListener, {capture: true})

        return () => document.removeEventListener('contextmenu', contextMenuListener);
    }, []);

    return (
        <>
            <Navbar/>
            <Outlet/>
        </>
    )
}