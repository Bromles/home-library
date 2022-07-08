import {Navbar} from "./navbar/Navbar";
import {Outlet} from "react-router-dom";

export const App = () => {
    return (
        <div>
            <Navbar/>
            <Outlet/>
        </div>
    )
}