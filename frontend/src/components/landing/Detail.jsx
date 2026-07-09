import React from 'react'
import adImage1 from '../../assets/images/ad image 1.png'
import adImage2 from '../../assets/images/ad image 2.png'
import arrow from '../../assets/icons/arrow.png'
import adVideo from '../../assets/videos/Realistic_App_Demo_Video_Request.mp4'

const Detail = () => {
  return (
    <>
    <div className='flex flex-col items-center my-5'>
        <h2 className='text-2xl'>Society Sphere App</h2>
        <h1 className='text-3xl font-bold'>Seamless living starts here.</h1>
        <h1 className='text-2xl'>Welcome home—your community, connected and at your fingertips.</h1>
    </div>
    <div className='flex justify-center items-center gap-20 py-5'>
        <div className='bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] h-130 w-120 flex flex-col items-center rounded-4xl gap-5 border-white border-1 hover:border-black duration-100 ease-in'>
          <img className='rounded-t-4xl' src={adImage1} alt="ad Image 1" />
          <button className='bg-[#56bd41] flex items-center justify-evenly h-15 w-50 rounded-4xl text-[#ffffff] hover:bg-[#1f9e4e] duration-100 ease-in hover:border-1 border-black text-xl font-bold'>Learn More <img className='h-10' src={arrow} alt="arrow" /></button>
        </div>

        <div className='bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] h-130 w-120 flex flex-col items-center rounded-4xl gap-5 border-white border-1 hover:border-black duration-100 ease-in'>
          <img className='rounded-t-4xl' src={adImage2} alt="ad Image 2" />
          <button className='bg-[#56bd41] flex items-center justify-evenly h-15 w-50 rounded-4xl text-[#ffffff] hover:bg-[#1f9e4e] duration-100 ease-in hover:border-1 border-black text-xl font-bold'>Learn More <img className='h-10' src={arrow} alt="arrow" /></button>
        </div>
    </div>
    <div className='h-200 w-full pt-20 px-20 flex flex-col items-center justify-start gap-10'>
        <h1 className='text-4xl font-bold'> Smart management for a better living experience </h1>
        <div className='h-200 w-3/4 bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] rounded-4xl shadow-2xl'>
          <video className='rounded-4xl' controls muted loop width="100%" src={adVideo} type="video/mp4">
            Your browser does not support the video tag.
          </video>
        </div>
    </div>
    </>
  )
}

export default Detail