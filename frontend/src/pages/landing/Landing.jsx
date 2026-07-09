import React from 'react'
import Navbar from '../../components/landing/Navbar'
import Intro from '../../components/landing/Intro'
import Detail from '../../components/landing/Detail'
import SocialMedia from '../../components/landing/SocialMedia'
import Rotation from '../../components/landing/Rotation'
import Unique from '../../components/landing/Unique'
import FAQ from '../../components/landing/FAQ'
import Footer from '../../components/landing/Footer'

const Landing = () => {
  return (
    <>
        <Navbar/>
        <Intro/>
        <Detail/>
        <Rotation/>
        <Unique/>
        <SocialMedia/>
        <FAQ/>
        <Footer/>
    </>
  )
}

export default Landing