import {Topbar} from "./components/topbar/Topbar";
import {Outlet} from "react-router-dom";
import {useDesktop} from "./hooks/useDesktop";

export const App = () => {
    useDesktop();

    return (
        <>
            <Topbar/>
            <Outlet/>
        </>
    )
}
