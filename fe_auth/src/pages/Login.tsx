import {type ChangeEvent, useState} from "react";
import * as React from "react";
import {useAuth} from "../context/AuthContext.tsx";

interface RequestLogin {
    email: string;
    password: string;
}

interface IState {
    isLoading: boolean;
    message: string;
    errorMessage: string;
    body: RequestLogin;
}

function Login() {
    const {setIsAuthenticated} = useAuth();
    const [state, setState] = useState<IState>({
        isLoading: false,
        message: "",
        errorMessage: "",
        body: {
            email: "",
            password: ""
        }
    });

    // ✅ handle input change
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;

        setState(prev => ({
            ...prev,
            body: {
                ...prev.body,
                [name]: value
            }
        }));
    };

    // ✅ submit login
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const endpointLogin = "http://localhost:8080/api/v1/auth/login";

        try {
            setState(prev => ({...prev, isLoading: true, errorMessage: ""}));

            const response = await fetch(endpointLogin, {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(state.body)
            });

            const data = await response.json();
            console.log("Response:", data);

            const {status, message} = data;

            switch (status) {
                case 400:
                    setState(prev => ({
                        ...prev,
                        errorMessage: message,
                        isLoading: false
                    }));
                    break;

                case 200:
                    setIsAuthenticated(status === 200)
                    setState(prev => ({
                        ...prev,
                        message: "Login success",
                        errorMessage: "",
                        isLoading: true
                    }));
                    break;
                default:
                    break;
            }
        } catch (e: any) {
            setState(prev => ({
                ...prev,
                errorMessage: e.message,
                message: "",
                isLoading: false
            }));
        }
    };

    const {isLoading, message, errorMessage} = state;

    return (
        <>
            <h1>Login Account</h1>

            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email</label>
                    <input
                        name="email" //
                        placeholder="example@gmail.com"
                        onChange={handleChange}
                    />
                </div>

                <div>
                    <label>Password</label>
                    <input
                        name="password"
                        type="password"
                        placeholder="********"
                        onChange={handleChange}
                    />d
                </div>

                <button type="submit" disabled={isLoading}>
                    {isLoading ? "Loading..." : "Submit"}
                </button>
            </form>

            {message && <p style={{color: "green"}}>{message}</p>}
            {errorMessage && <p style={{color: "red"}}>{errorMessage}</p>}
        </>
    );
}

export default Login;