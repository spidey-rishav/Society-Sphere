import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import logo from '../images/Society_Sphere_Logo-png2.png'

const Navbar = () => {
  const navigate = useNavigate();

  return (
    <div className='flex items-center justify-between px-8 py-4 bg-slate-800'>

      <img src={logo} alt="Logo" className='h-10 w-auto' />

      <div className='flex items-center space-x-6'>
        <Link to="/product">  Product  </Link>
        <Link to="/about">    About Us </Link>
        <Link to="/login">    Login    </Link>
      </div>

      <button
        onClick={() => navigate('/chat')}
        className='bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded'>
        Chat With Us
      </button>

    </div>
  )
}

export default Navbar