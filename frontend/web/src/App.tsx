import {Topbar} from "./components/topbar/Topbar";
import {Outlet} from "react-router-dom";
import {useDesktop} from "./hooks/useDesktop";
import {useThemeDetector} from "./hooks/useThemeDetector";
import React, {useEffect, useState} from "react";
import {AppStore} from "./AppStore";
import {themeChanger} from "./utils/themeChanger";

export const AppStoreContext = React.createContext<AppStore>({} as AppStore);

export const App = () => {
    useDesktop();

    const isDarkTheme = useThemeDetector();
    const [appStore] = useState(() => new AppStore(isDarkTheme));

    useEffect(() => {
        appStore.useDarkTheme = isDarkTheme;
        themeChanger(appStore);
    }, [isDarkTheme]);

    return (
        <>
            <AppStoreContext.Provider value={appStore}>
                <Topbar/>
                <Outlet/>
            </AppStoreContext.Provider>
        </>
    )
}
