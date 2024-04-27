import { CanActivateFn } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {

  
  
  const isLogged = localStorage.getItem('token') !== null;

  if (!isLogged) {
    window.location.href = '/error';
    return false;
  }

  return isLogged;
};


export const welcomeGuard: CanActivateFn = (route, state) => {

  const isLogged = localStorage.getItem('token') !== null;

  if (isLogged) {
    window.location.href = '/home';
    return false;
  }

  return true;
};