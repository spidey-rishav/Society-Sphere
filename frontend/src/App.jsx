import './App.css'
import {BrowserRouter, Routes, Route} from "react-router-dom"
import LandingPage from './pages/landing/Landing'

function App() {

  return (
    <>
    <BrowserRouter>
     <Routes>
      <Route index element={<LandingPage/>}/>
     </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
