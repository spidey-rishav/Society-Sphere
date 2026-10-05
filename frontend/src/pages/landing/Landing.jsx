import React from 'react'
import Navbar from '../../components/landing/Navbar'
import Intro from '../../components/landing/Intro'
import Detail from '../../components/landing/Detail'
import SocialMedia from '../../components/landing/SocialMedia'
import Rotation from '../../components/landing/Rotation'
import Unique from '../../components/landing/Unique'
import FAQ from '../../components/landing/FAQ'
import Footer from '../../components/landing/Footer'
import About from '../../components/landing/About'
import Features from '../../components/landing/Features'
import Contact from '../../components/landing/Contact'

const Landing = () => {
  return (
    <div className="min-h-screen w-full overflow-x-hidden bg-white text-gray-900">
        <Navbar/>
        <Intro/>
        <About/>
        <Features/>
        <Contact/>
        <Unique/>
        <SocialMedia/>
        <FAQ/>
        <Footer/>
    </div>
  )
}

export default Landing