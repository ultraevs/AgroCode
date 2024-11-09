import { PropsWithChildren } from 'react';
import styles from './button.module.scss';
import { interFontClassName } from '@/app/fonts/inter';

interface ButtonProps {
  buttonType: 'primary' | 'border';
  onClick?: () => void;
}

export function Button({
  buttonType,
  children,
  ...props
}: PropsWithChildren<ButtonProps>) {
  const buttonClassName =
    buttonType === 'primary' ? styles.primary : styles.border;
  return <button className={`${buttonClassName} ${interFontClassName}`} {...props}>{children}</button>;
}
