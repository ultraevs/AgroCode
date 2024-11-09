import { Comfortaa } from 'next/font/google'

const comfortaa = Comfortaa({
  subsets: ['latin'],
  display: 'swap',
})

export const comfortaaFontClassName = comfortaa.className;