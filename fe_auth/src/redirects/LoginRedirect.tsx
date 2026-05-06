import {useAuth} from "../context/AuthContext.tsx";
import type {JSX} from "react";
import {Navigate} from "react-router-dom";

export default function LoginRedirect({children}: { children: JSX.Element }) {
    const {isAuthenticated, loading} = useAuth();

    if (loading) return <div>Loading...</div>;

    if (!isAuthenticated) return <Navigate to="/login" replace/>;

    return children;
}