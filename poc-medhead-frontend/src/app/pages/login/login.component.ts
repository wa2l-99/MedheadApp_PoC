import { Component } from '@angular/core';
import { AuthenticationRequest } from '../../services/authentication_service/models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication_service/services';
import { TokenService } from '../../services/authentication_service/token/token.service';

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
  ) {}

 
  login() {
    this.errorMsg = [];
    this.authService.authenticate({body: this.authrequest}).subscribe({
      next: (res) => {
        this.tokenService.token = res.token as string;        this.router.navigate(['hospital']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else if (err.error) {
          this.errorMsg.push(err.error);
        } else {
          this.errorMsg.push("Une erreur inattendue s'est produite");
        }
      }
    });
  }

  register() {
    this.router.navigate(['register']);
  }
}
