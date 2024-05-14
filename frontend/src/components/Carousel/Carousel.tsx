import { AlbumModel } from '@/models/AlbumModel'
import './Caroulsel.css'
import Album from '../Albums/Album'

export default function Carousel({ albums }: { albums: AlbumModel[] }) {

  return (
    <div className="carousel-home flex justify-center h-[160px] sm:h-[260px] md:h-[318px] lg:h-[340px] relative overflow-x-hidden">
      {albums?.slice(0, 10).map((album) => (
        <div className="pr-4 sm:pr-8" key={album.id}>
          <Album album={album} key={album.id} />
        </div>
      ))}

    </div>
  )
}
