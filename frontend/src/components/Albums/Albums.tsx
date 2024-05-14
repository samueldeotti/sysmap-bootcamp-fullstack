import { AlbumModel } from '@/models/AlbumModel';
import Album from './Album';

export default function Albums({albums, isCollection}: {albums: AlbumModel[], isCollection?: boolean}) {
  return (
    <section className="flex flex-wrap h-full justify-center gap-4">
      {albums?.map((album) => (
        <Album isCollection={isCollection} key={album.id} album={album} />
      ))}
    </section>
  );
}
