import { GlobalContext } from "@/GlobalContext"
import { useContext, useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

export default function BookList(){

    const navigate = useNavigate()
    const [bookList, setBookList] = useState([])
    const {loggedIn} = useContext(GlobalContext)

    useEffect( () => {
        async function fetchBooks(){
            await fetch("http://localhost:8080/book")
            .then(response => response.json())
            .then(data => setBookList(data))
        }
        fetchBooks();
        if(!loggedIn){
            navigate("/login")
        }

    }, [])

    return <>
    
    <div>
        <ul className="book-list">
            {bookList.map(book => (
                <div key={book.id}>
                    <li>
                        {book.title}
                        </li>
                        <li>
                            {book.author.firstName} {book.author.lastName}
                        </li>
                        </div>
            ))}
        </ul>
    </div>
    
    </>
}