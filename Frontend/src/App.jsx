
import {
  BrowserRouter,
  Navigate,
  Route,
  Routes
} from "react-router-dom"
import LoginForm from "./components/LoginForm"
import BookList from "./components/BookList"
import { GlobalProvider, GlobalContext } from "./GlobalContext"

function App() {

  return (
    <>
    <GlobalProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginForm />}/>
          <Route path="/" element={<BookList />}/>
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </BrowserRouter>
    </GlobalProvider>
    
    </>
  )
}

export default App
