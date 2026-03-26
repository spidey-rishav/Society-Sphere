import React from 'react'

import { useState } from "react";

const FAQ = () => {
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
    <div className="max-w-md mx-auto mt-10 space-y-2">
      {data.map((item, index) => (
        <div key={index} className="border rounded-xl overflow-hidden shadow-sm">
          
          {/* Title */}
          <button
            onClick={() => toggle(index)}
            className="w-full text-black text-left px-4 py-3 bg-gray-100 hover:bg-gray-200 flex justify-between items-center"
          >
            <span>{item.title}</span>
            <span className="text-xl">
              {activeIndex === index ? "-" : "+"}
            </span>
          </button>

          {/* Content */}
          <div
            className={`text-[#ffffff] px-4 transition-all duration-300 ease-in-out overflow-hidden ${
              activeIndex === index ? "max-h-40 py-3" : "max-h-0"
            }`}
          >
            <p className="text-gray-600">{item.content}</p>
          </div>

        </div>
      ))}
    </div>
  );
};

export default FAQ;