import React from 'react'

export default function Background({ children, blur = false }: { children: React.ReactNode, blur?: boolean }) {
  return (
    <div className="bg-fundo bg-center bg-cover bg-no-repeat">
      <div className={`flex items-center h-screen backdrop-brightness-50 ${blur && "justify-center backdrop-blur-sm"}`}>
        {children}
      </div>
    </div>
  )
}
