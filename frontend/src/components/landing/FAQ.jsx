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
    <section className="w-full max-w-4xl mx-auto px-4 mt-8 md:mt-12 space-y-3 pb-12 md:pb-20">
      <h2 className='text-2xl sm:text-3xl font-bold text-gray-900 text-center md:text-left mb-6'>FAQs on Society Sphere</h2>
      {data.map((item, index) => (
        <div key={index} className="rounded-xl overflow-hidden shadow-sm border border-gray-200">
          
          {/* Title */}
          <button
            onClick={() => toggle(index)}
            className="w-full text-black bg-gray-50 text-left text-base sm:text-lg font-medium px-4 sm:px-5 py-4 hover:bg-gray-100 flex justify-between items-center transition-colors"
          >
            <span className="pr-4">{item.title}</span>
            <span className="text-xl font-bold text-indigo-600 flex-shrink-0">
              {activeIndex === index ? "−" : "+"}
            </span>
          </button>

          {/* Content */}
          <div
            className={`bg-white px-4 sm:px-5 transition-all duration-300 ease-in-out overflow-hidden ${
              activeIndex === index ? "max-h-60 py-4 border-t border-gray-100" : "max-h-0 py-0"
            }`}
          >
            <p className="text-gray-600 text-sm sm:text-base leading-relaxed">{item.content}</p>
          </div>

        </div>
      ))}
    </section>
  );
};

export default FAQ;