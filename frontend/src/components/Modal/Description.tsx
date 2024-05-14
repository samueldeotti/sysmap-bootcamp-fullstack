export default function Description({ label, value }: { label: string, value: number | string }) {
  return (
    <p className='overflow-hidden whitespace-nowrap h-4 sm:h-6 max-w-[202px] sm:max-w-[260px] text-ellipsis'>
      <span className='font-semibold'>{label}</span> {value}
    </p>
  )
}
