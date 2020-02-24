import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BasePageComponent } from 'app/pages/base-page.component';
import { Action } from 'app/shared/model/action.model';
import { Observable, of } from 'rxjs';
import {
  GenericDynamicAction,
  QueryMode,
  Theme,
  Condition,
  Operator,
  PageViewMode,
  DynamicActionBuilder,
  ActionType,
  ActionScope,
  DynamicUtil,
  ColumnMetadata,
  PageRelation,
  DynamicService
} from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';
import { ActionExecution } from 'app/shared/model/action-execution.model';

@Component({
  selector: 'jhi-page-action',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageActionComponent extends BasePageComponent<Action> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, private http: HttpClient, accountService: AccountService) {
    super('Action', dynamicService, accountService);
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Action)
      .withGridColumns('id', 'actionName', 'nextExecutionStartTime', 'actionState', 'actionScript.scriptName', 'agent.agentName')
      .withCompactColumns('id', 'actionName')
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'Action' || cmd.qualifier === 'ActionScript' || cmd.qualifier === 'Agent') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }

        this.attachSelector(cmd);
      })
      .withRelationConfiguration(relation => {
        if (relation.group === 'action') {
          relation.accessPath = 'actions';
        } else if (relation.qualifier === 'ActionScript') {
          relation.accessPath = 'action-scripts';
          relation.descriptionColumnName = 'scriptName';
          relation.popupColumns = ['id', 'scriptName', 'actionCode', 'scriptType'];
        } else if (relation.qualifier === 'Agent') {
          relation.accessPath = 'agent';
          relation.descriptionColumnName = 'agentName';
          relation.popupColumns = ['id', 'agentName', 'agentStatus'];
        } else if (relation.qualifier === 'ActionExecution') {
          relation.accessPath = 'action-executions';
          relation.descriptionColumnName = 'executionStartTime';

          this.attachActionExecutionRelation(relation);
        } else {
          relation.searchable = false;
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('agent.agentName', Operator.LIKE, '').and()
          .addPredicate('actionScript.scriptName', Operator.LIKE, '').and()
          .addPredicate('actionName', Operator.LIKE, '').and();
      })
      .withViewer(PageViewMode.EDITOR);

    this.pageManager!.ready().subscribe(isReady => {
      if (isReady) {
        this.registerActions();
      }
    });
  }

  private attachActionExecutionRelation(relation: PageRelation): void {
    this.pageManager!.createRelationPageManager(relation)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 50;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        config.canCreate = false;
        config.canEdit = false;
        config.canDelete = false;
        return config;
      })
      .withGridColumns('id', 'executionStartTime', 'executionEndTime', 'executionStatus')
      .withCompactColumns('id', 'executionStartTime')
      .withSortingSamples(ActionExecution)
      .withRelationConfiguration((pb, rel) => {
        if (rel.qualifier === 'ActionExecution') {
          rel.accessPath = 'action-executions';
        } else if (relation.qualifier === 'Action') {
          rel.accessPath = 'actions';
          rel.descriptionColumnName = 'actionName';
          rel.popupColumns = ['id', 'actionName', 'actionState'];
        }
        return rel;
      })
      .withViewer(PageViewMode.DETAIL);
  }

  private attachSelector(cmd: ColumnMetadata): void {
    if (cmd.qualifier === 'ActionScript' && (cmd.name === 'scriptName' || cmd.name === 'actionCode')) {
      cmd.selector = this.pageManager!.createSelectorBuilder(cmd.qualifier, 'action-scripts')
        .withSelectionKey(cmd.name)
        .withSelectorColumns('id', 'scriptName', 'actionCode', 'scriptType', 'actionType')
        .addSelectionMapping(cmd.name, cmd.name)
        .preventManuelEntry()
        .addSelectorColumnWidth('id', '40px')
        .addSelectorColumnWidth('scriptName', '300px')
        .addSelectorColumnWidth('actionCode', '200px')
        .addSelectorColumnWidth('scriptType', '150px')
        .addSelectorColumnWidth('actionType', '150px')
        .addQueryItem({ name: cmd.name, operator: Operator.LIKE, value: '' })
        .build();
    } else if (cmd.qualifier === 'Agent' && (cmd.name === 'agentName' || cmd.name === 'agentInstanceId')) {
      cmd.selector = this.pageManager!.createSelectorBuilder(cmd.qualifier, 'agents')
        .withSelectionKey(cmd.name)
        .withSelectorColumns('id', 'agentName', 'agentInstanceId', 'agentType', 'agentStatus')
        .addSelectionMapping(cmd.name, cmd.name)
        .addQueryItem({ name: cmd.name, operator: Operator.LIKE, value: '' })
        .build();
    }
  }

  private executeAction(): Observable<boolean> {
    const current: Action = this.pageManager!.getSelectedData();
    if (!current || !current.id) {
      return of(false);
    }
    let params: HttpParams = new HttpParams();
    params = params.set('action', 'execute-sql-action');
    const requestURL = `/api/execute-sql-action/${current.id}`;
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
    const testAction: GenericDynamicAction<any> = new DynamicActionBuilder<any>('extension.action.execute', ActionType.CUSTOM)
      .withScope(ActionScope.EDITOR)
      .withLabel('extension.action.execute')
      .withButtonClass(DynamicUtil.themeFor('btn-', Theme.danger))
      .withIconClass('bolt')
      .withHandler(comp => {
        comp.disabled = true;
        this.executeAction().subscribe(ok => {
          if (ok) {
            const notification = {
              message: 'extension.message.PRM_ACTION_EXECUTED',
              notificationType: 'alert',
              title: ''
            };
            this.pageManager!.notify(notification);
          }
          comp.disabled = false;
        });
      })
      .build();
    this.registerAction(testAction);
  }

  ngOnDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    super.ngOnDestroy();
  }
}
