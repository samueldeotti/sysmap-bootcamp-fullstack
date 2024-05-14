import { SearchIcon } from "lucide-react";

interface SearchDashbordProps {
  search: string;
  setSearch: (search: string) => void;
  handleSearch: (e: React.FormEvent<HTMLFormElement>) => void;
}

export default function SearchDashbord({ search, setSearch, handleSearch }: SearchDashbordProps) {
  return (
    <form
      action=""
      onSubmit={handleSearch}
      className="flex w-fit h-fit item-center justify-center border-[1px] border-[#CBCAD7] rounded-xl mb-4 max-w-96"
    >
      <label htmlFor="search">
        <input
          type="text"
          name='search'
          value={search}
          data-color-text='#fff'
          data-color-bg='white'
          className="focus:outline-none border-none w-full bg-transparent p-3 rounded-s-xl"
          onChange={(e) => setSearch(e.target.value)}
        />
      </label>
      <button type="submit" className='p-3 focus:outline-none border-none'>
        <SearchIcon />
      </button>
    </form>
  )
}
