import { Component } from '@angular/core';
import { AuthenticationRequest } from '../../services/authentication_service/models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication_service/services';
import { TokenService } from '../../services/authentication_service/token/token.service';
import { ToastrService } from 'ngx-toastr';
import { StorageUserServiceService } from '../../services/authentication_service/storageUser/storage-user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  authrequest: AuthenticationRequest = { email: '', password: '' };
  errorMsg: string[] = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService,
    private toastr: ToastrService,
    private storageUserService: StorageUserServiceService
  ) {}

  login() {
    this.errorMsg = [];
    this.authService.authenticate({ body: this.authrequest }).subscribe({
      next: (res) => {
        this.tokenService.token = res.token as string;
        this.router.navigate(['hospital']);
        this.toastr.success('Connexion réussie !', 'Succès');

        if (res.user) {
          this.storageUserService.saveUser(res.user);
        }
      },
      error: (err) => {
        if (err.error) {
          if (Array.isArray(err.error.validationErrors)) {
            this.errorMsg = err.error.validationErrors;
            this.errorMsg.forEach((error: string) => {
              this.toastr.error(error, 'Erreur de validation');
            });
          } else if (err.error.error) {
            this.toastr.error(err.error.error, 'Erreur');
          } else {
            this.toastr.error("Une erreur inattendue s'est produite", 'Erreur');
          }
        } else {
          this.toastr.error("Une erreur inattendue s'est produite", 'Erreur');
        }
      },
    });
  }

  register() {
    this.router.navigate(['register']);
  }
}
