import {Navigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext.tsx";
import type {JSX} from "react";


export default function HomeRedirect({children}: { children: JSX.Element }) {

    const {loading, isAuthenticated} = useAuth();

    if (loading) return <div>Loading...</div>;

    if (isAuthenticated) return <Navigate to="/" replace/>;

    return children;
}