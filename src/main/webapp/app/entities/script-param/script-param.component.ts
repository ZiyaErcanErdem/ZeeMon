import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IScriptParam } from 'app/shared/model/script-param.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ScriptParamService } from './script-param.service';
import { ScriptParamDeleteDialogComponent } from './script-param-delete-dialog.component';

@Component({
  selector: 'jhi-script-param',
  templateUrl: './script-param.component.html'
})
export class ScriptParamComponent implements OnInit, OnDestroy {
  scriptParams?: IScriptParam[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected scriptParamService: ScriptParamService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.scriptParamService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IScriptParam[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInScriptParams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IScriptParam): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInScriptParams(): void {
    this.eventSubscriber = this.eventManager.subscribe('scriptParamListModification', () => this.loadPage());
  }

  delete(scriptParam: IScriptParam): void {
    const modalRef = this.modalService.open(ScriptParamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.scriptParam = scriptParam;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IScriptParam[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/script-param'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.scriptParams = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
