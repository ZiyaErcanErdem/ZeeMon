import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/core/language/language.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  swaggerEnabled?: boolean;
  version: string;

  pages: Array<{ link: string; title: string }> = [];

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
  }

  private setupPages(): void {
    this.pages.push({ link: 'page-agent', title: 'dynamic.page.Agent' });
    this.pages.push({ link: 'page-endpoint', title: 'dynamic.page.Endpoint' });
    // this.pages.push({link: 'page-endpoint-property', title: 'dynamic.page.EndpointProperty'});
    this.pages.push({ link: 'page-event-trigger', title: 'dynamic.page.EventTrigger' });
    this.pages.push({ link: 'page-content-mapper', title: 'dynamic.page.ContentMapper' });

    // this.pages.push({link: 'page-field-mapping', title: 'dynamic.page.FieldMapping'});
    this.pages.push({ link: 'page-check-script', title: 'dynamic.page.CheckScript' });
    // this.pages.push({link: 'page-script-param', title: 'dynamic.page.ScriptParam'});
    this.pages.push({ link: 'page-action-script', title: 'dynamic.page.ActionScript' });
    // this.pages.push({link: 'page-action-param', title: 'dynamic.page.ActionParam'});

    this.pages.push({ link: 'page-flow', title: 'dynamic.page.Flow' });
    this.pages.push({ link: 'page-task', title: 'dynamic.page.Task' });
    this.pages.push({ link: 'page-action', title: 'dynamic.page.Action' });

    this.pages.push({ link: 'page-flow-execution', title: 'dynamic.page.FlowExecution' });
    this.pages.push({ link: 'page-task-execution', title: 'dynamic.page.TaskExecution' });
    this.pages.push({ link: 'page-action-execution', title: 'dynamic.page.ActionExecution' });

    this.pages.push({ link: 'page-content', title: 'dynamic.page.Content' });
    this.pages.push({ link: 'page-content-validation-error', title: 'dynamic.page.ContentValidationError' });
  }

  ngOnInit(): void {
    this.setupPages();
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
