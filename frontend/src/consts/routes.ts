import { IRouteItem } from "./types";

export const authRoutes: IRouteItem[] = [
  { title: "Войти", link: "/signin" },
  { title: "Загеристрироваться", link: "/signup" },
];

export const profileRoutes: IRouteItem[] = [
  { title: "Главная", link: "/main" },
 ];

export const routes = {
  main: "/welcome",
  auth: {
    signin: "/signin",
    signup: "/signup",
  },
  profile: {
    main: "/main",
  },
};