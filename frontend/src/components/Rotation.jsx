import React from 'react'
import '../Styles/rotate.css'
import SocietySphere from '../images/Society Sphere.png'

const Rotation = () => {
  return (<div className="carousel">
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