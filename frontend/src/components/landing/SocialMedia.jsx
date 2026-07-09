import React from 'react'
import instagram from '../../assets/logos/Instagram.png'
import linkedin from '../../assets/logos/Linkedin.png'
import youtube from '../../assets/logos/Youtube.png'

const SocialMedia = () => {
  return (
    <div className='flex items-center justify-center gap-10 h-10 py-20'>
        <a className='w-15 hover:scale-105 transition duration-200 ease-in-out'><img src={instagram} alt="instagram" /></a>
        <a className='w-15 hover:scale-105 transition duration-200 ease-in-out'><img src={linkedin} alt="linkedin" /></a>
        <a className='w-15 hover:scale-105 transition duration-200 ease-in-out'><img src={youtube} alt="youtube" /></a>
    </div>
  )
}

export default SocialMedia