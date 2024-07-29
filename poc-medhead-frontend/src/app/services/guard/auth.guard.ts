import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../authentication_service/token/token.service';
import { StorageUserServiceService } from '../authentication_service/storageUser/storage-user.service';

export const authGuard: CanActivateFn = (route) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);
  const storageUserService = inject(StorageUserServiceService);

  if (tokenService.isTokenNotValid()) {
    router.navigate(['login']);
    return false;
  }
  const userRoles = storageUserService.getSavedUser()?.roles || [];
  // eslint-disable-next-line @typescript-eslint/array-type
  const routeRoles = route.data['roles'] as Array<string>;

  if (routeRoles && routeRoles.length > 0) {
    const hasRole = routeRoles.some((role) => userRoles.includes(role));
    if (!hasRole) {
      // Rediriger vers une page d'erreur ou la page d'accueil si l'utilisateur n'a pas les rôles requis
      router.navigate(['/forbidden']);
      return false;
    }
  }
  return true;
};
