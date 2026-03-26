import React from 'react'
import FAQ from './FAQ'

const Unique = () => {
  return (
    <>
    <div className='flex justify-center'>
        <div className='py-10 flex flex-col items-center justify-evenly shadow-2xl w-3/4 bg-[#0b2d35] text-[#ffffff] rounded-4xl'>
            <div className='flex flex-col items-center'>
                <h1 className='text-3xl font-bold'>Why choose Society Sphere?</h1>
                <h3 className='text-xl'>Society Sphere is a smart, user-friendly platform that simplifies and centralizes all aspects of housing society management.</h3>
            </div>
            <div className='flex items-center justify-around'>
              <div className=''><FAQ/></div>
              <div className='w-100'></div>
            </div>
        </div>
    </div>
    </>
  )
}

export default Unique