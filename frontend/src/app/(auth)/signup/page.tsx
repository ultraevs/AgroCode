'use client';

import { comfortaaFontClassName } from '@/app/fonts/comfortaa';
import Image from 'next/image';
import { Formik, Form, Field } from 'formik';
import styles from './page.module.scss';
import logo from '../../../../public/images/logo.png';
import { Button } from '@/ui';
import { useAppDispatch } from "@/app/store/hooks";
import { useRouter } from 'next/navigation';
import { usePathname } from 'next/navigation';
import Link from 'next/link';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { useState } from 'react';

export default function SignUp() {
    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
        setShowPassword((prevShowPassword) => !prevShowPassword);
    };
    // const dispatch = useAppDispatch();
    const router = useRouter();
    // const pathname = usePathname();

    // const isLinkActive = (route: string): boolean => route === pathname;

    const initialValues = {
        email: "",
        name: "",
        password: "",
    };

    return (
        <div className={`${styles.wrapper} ${comfortaaFontClassName}`}>
            <h2>Присоединяйтесь к сообществу!</h2>
            <Formik
                initialValues={initialValues}
                onSubmit={(values) =>
                    // dispatch(registerUser(values))
                    //     .unwrap()
                    //     .then((data: { success: any; }) => {
                    //         if (data.success) {
                    //             router.push("/profile");
                    //         }
                    //     })
                    router.push("/main")
                }
            >
                <Form className={styles.wrapper__form}>
                    <div className={styles.wrapper__form__fields}>
                        <label>
                            <Field name="email" placeholder="ФИО" />
                        </label>
                        <label>
                            <Field name="name" placeholder="Эл. почта" />
                        </label>
                        <label>
                            <Field type={showPassword ? "text" : "password"} name="password" placeholder="Пароль" />
                        </label>
                    </div>
                    <button type="submit">Зарегистрироваться</button>
                </Form>
            </Formik>
            <button className={styles.wrapper__eye} onClick={togglePasswordVisibility}>
                {showPassword ? <FaEye /> : <FaEyeSlash />}
            </button>
            <Link href="signin">
                Уже есть аккаунт? <span className={styles.wrapper__link}>Войдите!</span>
            </Link>
        </div>
    );
}

