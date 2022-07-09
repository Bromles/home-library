import styles from "./Home.module.css";
import logo from "../../logo.svg";

export const Home = () => {
    return (
        <div className={styles.app}>
            <header className={styles.appHeader}>
                <img src={logo} className={styles.appLogo} alt="logo"/>
                <p>Hello Vite + React!</p>
                <p>{`${window.location}`}</p>
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
}