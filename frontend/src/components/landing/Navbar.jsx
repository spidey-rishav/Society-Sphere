import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import logo from '../../assets/logos/Society_Sphere_Logo-png2.png'
import whatsapp from '../../assets/logos/whatsapp.png'
import arrow from '../../assets/icons/arrow.png'

const Navbar = () => {
  const navigate = useNavigate();

  return (
    <div className='flex items-center justify-between px-8 py-4 bg-[#0b2d35] text-[#ffffff]'>

      <img src={logo} alt="Logo" className='h-25 w-auto' />

      <div className='flex items-center space-x-10 text-2xl'>
        <Link className='hover:scale-105 transform duration-200 ease-in-out' to="/product">  Product  </Link>
        <Link className='hover:scale-105 transform duration-200 ease-in-out' to="/services">  Services  </Link>
        <Link className='hover:scale-105 transform duration-200 ease-in-out' to="/blog">  Blog  </Link>
        <Link className='hover:scale-105 transform duration-200 ease-in-out' to="/about">    About Us </Link>

      </div>

      <button
        onClick={() => navigate('/chat')}
        className='bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] cursor-pointer h-15 flex items-center gap-2 hover:bg-blue-700 hover:scale-105 transition duration-300 ease-in-out text-white py-2 px-4 rounded-4xl'>
          <img className='h-5' src={whatsapp} alt="whatsapp"/>Chat With Us<img src={arrow} alt="arrow" className='h-5'/>
      </button>

    </div>
  )
}

export default Navbar