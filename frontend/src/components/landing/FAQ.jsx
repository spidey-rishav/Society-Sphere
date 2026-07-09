import React from 'react'

import { useState } from "react";

const FAQ = () => {
  const [activeIndex, setActiveIndex] = useState(null);

  const data = [
    {
      title: "What is Society Sphere?",
      content: "Society Sphere is a smart housing management system designed to simplify and digitize the operations of residential societies. It helps manage maintenance, communication, records, and more—all in one platform.",
    },
    {
      title: "Who can use Society Sphere?",
      content: "The system is designed with simplicity in mind, making it easy for both residents and administrators to use. Even non-technical users can navigate and perform tasks without confusion.",
    },
    {
      title: "What features does Society Sphere offer?",
      content: "It includes features like maintenance tracking, announcements, complaint management, resident records, and more. All functionalities are integrated to provide a seamless user experience.",
    },
    {
      title: "Is Society Sphere easy to use?",
      content: "Yes, the platform is built with a user-friendly interface that is simple to navigate. Even users with minimal technical knowledge can use it comfortably.",
    },
    {
      title: "How secure is Society Sphere?",
      content: "Society Sphere uses secure authentication and access control to protect user data. This ensures that sensitive information remains safe and accessible only to authorized users.",
    },
    {
      title: "Can Society Sphere be customized?",
      content: "Yes, the system can be customized according to the specific needs of a society. This flexibility makes it suitable for different types and sizes of communities.",
    },
    {
      title: "Is Society Sphere affordable?",
      content: "Society Sphere is designed to be a cost-effective solution compared to other complex platforms. It provides essential features without unnecessary expenses.",
    },
    {
      title: "How does Society Sphere improve communication?",
      content: "It allows instant sharing of notices, updates, and announcements within the society. This ensures that all residents stay informed in real time.",
    },
    {
      title: "Can residents raise complaints or requests?",
      content: "Yes, residents can easily submit complaints or service requests through the platform. Admins can track and resolve them efficiently.",
    },
    {
      title: "How can I get started with Society Sphere?",
      content: "You can sign up through the website and set up your society profile. Once registered, you can start managing your society digitally right away.",
    },
  ];

  const toggle = (index) => {
    setActiveIndex(activeIndex === index ? null : index);
  };

  return (
    <div className="w-3/4 mx-auto mt-10 space-y-2 pb-20">
      <h1 className='text-3xl font-bold'>FAQs on Society Sphere</h1>
      {data.map((item, index) => (
        <div key={index} className="rounded-xl overflow-hidden shadow-md border-b border-[#0b2d35] shadow-md active:scale-101 transform duration-200 ease-in-out">
          
          {/* Title */}
          <button
            onClick={() => toggle(index)}
            className="w-full text-black bg-gray-100 text-left text-xl px-4 py-5 hover:bg-gray-200 flex justify-between items-center"
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