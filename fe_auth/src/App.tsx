import './App.css'
import {AuthProvider} from "./context/AuthContext.tsx";
import Login from "./pages/Login.tsx";
import Home from "./pages/Home.tsx";
import LoginRedirect from "./redirects/LoginRedirect.tsx";
import HomeRedirect from "./redirects/HomeRedirect.tsx";
import {Routes, Route, BrowserRouter as Router} from "react-router-dom";

function AppRoutes() {
    return (
        <Router>
            <Routes>
                <Route
                    path="/login" element={
                    <HomeRedirect>
                        <Login/>
                    </HomeRedirect>
                }
                />

                <Route
                    path="/"
                    element={
                        <LoginRedirect>
                            <Home/>
                        </LoginRedirect>
                    }
                />
            </Routes>
        </Router>
    )
}

function App() {
    return (
        <AuthProvider>
            <AppRoutes/>
        </AuthProvider>
    )
}

export default App
