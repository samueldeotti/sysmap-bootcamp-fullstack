import { useState } from 'react';
import { LogOutIcon } from 'lucide-react';
import avatar from '../../assets/avatar.jpg';
import { useAuth } from '@/hooks/UseAuth';

export default function MenuHeader() {
  const { logout } = useAuth();
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const handleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <div
      onClick={ handleMenu }
      className="relative "
      onBlur={ handleMenu }
      tabIndex={ 0 }
      key="header-div"
    >
      <img
        className="size-10 rounded-[50%] cursor-pointer"
        src={ avatar }
        alt="User Avatar"
      />
      {isMenuOpen && (
        <div
          className="absolute bg-white p-2 rounded-lg cursor-pointer w-[100px] right-0 hover:bg-white/80 transition"
          onClick={ logout }
        >
          <div className="flex gap-2 text-[#807f88] w-full">
            <LogOutIcon />
            {' '}
            Sair
          </div>
        </div>
      )}
    </div>
  );
}
