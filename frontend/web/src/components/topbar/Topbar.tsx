import {observer} from "mobx-react-lite";
import styles from "./Topbar.module.css";
import {Button} from "primereact/button";
import {PrimeIcons} from "primereact/api";
import {useCallback, useContext} from "react";
import {Link} from "react-router-dom";
import {AppStoreContext} from "../../App";
import {themeChanger} from "../../utils/themeChanger";

export const Topbar = observer(() => {
    return (
        <nav className={styles.navbar}>
            <Link className={styles.appLink} to="/">Home</Link>
            <ThemeToggle/>
        </nav>
    )
})

const ThemeToggle = observer(() => {
    const store = useContext(AppStoreContext);
    const themeChangeHandler = useCallback(() => {
        store.useDarkTheme = !store.useDarkTheme;
        themeChanger(store)
    }, [store])

    return (
        <Button icon={store.useDarkTheme ? PrimeIcons.SUN : PrimeIcons.MOON}
                className={styles.appThemeButton}
                onClick={themeChangeHandler}>
        </Button>
    )
})
