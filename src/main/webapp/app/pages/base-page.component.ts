import { OnDestroy } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { BasePageView, PageManager, DynamicService, Theme, PanelState } from 'angular-dynamic-page';

export class BasePageComponent<T> extends BasePageView<T> implements OnDestroy {
  public pageManager?: PageManager<T>;
  private userID = '';
  private qualifier: string;

  private userGroups?: Array<string>;
  private userRoles: Array<string> = [];

  protected gridCols: Array<string>;
  protected compactCols: Array<string>;

  constructor(qualifier: string, private dynamicService: DynamicService, private accountService: AccountService) {
    super();
    this.theme = Theme.dark;

    this.qualifier = qualifier;
    this.setupUserGroupsAndRoles();
    this.gridCols = new Array<string>();
    this.compactCols = new Array<string>();
    this.setQualifier(qualifier);
    this.panelState = PanelState.EXPANDED;
  }

  protected setQualifier(qualifier: string): void {
    if (qualifier) {
      this.qualifier = qualifier;
      if (this.pageManager) {
        this.pageManager.destroy();
      }
      this.pageManager = this.dynamicService.createPageManager({ qualifier });
    }
  }

  public setGridCols(...colNames: string[]): void {
    this.gridCols = [...colNames];
  }

  public setCompactCols(...colNames: string[]): void {
    this.compactCols = [...colNames];
  }

  private setupUserGroupsAndRoles(): void {
    this.userRoles = [];
    this.userGroups = [];
    this.accountService.identity().subscribe(account => {
      this.userID = account!.login || '';
      // this.userGroups = identity.groups;
      if (!this.userGroups) {
        this.userGroups = [];
      } else {
        this.userGroups = this.userGroups.filter(g => g.startsWith('/Projects'));
      }
      this.userRoles = account!.authorities || [];
      if (!this.userRoles) {
        this.userRoles = [];
      }
    });
  }

  public getUserId(): string {
    return this.userID;
  }

  public getUserDefaultProjectGroup(): string {
    if (this.userGroups!.length === 0) {
      return 'not-authorized';
    }
    return this.userGroups![0];
  }

  public getUserProjectGroups(): Array<string> | null {
    if (this.userGroups === undefined || this.userGroups.length === 0) {
      return null;
    }
    return this.userGroups.concat([]);
  }

  protected hasUserAnyGroup(): boolean {
    return this.userGroups !== undefined && this.userGroups.length > 0;
  }

  protected hasUserAnyRole(): boolean {
    return this.userRoles !== undefined && this.userRoles.length > 0;
  }

  public isUserInRole(role: string): boolean {
    if (!role) {
      return false;
    }
    const hasRole = this.userRoles.some((r: string) => r === role);
    return hasRole;
  }

  public isUserManager(): boolean {
    return this.isUserQualityNamager() || this.isUserSupervisor();
  }

  public isUserSupervisor(): boolean {
    const isSupervisor = this.isUserInRole('ROLE_SUPERVISOR');
    return isSupervisor;
  }

  public isUserQualityNamager(): boolean {
    const isQM = this.isUserInRole('ROLE_QUALITY_MANAGER') || this.isUserInRole('ROLE_SUPERVISOR');
    return isQM;
  }

  public isUserAgent(): boolean {
    const isAgent = this.isUserInRole('ROLE_AGENT');
    return isAgent;
  }

  public canUserDownloadExcel(): boolean {
    const hasRole = this.isUserInRole('ROLE_DOWNLOAD_EXCEL');
    return hasRole;
  }

  public canUserDownloadResource(): boolean {
    const hasRole = this.isUserInRole('ROLE_DOWNLOAD_RESOURCE');
    return hasRole;
  }

  public canUserUploadExcel(): boolean {
    const hasRole = this.isUserInRole('ROLE_UPLOAD_EXCEL');
    return hasRole;
  }

  public canUserPreviewResource(): boolean {
    const hasRole = this.isUserInRole('ROLE_PREVIEW_RESOURCE');
    return hasRole;
  }

  public ngOnDestroy(): void {
    if (this.pageManager) {
      this.pageManager.destroy();
    }
    super.ngOnDestroy();
  }
}
