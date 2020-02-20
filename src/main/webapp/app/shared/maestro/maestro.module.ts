import { NgModule } from '@angular/core';
import { ExecutionContentViewComponent } from './execution-content-view/execution-content-view.component';
import { TranslateModule } from '@ngx-translate/core';
import { DynamicTableModule } from 'angular-dynamic-page';

@NgModule({
  imports: [TranslateModule, DynamicTableModule],
  declarations: [ExecutionContentViewComponent],
  entryComponents: [ExecutionContentViewComponent],
  exports: [ExecutionContentViewComponent]
})
export class MaestroModule {}
