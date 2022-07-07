import {useState} from 'react'
import logo from '../logo.svg'
import styles from './App.module.css'
import globalStore from "./GlobalStore";
import {observer} from "mobx-react-lite";

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
                <p>
                    <button type="button" onClick={() => globalStore.useDarkTheme = !globalStore.useDarkTheme}>
                        Toggle dark mode: {`${globalStore.useDarkTheme}`}
                    </button>
                </p>
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