import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { HttpClient, HttpParams } from '@angular/common/http';
import {
  GenericDynamicAction,
  QueryMode,
  Theme,
  Condition,
  Operator,
  PageViewMode,
  DynamicService,
  ActionType,
  DynamicUtil,
  ActionScope,
  DynamicActionBuilder
} from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { CheckScript } from 'app/shared/model/check-script.model';
import { ScriptParam } from 'app/shared/model/script-param.model';
import { Observable, of } from 'rxjs';
import { ContentMapper } from 'app/shared/model/content-mapper.model';

@Component({
  selector: 'jhi-page-check-script',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageCheckScriptComponent extends BasePageComponent<CheckScript> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(private http: HttpClient, dynamicService: DynamicService, accountService: AccountService) {
    super('CheckScript', dynamicService, accountService);
    this.setGridCols('id', 'scriptName', 'scriptType', 'contentMapper.mapperName', 'endpoint.endpointName');
    this.setCompactCols('id', 'scriptName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(CheckScript)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'CheckScript' || cmd.qualifier === 'ContentMapper' || cmd.qualifier === 'Endpoint') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.group === 'checkScript') {
          relation.accessPath = 'check-scripts';
        } else if (relation.qualifier === 'Endpoint') {
          relation.accessPath = 'endpoints';
          relation.descriptionColumnName = 'endpointName';
          relation.popupColumns = ['id', 'endpointName', 'endpointInstanceId'];
        } else if (relation.qualifier === 'ContentMapper') {
          relation.accessPath = 'content-mappers';
          relation.descriptionColumnName = 'mapperName';
          relation.popupColumns = ['id', 'mapperName', 'itemFormat'];
        } else if (relation.qualifier === 'ScriptParam') {
          relation.accessPath = 'script-params';
          relation.descriptionColumnName = 'paramName';
          relation.popupColumns = ['id', 'paramName', 'paramValue'];

          this.pageManager!.createRelationPageManager(relation)
            .withGridColumns('id', 'paramName', 'paramDataType', 'paramValue')
            .withCompactColumns('id', 'paramName')
            .withSortingSamples(ScriptParam)
            .withRelationConfiguration((pb, rel) => {
              if (rel.qualifier === 'CheckScript') {
                rel.accessPath = 'check-scripts';
                rel.descriptionColumnName = 'scriptName';
              }
              return rel;
            });
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query.withCondition(Condition.OR).addPredicate('scriptName', Operator.LIKE, '');
      })
      .withViewer(PageViewMode.EDITOR);

    this.pageManager!.ready().subscribe(isReady => {
      if (isReady) {
        this.registerActions();
      }
    });
  }

  private generateContentMapper(): Observable<ContentMapper> {
    const current: CheckScript = this.pageManager!.getSelectedData();
    if (!current || !current.id) {
      return of();
    }
    let params: HttpParams = new HttpParams();
    params = params.set('action', 'generate-sql-mapper');
    const requestURL = `/api/generate-sql-mapper/${current.id}`;
    return this.http.post<ContentMapper>(requestURL, { params, observe: 'response' });
  }

  private generateParam(): Observable<boolean> {
    const current: CheckScript = this.pageManager!.getSelectedData();
    if (!current || !current.id) {
      return of(false);
    }
    let params: HttpParams = new HttpParams();
    params = params.set('action', 'generate-sql-params');
    const requestURL = `/api/generate-sql-params/${current.id}`;
    return this.http.post<boolean>(requestURL, { params, observe: 'response' });
  }

  private registerAction(action: GenericDynamicAction<any>): boolean {
    if (this.pageManager!.registerAction(action)) {
      this.registeredActions.push(action);
      return true;
    }
    return false;
  }

  private registerActions(): void {
    const generateMapperAction: GenericDynamicAction<any> = new DynamicActionBuilder<any>(
      'extension.action.generateMapper',
      ActionType.CUSTOM
    )
      .withScope(ActionScope.EDITOR)
      .withLabel('extension.action.generateMapper')
      .withButtonClass(DynamicUtil.themeFor('btn-', Theme.primary))
      .withIconClass('database')
      .withHandler(comp => {
        comp.disabled = true;
        this.generateContentMapper().subscribe(cm => {
          if (cm) {
            const current: CheckScript = this.pageManager!.getSelectedData();
            if (cm && cm.id && current && current.id) {
              current.contentMapper = cm;
            }
            const notification = {
              message: 'extension.message.CM_GENERATED',
              notificationType: 'alert',
              title: ''
            };
            this.pageManager!.notify(notification);
          }
          comp.disabled = false;
        });
      })
      .build();
    this.registerAction(generateMapperAction);

    const generateParamAction: GenericDynamicAction<any> = new DynamicActionBuilder<any>(
      'extension.action.generateParam',
      ActionType.CUSTOM
    )
      .withScope(ActionScope.EDITOR)
      .withLabel('extension.action.generateParam')
      .withButtonClass(DynamicUtil.themeFor('btn-', Theme.primary))
      .withIconClass('database')
      .withHandler(comp => {
        comp.disabled = true;
        this.generateParam().subscribe(ok => {
          if (ok) {
            const notification = {
              message: 'extension.message.PRM_GENERATED',
              notificationType: 'alert',
              title: ''
            };
            this.pageManager!.notify(notification);
          }
          comp.disabled = false;
        });
      })
      .build();
    this.registerAction(generateParamAction);
  }

  ngDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    super.ngOnDestroy();
  }
}
