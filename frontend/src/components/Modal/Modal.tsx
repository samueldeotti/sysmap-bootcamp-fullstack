import { AlbumModel, CollectionAlbumModel } from '@/models/AlbumModel';
import { getUserAlbums } from '@/services/albumService/AlbumService';
import { albumApi, userApi } from '@/services/apiService';
import toast from 'react-hot-toast';
import AlbumDescription from './AlbumDescription';
import { useState } from 'react';

interface ModalProps {
  isOpen: boolean;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  album: AlbumModel;
}

export default function Modal({ isOpen, setIsOpen, album }: ModalProps) {
  const [loading, setLoading] = useState(false);

  // I put this in because I wanted to simulate what it would be like in production, but when I release it to production, remove it
  // I refer to setTimeOut in the following way:

  const handleBuy = async () => {
    try {
      setLoading(true);
      const userAlbum: CollectionAlbumModel[] = await getUserAlbums();

      if (userAlbum?.some((userAlbum) => userAlbum.idSpotify === album.id)) {
        setTimeout(() => {
          toast.error('Você já possui esse album');
        }, 1000);
        return;
      }

      const resp = await userApi.get('/wallet');
      if (resp.data.balance < album.value) {
        setTimeout(() => {
          toast.error('Saldo insuficiente');
          toast.error('Album não comprado');
        }, 1000);
        return;
      }

      await albumApi.post('/albums/sale', {
        name: album.name,
        idSpotify: album.id,
        artistName: album.artists[0].name,
        imageUrl: album.images[0].url,
        value: album.value,
      });

      setTimeout(() => {
        toast.success('Album comprado com sucesso');
      }, 1000);
    } catch (error: any) {
      setTimeout(() => {
        toast.error('Algo deu errado');
        toast.error('Album não comprado');
      }, 1000);
    } finally {
      setTimeout(() => {
        setLoading(false);
      }, 1000);
    }
  };

  return (
    isOpen && (
      <div className="fixed top-0 left-0 w-full h-full z-50 backdrop-blur-sm">
        <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-2xl flex flex-col sm:flex-row sm:w-3/4 sm:h-3/4 max-w-[70%] sm:max-w-[600px] sm:max-h-[320px] max-h-fit bg-white">
          <img
            src={album.images[0].url}
            alt="Album cover"
            className="rounded-t-2xl h-full sm:rounded-s-2xl sm:rounded-none sm:w-1/2 "
          />

          <AlbumDescription
            album={album}
            setIsOpen={setIsOpen}
            handleBuy={handleBuy}
            loading={loading}
          />
        </div>
      </div>
    )
  );
}
