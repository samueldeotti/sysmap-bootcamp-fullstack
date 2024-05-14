import React from 'react'
import background from '../assets/init_background.jpg'

export default function Background({ children, blur = false }: { children: React.ReactNode, blur?: boolean }) {
  return (
    <div className="bg-center bg-cover bg-no-repeat" style={{ backgroundImage: `url(${background})` }}>
      <div className={`flex items-center h-screen backdrop-brightness-50 ${blur && "justify-center backdrop-blur-sm"}`}>
        {children}
      </div>
    </div>
  )
}
