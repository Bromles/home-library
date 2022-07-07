import {useState} from 'react';
import logo from '../logo.svg';
import styles from './App.module.css';
import store from "./AppStore";
import {observer} from "mobx-react-lite";
import {Button} from "primereact/button";

if (store.useDarkTheme) {
    await import('primereact/resources/themes/lara-dark-teal/theme.css')
} else {
    await import('primereact/resources/themes/lara-light-teal/theme.css')
}

import "primeicons/primeicons.css";
import {PrimeIcons} from "primereact/api";

const App = observer(() => {
    const [count, setCount] = useState(0)

    return (
        <div className={styles.app}>
            <header className={styles.appHeader}>
                <img src={logo} className={styles.appLogo} alt="logo"/>
                <p>Hello Vite + React!</p>
                <p>
                    <button type="button" onClick={() => setCount((c) => c + 1)}>
                        count is: {count}
                    </button>
                </p>
                <div>
                    <Button icon={store.useDarkTheme ? PrimeIcons.SUN : PrimeIcons.MOON}
                            className="themeButton p-button-outlined"
                            onClick={() => store.useDarkTheme = !store.useDarkTheme}>
                    </Button>
                </div>
                <p>
                    Edit <code>App.tsx</code> and save to test HMR updates.
                </p>
                <p>
                    <a
                        className={styles.appLink}
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                    {' | '}
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