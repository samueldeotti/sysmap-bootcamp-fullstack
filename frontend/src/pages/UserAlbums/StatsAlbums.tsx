import StatsAlbumCard from './StatsAlbumCard';
import { DollarSignIcon, FileVideoIcon } from 'lucide-react';

interface StatsAlbumsProps {
  userAlbumQuantity: number;
  userSpentAmout: string;
}

export default function StatsAlbums({ userAlbumQuantity, userSpentAmout }: StatsAlbumsProps) {
  return (
    <section className="text-black flex flex-wrap sm:flex-nowrap gap-4 sm:gap-6 w-full max-w-2xl ">

      <StatsAlbumCard label="Total de Albums" value={userAlbumQuantity}>
        <FileVideoIcon className="h-8 w-8 text-white" />
      </StatsAlbumCard>

      <StatsAlbumCard
        label="Valor Investido"
        value={`R$ ${userSpentAmout}`}
      >
        <DollarSignIcon className="h-8 w-8 text-white" />
      </StatsAlbumCard>
    </section>
  );
}
