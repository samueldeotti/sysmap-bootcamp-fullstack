import { AlbumModel } from '@/models/AlbumModel';
import React, { useEffect, useState } from 'react';
import Carousel from '@/components/Carousel/Carousel';
import Albums from '@/components/Albums/Albums';
import toast from 'react-hot-toast';
import { getAlbums } from '@/services/albumService/AlbumService';
import HeaderDashboard from './HeaderDashboard';
import SearchDashbord from './SearchDashbaord';

export function Dashboard() {
  const [startAlbums, setStartAlbums] = useState<AlbumModel[]>([]);
  const [albums, setAlbums] = useState<AlbumModel[]>([]);
  const [search, setSearch] = useState('');
  const [isInputEmpty, setIsInputEmpty] = useState(true);
  const [loading, setLoading] = useState(true);

  const getData = async (searhInput: string, isFirstCall: boolean) => {
    try {
      setLoading(true);
      const resp = await getAlbums(searhInput);
      if (isFirstCall) {
        setStartAlbums(resp);
        return;
      }
      setAlbums(resp);
      setIsInputEmpty(false);
    } catch (error: any) {
      toast.error(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData('Rock', true);
  }, []);

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!search) {
      setIsInputEmpty(true);
      return;
    }
    getData(search, false);
  };

  return (
    <>
      <HeaderDashboard />

      <main className="flex flex-col items-center justify-center h-full my-4 text-white px-2 sm:px-16 md:px-20 lg:px-24">
        <SearchDashbord
          search={search}
          setSearch={setSearch}
          handleSearch={handleSearch}
        />

        <section className="flex flex-col gap-5 w-full overflow-hidden mb-8">
          {isInputEmpty && (<h2 className="text-3xl sm:text-5xl font-bold">Trends</h2>)}
          {loading ? (
            <p className="text-2xl">Carregando...</p>
          ) : isInputEmpty ? (
            <Carousel albums={startAlbums} />
          ) : (
            <Albums albums={albums} />
          )}
        </section>
      </main>
    </>
  );
}
