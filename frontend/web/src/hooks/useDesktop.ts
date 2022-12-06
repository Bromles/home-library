import {useEffect} from "react";

export const useDesktop = () => {
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
}
