import React from 'react'
import { useState } from "react";

const Unique = () => {
  const [activeIndex, setActiveIndex] = useState(null);
  
    const data = [
      {
        title: "All-in-One Management",
        content: "Society Sphere brings all essential society operations—like maintenance, communication, and records—into a single platform. This eliminates the need for multiple tools and simplifies overall management.",
      },
      {
        title: "User-Friendly Interface",
        content: "The system is designed with simplicity in mind, making it easy for both residents and administrators to use. Even non-technical users can navigate and perform tasks without confusion.",
      },
      {
        title: "Secure & Reliable",
        content: "Society Sphere ensures data safety through secure authentication and controlled access (e.g., JWT-based security). This protects sensitive information and builds trust among users.",
      },
    ];
  
    const toggle = (index) => {
      setActiveIndex(activeIndex === index ? null : index);
    };
  
  return (
    <>
    <div className='flex justify-center'>
        <div className='py-10 flex flex-col items-center justify-evenly shadow-2xl w-3/4 bg-[#0b2d35] text-[#ffffff] rounded-4xl'>
            <div className='flex flex-col items-center'>
                <h1 className='text-3xl font-bold'>Why choose Society Sphere?</h1>
                <h3 className='text-xl'>Society Sphere is a smart, user-friendly platform that simplifies and centralizes all aspects of housing society management.</h3>
            </div>
            <div className='flex items-center justify-around'>
              <div className="max-w-md mx-auto mt-10 space-y-2">
                {data.map((item, index) => (
                  <div key={index} className="rounded-xl overflow-hidden shadow-sm">
                    
                    {/* Title */}
                    <button
                      onClick={() => toggle(index)}
                      className="w-full text-[#f7f7f7] text-left px-4 py-3 bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] flex justify-between items-center"
                    >
                      <span>{item.title}</span>
                      <span className="text-xl">
                        {activeIndex === index ? "-" : "+"}
                      </span>
                    </button>

                    {/* Content */}
                    <div
                      className={`bg-[#f7f7f7] border-b px-4 transition-all duration-300 ease-in-out overflow-hidden ${
                        activeIndex === index ? "max-h-40 py-3" : "max-h-0"
                      }`}
                    >
                      <p className='text-gray-500'>{item.content}</p>
                    </div>
                  </div>
                ))}
              </div>
              <div className='w-100'></div>
            </div>
        </div>
    </div>
    </>
  )
}

export default Unique