import { NavLink } from 'react-router-dom'
import ButtonNavHeader from './ButtonNavHeader';
import MenuHeader from './MenuHeader';

export default function NavHeader({ isLanding = false }: { isLanding?: boolean }) {


  return (
    <nav className="flex gap-2 sm:gap-4 items-center ">
      {isLanding ? (
        <>
          <ButtonNavHeader path="/login">Entrar</ButtonNavHeader>
          <ButtonNavHeader path="/signup" classNames="hidden sm:block bg-[#9EE2FF] text-black ">Inscrever-se</ButtonNavHeader>
        </>
      ) : (
        <>
          <NavLink
            to="/albums/my-collection"
            className='md:text-lg lg:text-xl active:font-bold'
            style={({ isActive }) => ({ fontWeight: isActive ? 600 : 500 })}
          >
            Meus Discos
          </NavLink>
          <NavLink
            to="/wallet"
            className='md:text-lg lg:text-xl'
            style={({ isActive }) => ({ fontWeight: isActive ? 600 : 500 })}
          >
            Carteira
          </NavLink>
          <MenuHeader />
        </>
      )}
    </nav>
  )
}
