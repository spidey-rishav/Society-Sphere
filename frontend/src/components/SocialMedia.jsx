import React from 'react'
import instagram from '../images/Instagram.png'
import linkedin from '../images/Linkedin.png'
import youtube from '../images/Youtube.png'

const SocialMedia = () => {
  return (
    <div className='flex items-center justify-center gap-10 h-10'>
        <a className='w-15'><img src={instagram} alt="instagram" /></a>
        <a className='w-15'><img src={linkedin} alt="linkedin" /></a>
        <a className='w-15'><img src={youtube} alt="youtube" /></a>
    </div>
  )
}

export default SocialMedia