import { AlbumModel } from '@/models/AlbumModel';
import React, { useState } from 'react';
import Modal from '../Modal/Modal';

export default function Album({album, isCollection }: { album: AlbumModel, isCollection?: boolean; }) {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <div
      style={
        {
          '--bg-fundo': `url(${album.images[0].url})`,
        } as React.CSSProperties
      }
      className="bg-[image:var(--bg-fundo)] bg-cover bg-no-repeat w-36 h-36 sm:w-60 sm:h-60 md:h-72 md:w-72 lg:h-80 lg:w-80 rounded-md "
    >
      <div
        onClick={() => {
          isCollection
            ? window.open(`https://open.spotify.com/album/${album.id}`,'_blank')
            : setIsModalOpen(true);
        }}
        className="flex h-full justify-center items-center backdrop-brightness-50 rounded-md cursor-pointer relative text-white"
      >
        <h1 className="text-lg w-4/5 sm:text-2xl font-semibold text-center overflow-hidden line-clamp-2 sm:line-clamp-3 sm:leading-[2.7rem]">
          {album.name}
        </h1>
        <h2 className="text-lg font-semibold sm:text-2xl absolute bottom-2 right-4">
          R$ {album.value}
        </h2>
      </div>
      {!isCollection && (
        <Modal isOpen={isModalOpen} setIsOpen={setIsModalOpen} album={album} />
      )}
    </div>
  );
}
