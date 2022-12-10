import {useEffect, useState} from "react";

export const useThemeDetector = () => {
    const [isDarkTheme, setIsDarkTheme] = useState(() => window.matchMedia("(prefers-color-scheme: dark)").matches);

    const mqListener = (((e: { matches: boolean }) => {
        setIsDarkTheme(e.matches);
    }));

    useEffect(() => {
        const darkThemeMq = window.matchMedia("(prefers-color-scheme: dark)");

        darkThemeMq.addEventListener("change", mqListener);

        return () => darkThemeMq.removeEventListener("change", mqListener);
    }, []);

    const storedValue: string | null = localStorage.getItem('useDarkTheme');
    if(storedValue !== null) {
        return storedValue === 'true';
    }
    else {
        return isDarkTheme;
    }
}
