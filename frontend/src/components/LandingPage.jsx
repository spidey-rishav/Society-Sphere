import React from 'react'
import Navbar from './navbar'
import Intro from './Intro'
import Detail from './Detail'
import SocialMedia from './SocialMedia'
import Rotation from './Rotation'
import Unique from './Unique'
import FAQ from './FAQ'

const LandingPage = () => {
  return (
    <>
        <Navbar/>
        <Intro/>
        <Detail/>
        <Rotation/>
        <Unique/>
        <SocialMedia/>
    </>
  )
}

export default LandingPage