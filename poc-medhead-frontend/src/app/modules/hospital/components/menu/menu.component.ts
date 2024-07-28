import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss',
})
export class MenuComponent implements OnInit {
  currentRoute = '';
  activeLink = '';
  private _manage = false;

  constructor(private router: Router, private route: ActivatedRoute) {}

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
    throw new Error('Method not implemented.');
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

  get manage(): boolean {
    return this._manage;
  }

  set manage(value: boolean) {
    this.manage = value;
  }
}
