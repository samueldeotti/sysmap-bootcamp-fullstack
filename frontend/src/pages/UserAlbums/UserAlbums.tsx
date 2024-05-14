import Albums from '@/components/Albums/Albums';
import Header from '@/components/Header/Header';
import { AlbumModel, CollectionAlbumModel } from '@/models/AlbumModel';
import { getUserAlbums } from '@/services/albumService/AlbumService';
import { useEffect, useState } from 'react';
import toast from 'react-hot-toast';
import StatsAlbums from './StatsAlbums';

export default function UserAlbums() {
  const [userAlbums, setUserAlbums] = useState<AlbumModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  const collectionAlbumToAlbum = (albums: CollectionAlbumModel[]) => {
    return albums.map((album) => {
      return {
        id: album.idSpotify,
        name: album.name,
        value: album.value,
        externalUrls: {
          externalUrls: {
            spotify: `https://open.spotify.com/album/${album.idSpotify}`,
          },
        },
        images: [{ url: album.imageUrl }],
        artists: [{ name: album.artistName }],
      };
    });
  };

  useEffect(() => {
    const getData = async () => {
      try {
        setLoading(true);
        const resp = await getUserAlbums();
        const albums = collectionAlbumToAlbum(resp);
        setUserAlbums(albums as AlbumModel[]);
      } catch (error: any) {
        toast.error(error.message);
      } finally {
        setTimeout(() => {
          setLoading(false);
        }, 1000);
      }
    };
    getData();
  }, []);

  const getSpentAmount = (albums: AlbumModel[]) =>
    albums.reduce((acc, album) => acc + album.value, 0).toFixed(2);

  return (
    <>
      <Header />
      <main className="px-2 sm:px-16 md:px-20 lg:px-24 sm:m-auto flex flex-col gap-12 mt-14 sm:mt-28">
        <h1 className="font-bold text-white text-4xl">Meus Discos</h1>

        <StatsAlbums
          userAlbumQuantity={userAlbums.length}
          userSpentAmout={getSpentAmount(userAlbums)}
        />
        {loading ? (
          <p className="text-white w-full text-3xl p-4 rounded-xl">
            Carregando...
          </p>
        ) : userAlbums.length === 0 ? (
          <p className="text-white w-full text-3xl p-4 rounded-xl">
            Você não possui nenhum disco
          </p>
        ) : (
          <Albums isCollection albums={userAlbums} />
        )}
      </main>
    </>
  );
}
