import React from 'react'
import '../../styles/rotate.css'
import SocietySphere from '../../assets/logos/Society Sphere.png'

const Rotation = () => {
  return (
    <div className="carousel pt-10">
        <div className="group">
            {[...Array(6)].map((_, i) => (
                <div className="card" key={i}>
                    <img src={SocietySphere} alt="Society Sphere" />
                </div>
            ))}
        </div>
        <div className="group">
            {[...Array(6)].map((_, i) => (
                <div className="card" key={i}>
                    <img src={SocietySphere} alt="Society Sphere" />
                </div>
            ))}
        </div>
    </div>
  )
}

export default Rotation