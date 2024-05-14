import { NavLink } from 'react-router-dom';
import NavHeader from './NavHeader';
import Logo from '../Logo';


export default function Header({ isLanding = false }: { isLanding?: boolean }) {
  return (
    <header className={`flex justify-between w-full text-white ${isLanding && "absolute top-0"} p-2 sm:px-16 md:px-20 lg:px-24 bg-white/30 `}>
      <NavLink
        to={isLanding ? "/" : "/dashboard"}
        className='flex items-center gap-2'
        style={({ isActive }) => ({ fontWeight: isActive ? 600 : 500 })}
      >
        <Logo />
        <p className='md:text-lg lg:text-xl'>BootPlay</p>
      </NavLink>
      <NavHeader isLanding={isLanding} />
    </header>
  );
}