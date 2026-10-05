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
    <section className='py-10 md:py-16 flex justify-center px-4'>
        <div className='py-8 px-6 sm:px-10 flex flex-col items-center justify-between shadow-2xl w-full max-w-5xl bg-[#0b2d35] text-[#ffffff] rounded-3xl md:rounded-4xl gap-6'>
            <div className='flex flex-col items-center text-center max-w-2xl'>
                <h2 className='text-2xl sm:text-3xl font-bold mb-2'>Why choose Society Sphere?</h2>
                <p className='text-sm sm:text-base text-gray-200'>Society Sphere is a smart, user-friendly platform that simplifies and centralizes all aspects of housing society management.</p>
            </div>
            
            <div className="w-full max-w-xl mx-auto space-y-3">
              {data.map((item, index) => (
                <div key={index} className="rounded-xl overflow-hidden shadow-sm">
                  
                  {/* Title */}
                  <button
                    onClick={() => toggle(index)}
                    className="w-full text-[#f7f7f7] text-left px-4 py-3 bg-gradient-to-r from-[#5b42e4] via-[#8137e9] to-[#7f32da] flex justify-between items-center text-base sm:text-lg font-medium"
                  >
                    <span>{item.title}</span>
                    <span className="text-xl font-bold">
                      {activeIndex === index ? "−" : "+"}
                    </span>
                  </button>

                  {/* Content */}
                  <div
                    className={`bg-[#f7f7f7] border-b px-4 transition-all duration-300 ease-in-out overflow-hidden ${
                      activeIndex === index ? "max-h-48 py-3" : "max-h-0 py-0"
                    }`}
                  >
                    <p className='text-gray-700 text-sm sm:text-base'>{item.content}</p>
                  </div>
                </div>
              ))}
            </div>
        </div>
    </section>
  )
}

export default Unique