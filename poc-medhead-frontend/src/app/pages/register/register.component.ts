import { Component } from '@angular/core';
import { RegistrationRequest } from '../../services/authentication_service/models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication_service/services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  errorMsg: string[] = [];
  registerRequest: RegistrationRequest = {
    adresse: '',
    dateNaissance: '',
    email: '',
    nom: '',
    numero: '',
    password: '',
    prenom: '',
    sexe: '',
  };
  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  login() {
    this.router.navigate(['login']);
  }

  register() {
    this.errorMsg = [];
    this.authService.register({ body: this.registerRequest })
    .subscribe({
      next: () => {
        this.router.navigate(['activate-account'])
      },
      error: (err) => {
        this.errorMsg = err.error.validationErrors;
      }
    });
  }
}
