import { ReactNode } from "react";
import AppBar from '../common/Appbar';
import Footer from "../common/Footer";

export default function Layout({children}: {children: ReactNode}) {
    return (
        <div className="app">
            <header>
                <AppBar />
            </header>
            <main>{children}</main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}