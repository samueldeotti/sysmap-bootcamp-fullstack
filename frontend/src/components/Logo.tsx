import logo from '../assets/logo.svg';

export default function Logo({ size }: { size?: string }) {
  return <img src={logo} alt="Bootplay logo" className={`size-${size}`} />;
}
