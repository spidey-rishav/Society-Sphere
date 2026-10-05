import React from 'react'
import icon from '../../assets/images/Society_Sphere_icon-png.png'
import arrow from '../../assets/icons/arrow.png'
import { useNavigate } from 'react-router-dom'

const Intro = () => {
  const navigate = useNavigate();
  return (
    <section className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 md:py-20 flex flex-col-reverse md:flex-row items-center justify-between gap-8 md:gap-12 overflow-hidden'>
      <div className='flex flex-col items-center md:items-start text-center md:text-left max-w-xl'>
        <h1 className='text-3xl sm:text-4xl md:text-5xl lg:text-6xl font-extrabold text-gray-900 leading-tight'>
          Smart Tech. <br className="hidden sm:inline" />Seamless Living. <br className="hidden sm:inline" />Solid Society.
        </h1>
        <p className='text-lg sm:text-xl md:text-2xl text-gray-600 mt-4 font-normal'>
          Smart management for a better living experience.
        </p>
        <button 
          onClick={() => navigate("/society")} 
          className='flex items-center cursor-pointer gap-3 bg-[#56bd41] rounded-full px-6 py-3.5 mt-6 text-lg md:text-xl font-bold hover:bg-[#347127] text-white hover:scale-105 transition duration-300 ease-in-out shadow-lg'>
          <span>Get Started</span>
          <img className='h-5 w-auto' src={arrow} alt="arrow" />
        </button>
      </div>

      <div className='flex justify-center w-full md:w-auto'>
        <img 
          src={icon} 
          alt="Society Sphere Icon" 
          className='h-auto w-64 sm:w-80 md:w-[450px] lg:w-[500px] max-w-full object-contain drop-shadow-xl'
        />
      </div>
    </section>
  )
}

export default Intro