import Background from '@/components/Background';
import Header from '@/components/Header/Header';
import ButtonNavHeader from '@/components/Header/ButtonNavHeader';

export default function Landing() {
  return (
    <Background>
      <Header isLanding />
      <main className="px-2 sm:px-16 md:px-20 lg:px-24 w-[95%] md:w-[85%] lg:w-2/3 lg:gap-8 flex flex-col gap-4">
        <h1 className="text-3xl text-white font-bold w-full sm:text-6xl">A história da música não pode ser esquecida!</h1>
        <p className="text-base w-5/6 text-white sm:text-2xl">Crie já sua conta e curta os sucessos que marcaram os tempos no Vinil.</p>
        <ButtonNavHeader path="/signup" classNames="w-40 bg-[#9EE2FF] text-black sm:py-3">Inscrever-se</ButtonNavHeader>
      </main>
    </Background>
  );
}
