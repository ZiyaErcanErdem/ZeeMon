import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ZeemonSharedModule } from 'app/shared/shared.module';
import { ZeemonCoreModule } from 'app/core/core.module';
import { ZeemonAppRoutingModule } from './app-routing.module';
import { ZeemonHomeModule } from './home/home.module';
import { ZeemonEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ZeemonPageModule } from './pages/page.module';

@NgModule({
  imports: [
    BrowserModule,
    ZeemonSharedModule,
    ZeemonCoreModule,
    ZeemonHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ZeemonEntityModule,
    ZeemonPageModule,
    ZeemonAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class ZeemonAppModule {}
