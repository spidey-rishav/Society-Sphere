import React from 'react'
import { useNavigate } from 'react-router-dom'
import SocietySphereLogo from '../../assets/logos/Society_Sphere_Logo-png2.png'
import Sphere from '../../assets/logos/Society Sphere.png'
import whatsapp from '../../assets/logos/whatsapp.png'
import arrow from '../../assets/icons/arrow.png'

const Footer = () => {
    const navigate = useNavigate();
  return (
    <div className='h-150 bg-[#0b2d35] text-white flex items-center justify-center'>
        <div className='h-3/4 w-2/3 rounded-4xl'>
            <div className='h-1/4 w-full flex items-center justify-between px-5 border-b border-yellow-500'>
                <div className='flex items-center'>
                    <img className='h-20' src={SocietySphereLogo} alt="Logo" />
                    <img className='h-10' src={Sphere} alt="Sphere" />
                </div>
                <div>
                    <button
                        onClick={() => navigate('/chat')}
                        className='bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] cursor-pointer h-15 flex items-center gap-2 hover:bg-blue-700 hover:scale-102 transition duration-300 ease-in-out text-white py-2 px-4 rounded-4xl'>
                        <img className='h-5' src={whatsapp} alt="whatsapp"/>Chat With Us<img src={arrow} alt="arrow" className='h-5'/>
                    </button>
                </div>
            </div>
            <div>

            </div>
        </div>
    </div>
  )
}

export default Footer