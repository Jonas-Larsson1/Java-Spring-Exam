import { GlobalContext } from "@/GlobalContext";
import { useContext, useEffect, useState } from "react"
import { Link, useNavigate } from "react-router-dom";


export default function LoginForm(){

    const navigate = useNavigate();
    const [memberNumber, setMemberNumber] = useState("");
    const [member, setMember] = useState([])
    const {setLoggedIn} = useContext(GlobalContext)

    useEffect(() => {
        console.log(memberNumber)
    }, [memberNumber])

    

    async function login(){
          const response = await fetch(`http://localhost:8080/members/${memberNumber}`);
          const result = await response.json().then(data => setMember(data))

          if(response.status === 200){
            navigate("/")
            setLoggedIn(true)
          }
        

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