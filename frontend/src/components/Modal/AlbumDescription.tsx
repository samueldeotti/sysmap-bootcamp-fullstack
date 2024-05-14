import { Loader2, X } from 'lucide-react';
import { Button } from '../ui/button';
import { AlbumModel } from '@/models/AlbumModel';
import Description from './Description';

interface AlbumDescriptionProps {
  album: AlbumModel;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  handleBuy: () => void;
  loading: boolean;
}

export default function AlbumDescription({
  album,
  setIsOpen,
  handleBuy,
  loading,
}: AlbumDescriptionProps) {
  const artists = () =>
    album.artists
      .reduce((acc, artist) => acc + artist.name + ', ', '')
      .slice(0, -2);

  const getDate = () => {
    const splittedDate = album?.releaseDate?.split('-');

    return splittedDate
      ?.filter((date) => date)
      .reverse()
      .reduce((acc, date) => acc + date + '/', '')
      .slice(0, -1);
  };

  return (
    <div className="flex flex-col gap-2 sm:gap-0 sm:w-1/2 p-4 bg-transparent items-center justify-between sm:relative rounded-b-2xl sm:rounded-e-2xl sm:rounded-none text-black">
      <h1 className="text-lg sm:text-2xl sm:w-4/5 font-bold text-center overflow-hidden line-clamp-2 sm:line-clamp-3 sm:max-h-24">
        {album.name}
      </h1>
      <button
        onClick={() => setIsOpen(false)}
        className="bg-[#F4F4F4] text-white p-1 rounded-[50%] absolute right-2 top-2"
      >
        <X className="text-[#444257] size-5" />
      </button>

      <div className="flex flex-col text-xs gap-2 sm:gap-3 sm:text-sm">
        <Description label="Tipo:" value={album.albumType} />
        <Description label="Artistas:" value={artists()} />
        <Description label="Preço:" value={`R$ ${album.value}`} />
        <Description label="Data de Lançamento:" value={getDate()} />
      </div>

      <Button
        type="submit"
        disabled={loading}
        onClick={handleBuy}
        className={`text-white w-full py-1 rounded-full bg-[#FBBC05] hover:bg-[#FBBC05] font-bold ${loading && 'cursor-not-allowed'
          }`}
      >
        {loading ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : null}
        {loading ? 'Carregando...' : 'Comprar'}
      </Button>
    </div>
  );
}
