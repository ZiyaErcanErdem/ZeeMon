import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { DynamicModule } from 'angular-dynamic-page';

@NgModule({
  imports: [
    DynamicModule.forRoot({
      defaultAppId: 'Zeemon',
      registries: [
        {
          // serverApiUrl: 'http://localhost:8590/',
          applicationId: 'Zeemon',
          i18nPrefix: 'zeemonApp',
          i18nAppName: '',
          appPathPrefix: ''
        }
      ]
    })
  ],
  exports: [
    FormsModule,
    CommonModule,
    NgbModule,
    NgJhipsterModule,
    InfiniteScrollModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    TranslateModule,
    DynamicModule
  ]
})
export class ZeemonSharedLibsModule {}
