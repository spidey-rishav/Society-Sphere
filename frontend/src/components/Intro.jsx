import React from 'react'
import icon from '../images/Society_Sphere_icon-png.png'
import arrow from '../assets/arrow.png'
import { useNavigate } from 'react-router-dom'

const Intro = () => {
  const navigate = useNavigate();
  return (
    <div className='flex items-center justify-around'>
        <div className='flex flex-col items-start'>
            <h1 className='text-5xl font-bold'>Smart Tech. Seamless Living. Solid Society.</h1>
            <p className='text-2xl'>Smart management for a better living experience.</p>
            <button onClick={() => {navigate("/")}} className='flex items-center gap-2 h-15 w-auto bg-green-500 rounded-2xl px-5 py-1 my-5 text-xl font-bold hover:bg-green-600'> Book Now <img className='h-8' src={arrow} alt="arrow" /></button>
        </div>
        <img src={icon} alt="icon" className='h-150 w-auto'/>
    </div>
  )
}

export default Intro