'use client';

import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    router.push('/welcome')
  }, [])
  
  return (
    <div>
      <h1>Home Page</h1>
    </div>
  );
}
