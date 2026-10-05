import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import logo from '../../assets/logos/Society_Sphere_Logo-png2.png'
import whatsapp from '../../assets/logos/whatsapp.png'
import arrow from '../../assets/icons/arrow.png'

const Navbar = () => {
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className='bg-[#0b2d35] text-[#ffffff] px-4 md:px-8 py-3 sticky top-0 z-50 shadow-lg'>
      <div className='max-w-7xl mx-auto flex items-center justify-between'>

        {/* Logo */}
        <Link to="/" className='flex items-center'>
          <img src={logo} alt="Logo" className='h-12 md:h-16 w-auto object-contain' />
        </Link>

        {/* Desktop Links */}
        <div className='hidden md:flex items-center space-x-8 text-lg font-medium'>
          <Link className='hover:text-emerald-400 transition-colors duration-200' to="/product">Product</Link>
          <Link className='hover:text-emerald-400 transition-colors duration-200' to="/services">Services</Link>
          <Link className='hover:text-emerald-400 transition-colors duration-200' to="/blog">Blog</Link>
          <Link className='hover:text-emerald-400 transition-colors duration-200' to="/about">About Us</Link>
        </div>

        {/* Desktop Button */}
        <div className='hidden md:block'>
          <button
            onClick={() => navigate('/chat')}
            className='bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] cursor-pointer flex items-center gap-2 hover:scale-105 transition-all duration-300 text-white py-2.5 px-5 rounded-full text-sm font-semibold shadow-md'>
            <img className='h-4 w-4' src={whatsapp} alt="whatsapp"/>
            <span>Chat With Us</span>
            <img src={arrow} alt="arrow" className='h-4 w-4'/>
          </button>
        </div>

        {/* Mobile Hamburger Button */}
        <button
          onClick={() => setIsOpen(!isOpen)}
          className='md:hidden text-white p-2 focus:outline-none'
          aria-label="Toggle Menu"
        >
          {isOpen ? (
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          ) : (
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
            </svg>
          )}
        </button>
      </div>

      {/* Mobile Menu Dropdown */}
      {isOpen && (
        <div className='md:hidden mt-3 pt-3 border-t border-gray-700 flex flex-col space-y-3 pb-3 px-2 text-base font-medium'>
          <Link onClick={() => setIsOpen(false)} className='hover:text-emerald-400 py-1' to="/product">Product</Link>
          <Link onClick={() => setIsOpen(false)} className='hover:text-emerald-400 py-1' to="/services">Services</Link>
          <Link onClick={() => setIsOpen(false)} className='hover:text-emerald-400 py-1' to="/blog">Blog</Link>
          <Link onClick={() => setIsOpen(false)} className='hover:text-emerald-400 py-1' to="/about">About Us</Link>
          <button
            onClick={() => { setIsOpen(false); navigate('/chat'); }}
            className='bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] flex items-center justify-center gap-2 text-white py-2.5 px-4 rounded-full text-sm font-semibold mt-2'>
            <img className='h-4 w-4' src={whatsapp} alt="whatsapp"/>
            <span>Chat With Us</span>
            <img src={arrow} alt="arrow" className='h-4 w-4'/>
          </button>
        </div>
      )}
    </nav>
  )
}

export default Navbar