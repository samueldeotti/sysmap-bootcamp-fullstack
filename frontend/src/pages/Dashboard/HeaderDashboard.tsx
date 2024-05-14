import Header from '@/components/Header/Header';

const { innerHeight } = window;

export default function HeaderDashboard() {
  return (
    <div className="bg-fundoHeader bg-center bg-cover bg-no-repeat relative overflow-hidden" style={ { height: innerHeight < 400 ? '100vh' : '50vh' } }>
      <div className="flex flex-col items-center h-full sm:gap-10 backdrop-brightness-50 relative">
        <Header />
        <div className="flex flex-col self-start ml-2 sm:ml-4 md:ml-6 lg:ml-10 gap-2 text-white w-4/5 absolute bottom-14 max-w-[560px]">
          <h1 className="text-3xl sm:text-4xl sm:w-5/6 font-semibold">
            A história da música não pode ser esquecida!
          </h1>
          <p className="text-xl w-4/5 sm:text-2xl">Sucessos que marcaram o tempo!!!</p>
        </div>
        <div className="absolute bottom-[-10px] left-0 right-0 h-16 bg-gradient-to-t from-[#19181F] to-transparent" />
      </div>
    </div>
  );
}
