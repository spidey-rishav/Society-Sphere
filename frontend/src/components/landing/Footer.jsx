import React from 'react'
import { useNavigate } from 'react-router-dom'
import SocietySphereLogo from '../../assets/logos/Society_Sphere_Logo-png2.png'
import Sphere from '../../assets/logos/Society Sphere.png'
import whatsapp from '../../assets/logos/whatsapp.png'
import arrow from '../../assets/icons/arrow.png'

const Footer = () => {
    const navigate = useNavigate();
  return (
    <footer className='bg-[#0b2d35] text-white py-10 md:py-16 px-4 border-t border-gray-800'>
        <div className='max-w-5xl mx-auto flex flex-col md:flex-row items-center justify-between gap-6 pb-6 border-b border-gray-700/60'>
            <div className='flex items-center gap-3'>
                <img className='h-12 md:h-16 w-auto object-contain' src={SocietySphereLogo} alt="Logo" />
                <img className='h-8 md:h-10 w-auto object-contain' src={Sphere} alt="Sphere" />
            </div>
            <div>
                <button
                    onClick={() => navigate('/chat')}
                    className='bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] cursor-pointer flex items-center gap-2 hover:scale-105 transition duration-300 text-white py-2.5 px-5 rounded-full text-sm font-semibold shadow-md'>
                    <img className='h-4 w-4' src={whatsapp} alt="whatsapp"/>
                    <span>Chat With Us</span>
                    <img src={arrow} alt="arrow" className='h-4 w-4'/>
                </button>
            </div>
        </div>
        <div className='max-w-5xl mx-auto pt-6 text-center text-xs sm:text-sm text-gray-400'>
          © {new Date().getFullYear()} Society Sphere. All rights reserved.
        </div>
    </footer>
  )
}

export default Footer