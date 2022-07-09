import {Navbar} from "./components/navbar/Navbar";
import {Outlet} from "react-router-dom";
import {useCallback, useEffect} from "react";

export const App = () => {
    const disableMenu = useCallback(() => {
        if (window.location.hostname !== 'tauri.localhost' && window.location.protocol !== 'tauri:') {
            return
        }

        document.addEventListener('contextmenu', e => {
            e.preventDefault();
            return false;
        }, { capture: true })
    }, []);

    useEffect(() => disableMenu(), []);

    return (
        <>
            <Navbar/>
            <Outlet/>
        </>
    )
}