import React, { useState } from 'react';
import './input.css'
interface Props {
  children: React.ReactNode;
  type: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  name: string;
  value: string;
  verifyValue: (value: string) => boolean;
}

export default function Input({ children, type, onChange, name, value, verifyValue }: Props) {

  const errors: any = {
    email: 'Email inválido',
    password: 'Precisa de 4 caracteres ou mais',
    name: 'Precisa de 3 caracteres ou mais'
  }

  const isValueValid = verifyValue(value)

  const [isFirstRender, setIsFirstRender] = useState(true)

  const setBorderColor = () => {
    return (isFirstRender || isValueValid) ? 'ring-zinc-900/20' : 'ring-red-500 focus:outline-red-500'
  }

  return (
    <>
      <label className="text-sm font-normal relative mb-2" htmlFor={name}>
        <span className="after:content-['*'] after:ml-0.5 after:text-red-500 block text-sm font-medium text-slate-700 md:text-sm lg:text-base">
          {children}
        </span>
        <input
          name={name}
          id={name}
          type={type}
          required
          value={value}
          onChange={(e) => {
            onChange(e)
            setIsFirstRender(false)
          }}
          className={`bg-zinc-50 w-full p-3 mt-1 rounded-xl ring-1 ${setBorderColor()} mb-5 outline-1 md:text-base `}
        />

        {!isFirstRender && !isValueValid && <span className='absolute bottom-0 left-0 ml-2 text-xs text-red-500'>{errors[name]}</span>}
      </label>
    </>
  );
}
