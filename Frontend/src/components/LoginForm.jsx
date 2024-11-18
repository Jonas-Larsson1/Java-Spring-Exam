import { useEffect, useState } from "react"
import { Link } from "react-router-dom";


export default function LoginForm(){

    const [loans, setLoans] = useState([]);
    const [memberNumber, setMemberNumber] = useState("");
    const [member, setMember] = useState([])

    useEffect(() => {
        console.log(memberNumber)
    }, [memberNumber])

    async function login(){
            fetch(`http://localhost:8080/member/${memberNumber}`)
            .then(response => console.log(response.json()))
            .then(data => setMember(data) )
    }

    return(
        <>
        <div id="loginForm">
        <div>Login</div>
        <input type="text" 
        placeholder="membernumber" 
        onChange={(e) => setMemberNumber(e.target.value)}/>
        <button type="submit" 
        onClick={login}>Submit</button>
        </div>
        </>
    )
}