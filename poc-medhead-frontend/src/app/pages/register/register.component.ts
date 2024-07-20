import { Component } from '@angular/core';
import { RegistrationRequest } from '../../services/authentication_service/models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication_service/services';
import { ToastrService } from 'ngx-toastr';

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
    private authService: AuthenticationService,
    private toastr: ToastrService
  ) {}

  login() {
    this.router.navigate(['login']);
  }

  register() {
    this.errorMsg = [];
    this.authService.register({ body: this.registerRequest }).subscribe({
      next: () => {
        this.router.navigate(['activate-account']);
        this.toastr.success(
          'Enregistrement réussi, veuillez activer votre compte.',
          'Succès'
        );
      },
      error: (err) => {
        if (err.error && Array.isArray(err.error.validationErrors)) {
          this.errorMsg = err.error.validationErrors;
          this.errorMsg.forEach((error: string) => {
            this.toastr.error(error, 'Erreur de validation');
          });
        }else {
          this.toastr.error("Une erreur inattendue s'est produite", 'Erreur');
        }
      },
    });
  }
}
