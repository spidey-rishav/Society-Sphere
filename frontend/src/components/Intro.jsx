import React from 'react'
import icon from '../images/Society_Sphere_icon-png.png'

const Intro = () => {
  return (
    <div className='flex items-center justify-around'>
        <div className='flex flex-col'>
            <h1 className='text-5xl font-bold'>Smart Tech. Seamless Living. Solid Society.</h1>
            <p className='text-2xl'>Smart management for a better living experience.</p>
        </div>
        <img src={icon} alt="icon" className='h-150 w-auto'/>
    </div>
  )
}

export default Intro