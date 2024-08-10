import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { StorageUserServiceService } from '../../../../services/authentication_service/storageUser/storage-user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss',
})
export class MenuComponent implements OnInit {
  currentRoute = '';
  activeLink = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private storageUserService: StorageUserServiceService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.activeLink = params['active'] || '';
      this.updateActiveLink();
    });

    // Mettre à jour les liens actifs lors de chaque changement de navigation
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.updateActiveLink();
      }
    });
  }

  logout() {
    localStorage.removeItem('token');
    window.location.reload();
  }

  updateActiveLink() {
    const linkColor = document.querySelectorAll('.nav-link');
    const pathname = window.location.pathname;

    // Déclarez explicitement le type de activeLink
    let activeLink: HTMLElement | null = null;

    // Trouver le lien actif en fonction de l'URL
    linkColor.forEach((link) => {
      const href = link.getAttribute('href') || '';
      if (
        pathname.endsWith(href) ||
        (href.includes(this.activeLink) && pathname.includes(href))
      ) {
        activeLink = link as HTMLElement;
      }
    });

    // Si un lien actif est trouvé, mettre à jour les classes
    if (activeLink) {
      linkColor.forEach((link) => {
        link.classList.toggle('active', link === activeLink);
      });
    }

    // Gestion de l'activation par clic
    document.querySelector('.navbar')?.addEventListener('click', (event) => {
      const target = event.target as HTMLElement;
      if (target.classList.contains('nav-link')) {
        linkColor.forEach((link) => link.classList.remove('active'));
        target.classList.add('active');
      }
    });
  }

  isAdmin(): boolean {
    return this.storageUserService.hasRole('Admin');
  }

  isPatient(): boolean {
    return this.storageUserService.hasRole('Patient');
  }
}
