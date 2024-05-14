import React from 'react'
import { NavLink } from 'react-router-dom'

interface ButtonNavHeaderProps {
  path: string;
  classNames?: string;
  children: React.ReactNode;
}

export default function ButtonNavHeader({ path, classNames = '', children }: ButtonNavHeaderProps) {
  return (
    <NavLink to={path} className={`${classNames ? classNames : 'bg-black text-white'} py-2 rounded-3xl text-center w-24 sm:w-40 ${classNames}`}>{children}</NavLink >
  )
}
