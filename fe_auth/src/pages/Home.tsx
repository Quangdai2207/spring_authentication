import {useAuth} from "../context/AuthContext.tsx";

function Home() {
    const {setIsAuthenticated} = useAuth();
    return (<>
        <h1>Home Page</h1>

        <button type="button" onClick={async () => {
            const res = await fetch("http://localhost:8080/api/v1/auth/logout", {
                method: "GET",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                }
            })
            const data = await res.json();
            const {status} = data;
            console.log("response: ", data);
            if (status === 200) setIsAuthenticated(false);
        }}>
            Logout
        </button>
    </>);
}

export default Home;