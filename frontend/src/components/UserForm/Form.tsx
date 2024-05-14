import React, { useState } from 'react'
import Input from '../Input/Input'
import { Button } from '../ui/button'
import { Loader2 } from 'lucide-react'
import { UserData } from './UserForm';


interface FormProps {
  loading: boolean;
  isSignup?: boolean;
  handleSubmit: (event: React.FormEvent<HTMLFormElement>, userData: UserData) => void;

}

export default function Form({ loading, isSignup,
  handleSubmit }: FormProps) {

  const initialUserData = {
    name: '',
    email: '',
    password: '',
  };

  const [userData, setUserData] = useState<UserData>(initialUserData);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setUserData((prevUserData) => ({
      ...prevUserData,
      [name]: value,
    }));
  };

  const verifyName = (name: string) => name.length >= 3;
  const verifyEmail = (email: string) => /\S+@\S+\.\S+/.test(email);
  const verifyPassword = (password: string) => password.length >= 4;

  return (
    <form onSubmit={(e) => handleSubmit(e, userData)} className="flex flex-col mt-2">
      {isSignup && (
        <Input verifyValue={verifyName} type="text" value={userData.name} name="name" onChange={handleChange}>
          Nome Completo
        </Input>
      )}
      <Input type="email" verifyValue={verifyEmail} value={userData.email} name="email" onChange={handleChange}>
        Email
      </Input>
      <Input verifyValue={verifyPassword} type="password" value={userData.password} name="password" onChange={handleChange}>
        Senha
      </Input>

      <Button
        type="submit"
        disabled={loading || !verifyEmail(userData.email) || !verifyPassword(userData.password) || (isSignup && !verifyName(userData.name))}
        className={`p-6 bg-zinc-900 text-white hover:bg-zinc-900/90 transition mb-4 rounded-lg ${loading && 'cursor-not-allowed'} md:text-lg`}
      >
        {loading ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : null}
        {isSignup ? 'Criar conta' : 'Entrar'}
      </Button>
    </form>
  )
}
