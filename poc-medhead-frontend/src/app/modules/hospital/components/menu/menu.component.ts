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
    linkColor.forEach((link) => {
      const href = link.getAttribute('href') || '';
      link.classList.remove('active');

      if (
        pathname.endsWith(href) ||
        (href.includes(this.activeLink) && pathname.includes(href))
      ) {
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach((l) => l.classList.remove('active'));
        link.classList.add('active');
      });
    });
  }

  isAdmin(): boolean {
    return this.storageUserService.hasRole('Admin');
  }

  isPatient(): boolean {
    return this.storageUserService.hasRole('Patient');
  }
}
