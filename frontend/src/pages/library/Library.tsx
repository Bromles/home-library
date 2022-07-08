import {observer} from "mobx-react-lite";
import styles from "./Library.module.css";

export const Library = observer(() => {
    return (
        <div className={styles.library}>
            <h1>Library</h1>
        </div>
    )
})