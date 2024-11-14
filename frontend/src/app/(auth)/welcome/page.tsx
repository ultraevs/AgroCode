'use client';

import { comfortaaFontClassName } from '@/app/fonts/comfortaa';
import Image from 'next/image';
import styles from './page.module.scss';
import logo from '../../../../public/images/logo.png';
import { Button } from '@/ui';
import Link from 'next/link';
import { routes } from '@/consts/routes';

export default function Welcome() {
  return (
    <div className={`${styles.wrapper} ${comfortaaFontClassName}`}>
      <div className={styles.wrapper__image}></div>
      <div className={styles.wrapper__bg}></div>
      <div className={styles.wrapper__content}>
        <Image src={logo} alt="logo" />
        <h1>MooMatch</h1>
      </div>
      <div className={styles.wrapper__btns}>
        <Button buttonType="primary">Войти</Button>

        <Link href={routes.auth.signup}>
          <Button buttonType="border">Регистрация</Button>
        </Link>
      </div>
    </div>
  );
}
