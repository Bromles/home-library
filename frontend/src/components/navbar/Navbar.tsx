import {observer} from "mobx-react-lite";
import store from "../../AppStore";
import styles from "./Navbar.module.css";
import {Button} from "primereact/button";
import {PrimeIcons} from "primereact/api";
import {useCallback} from "react";
import {Link} from "react-router-dom";

export const Navbar = observer(() => {
    return (
        <nav className={styles.navbar}>
            <Link className={styles.appLink} to="/">Home</Link>
            <Link className={styles.appLink} to="/library">Library</Link>
            <ThemeToggle/>
        </nav>
    )
})

const ThemeToggle = observer(() => {
    const changeTheme = useCallback(() => {
        const link = document.getElementById('app-theme')
        store.useDarkTheme = !store.useDarkTheme;
        link?.setAttribute('href', store.useDarkTheme ? '/theme-dark.css' : '/theme-light.css')
    }, [store])

    return (
        <Button icon={store.useDarkTheme ? PrimeIcons.SUN : PrimeIcons.MOON}
                className={styles.appThemeButton}
                onClick={changeTheme}>
        </Button>
    )
})