import logo from '../logo.svg';
import styles from './App.module.css';
import store from "./AppStore";
import {observer} from "mobx-react-lite";
import {Button} from "primereact/button";
import "primeicons/primeicons.css";
import {PrimeIcons} from "primereact/api";

const App = observer(() => {
    const changeTheme = () => {
        const link = document.getElementById('app-theme')
        store.useDarkTheme = !store.useDarkTheme;
        link?.setAttribute('href', store.useDarkTheme ? '/src/dark.css' : '/src/light.css')
    }

    return (
        <div className={styles.app}>
            <header className={styles.appHeader}>
                <img src={logo} className={styles.appLogo} alt="logo"/>
                <p>Hello Vite + React!</p>
                <div>
                    <Button icon={store.useDarkTheme ? PrimeIcons.SUN : PrimeIcons.MOON}
                            className={styles.appThemeButton}
                            onClick={() => changeTheme()}>
                    </Button>
                </div>
                <p>
                    <a
                        className={styles.appLink}
                        href="https://vitejs.dev/guide/features.html"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Vite Docs
                    </a>
                </p>
            </header>
        </div>
    )
})

export default App